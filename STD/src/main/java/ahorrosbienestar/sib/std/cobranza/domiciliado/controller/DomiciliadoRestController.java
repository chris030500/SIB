package ahorrosbienestar.sib.std.cobranza.domiciliado.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.controller.AutorizacionRestController;
import ahorrosbienestar.sib.std.cobranza.banco.model.BancoModel;
import ahorrosbienestar.sib.std.cobranza.banco.service.BancoService;
import ahorrosbienestar.sib.std.cobranza.detalleCarga.model.DetalleCargaModel;
import ahorrosbienestar.sib.std.cobranza.detalleCarga.service.DetalleCargaService;
import ahorrosbienestar.sib.std.cobranza.domiciliado.dto.ClabeDTO;
import ahorrosbienestar.sib.std.cobranza.domiciliado.dto.ProcesoDTO;
import ahorrosbienestar.sib.std.cobranza.domiciliado.dto.ValorDTO;
import ahorrosbienestar.sib.std.cobranza.domiciliado.model.CuentasModel;
import ahorrosbienestar.sib.std.cobranza.domiciliado.service.CuentasService;
import ahorrosbienestar.sib.std.cobranza.procesoCarga.bitacora.model.ProcesoCargaBitacoraModel;
import ahorrosbienestar.sib.std.cobranza.procesoCarga.bitacora.service.ProcesoCargaBitacoraService;
import ahorrosbienestar.sib.std.cobranza.procesoCarga.model.ProcesoCargaModel;
import ahorrosbienestar.sib.std.cobranza.procesoCarga.service.ProcesoCargaService;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DomiciliadoRestController {

	@Autowired
	private CuentasService cuentasService;

	@Autowired
	private BancoService bancoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ProcesoCargaService procesoCargaService;

	@Autowired
	DetalleCargaService detalleCargaService;

	@Autowired
	ProcesoCargaBitacoraService procesoCargaBitacoraService;

	@Autowired
	ConfigService configService;

	// Logger
	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	// prefijo archivo
	private final static int PREFIJO_ARCHIVO = 72;

	@RequestMapping(value = {"/recibir_clabe" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<ClabeDTO> datosCuentaClabe(@RequestBody List<ClabeDTO> listaClabeDTO) {
		List<ClabeDTO> listaRes = new ArrayList<ClabeDTO>();
		try {
			listaClabeDTO.forEach(clabe -> {
				CuentasModel model = cuentasService.datosCuentaClabe(clabe.getClabe());
				logger.info("se encontraron los datos de la cuenta CLABE");
				ClabeDTO dto = new ClabeDTO();
				dto.setClienteId(model.getCliente_id());
				dto.setClabe(model.getClabe());
				dto.setCreditoId("0" + model.getCredito_id());
				dto.setRfcOficial(model.getRfc_oficial());
				listaRes.add(dto);
			});
			return listaRes;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = { "/lista_bancos" }, method = RequestMethod.GET)
	public List<BancoModel> listaBancos() {
		List<BancoModel> respuesta = new ArrayList<BancoModel>();
		try {
			respuesta = bancoService.listaBancosPorEstatus(1);
			logger.info("Se encontraron bancos activos");
			return respuesta;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = {"/valores_layout" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus valoresLayout(@RequestBody ProcesoDTO dto) {
		try {
			// usuario
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Se encontró el usuario");

			// fecha
			LocalDate fechaLocal = LocalDate.now();

			// guardar proceso
			ProcesoCargaModel procesoModel = new ProcesoCargaModel();
			procesoModel.setFk_banco(bancoService.findById(dto.getBanco()));
			procesoModel.setI_cantidad_registro(dto.getCantidadRegistro());
			procesoModel.setI_total_monto(dto.getTotalMonto());
			procesoModel.setDt_fecha_carga(fechaLocal);
			procesoModel.setFk_usuario_carga(usuario);
			ProcesoCargaModel procesoGuardado = procesoCargaService.save(procesoModel);
			logger.info("Se guardó el proceso");

			// guardar detalle
			DetalleCargaModel detalleModel = new DetalleCargaModel();
			detalleModel.setFk_proceso(procesoGuardado);
			detalleModel.setTx_archivo_entrada(dto.getEntrada());
			detalleModel.setTx_archivo_salida(dto.getSalida());
			detalleModel.setDt_fecha_entrada(fechaLocal);
			detalleModel.setDt_fecha_salida(fechaLocal);
			detalleCargaService.save(detalleModel);
			logger.info("Se guardó el detalle");
			return HttpStatus.OK;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return null;
		}

	}

	@RequestMapping(value = { "/bitacora" }, method = RequestMethod.GET)
	public List<ProcesoCargaBitacoraModel> bitacora() {
		return procesoCargaBitacoraService.listaBitacora();
	}

	@RequestMapping(value = { "/prefijo_archivo" }, method = RequestMethod.GET)
	public ValorDTO prefijoArchivo() {
		ValorDTO valorDTO = new ValorDTO();
		try {
			// usuario
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Se encontró el usuario");
			// configuración
			Configuracion configuracion = configService.obtenerConfiguracion(PREFIJO_ARCHIVO);
			logger.info("Se encontró la configuración");
			valorDTO.setPrefijoArchivo(configuracion.getVc_valor_propiedad());
			return valorDTO;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return null;
		}
	}

}
