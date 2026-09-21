package ahorrosbienestar.sib.std.cobranza.refereciaPagos.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.catalogos.dto.EmpleadosCE;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.controller.BitacoraRestController;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.controller.ConsultaRestController;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.dto.ConsultaDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.service.CuentasClienteService;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.dto.HistorialDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.model.HistorialModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.service.HistorialService;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReferenciaPagosRestController {

	@Autowired
	private ConsultaRestController consulta;

	@Autowired
	private BitacoraRestController bitacora;

	@Autowired
	CuentasClienteService cuentasClienteService;

	@Autowired
	HistorialService historialService;

	@Autowired
	private IReporteRepository iReporteRepository;

	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	CatalogosCEService catalogosCEService;
	
	@Autowired
	ConfigService configService;
	
	// Leyenda
	private static final String leyenda = "LeyendaRP";

	// Logger
	private static final Logger logger =
	        LoggerFactory.getLogger(ReferenciaPagosRestController.class);

	private static final String nombreReporte = "rpt_historial_busqueda_rp";
	private static final String nombreExportar = "rpt_referencia_pagos";
	private static final String controllerReporte = "generarReporte";

	/**
	 * Guardar los datos del historial
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {
			"/recibir_consulta" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus recibirConsulta(@RequestBody List<ConsultaDTO> dto) {

		// Obtener las cuentas
		List<CuentasClienteModel> cuentasClienteList = new ArrayList<CuentasClienteModel>();

		try {

			cuentasClienteList = cuentasClienteService.clientePorNumCliente(dto.get(0).getNumeroCliente());

			cuentasClienteList.forEach(cuenta -> {

				// Guardar consulta y llenar DTO
				ConsultaModel model = consulta.guardarConsulta(dto.get(0), cuenta);

				// Guardar bitacora
				bitacora.guardarBitacora(model, cuenta);
			});

			return HttpStatus.OK;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Mostrar los datos del historial
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/historial" }, method = RequestMethod.GET)
	public List<HistorialDTO> historialDeBusqueda() {

		List<HistorialDTO> respuesta = new ArrayList<HistorialDTO>();

		List<HistorialModel> listaHistorial = historialService.historialDeBusqueda();

		listaHistorial.forEach(consulta -> {
			HistorialDTO dto = new HistorialDTO();
			// usuario
			Usuario usuario = usuarioService.findByNombre(consulta.getPk_id_usuario().getVc_nombre_usuario());
			logger.info("Empleado encontrado");

			List<EmpleadosCE> listaCes = catalogosCEService.nombreCompletoEmpleado(usuario.getI_id_empleado());
			logger.info("Empleado: " + listaCes.get(0).nombreCompleto);

			dto.setPk_id_consulta_rp(String.valueOf(consulta.getPk_id_consulta_rp()));
			dto.setVc_nombre_usuario(listaCes.get(0).nombreCompleto);
			dto.setDt_fecha_registro(consulta.getDt_fecha_registro().toString());
			dto.setVc_parametro_busqueda(consulta.getVc_parametro_busqueda());
			dto.setVc_valor_parametro(consulta.getVc_valor_parametro());
			dto.setI_numero_cliente(consulta.getI_numero_cliente().toString());
			dto.setVc_nombre_cliente(consulta.getVc_nombre_cliente());
			dto.setI_numero_cuenta_principal_1(consulta.getI_numero_cuenta_principal_1().toString());
			dto.setI_numero_cuenta_principal_2(consulta.getI_numero_cuenta_principal_2().toString());
			dto.setI_numero_credito(consulta.getI_numero_credito().toString());
			dto.setVc_producto_credito(consulta.getVc_producto_credito());
			dto.setI_referencia_banco_credito(consulta.getI_referencia_banco_credito().toString());
			dto.setVc_nombre_banco_credito(consulta.getVc_nombre_banco_credito());
			respuesta.add(dto);
		});

		return respuesta;
	}

	/**
	 * Generar el reporte de historial
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/reporte_historial" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteHistorial(@RequestParam("inicio") String inicio, @RequestParam("fin") String fin) {

		ArrayList<String> respuesta = new ArrayList<String>();

		try {
			
			// authentication
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");
			// usuario
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Usuario que realizó la busqueda: " + usuario.getVc_nombre_usuario());
			// empleado 
			List<EmpleadosCE> listaCes = catalogosCEService.nombreCompletoEmpleado(usuario.getI_id_empleado());
			logger.info("Empleado: " + listaCes.get(0).nombreCompleto);
            
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte) + datosReporte.getPk_id_reporte() + "&parametros=?empleado="
			+ listaCes.get(0).nombreCompleto
			+ "~FechaInicio=" + inicio 
			+ "~FechaFin=" + fin 
			+ "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Exportar los datos de la busqueda
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/exportar_cliente" }, method = RequestMethod.POST)
	public ResponseEntity<?> exportarDatosCliente(@RequestParam("clienteId") Integer clienteId) {

		ArrayList<String> respuesta = new ArrayList<String>();

		try {
			
			// authentication
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");
			// usuario
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Usuario que realizó la busqueda: " + usuario.getVc_nombre_usuario());
			// empleado 
			List<EmpleadosCE> listaCes = catalogosCEService.nombreCompletoEmpleado(usuario.getI_id_empleado());
			logger.info("Empleado: " + listaCes.get(0).nombreCompleto);
			// leyenda
			Configuracion configuracion = configService.valorPropiedad(leyenda);
			logger.info("Nombre propiedad: " + configuracion.getVc_nom_propiedad());

			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreExportar);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() + "&parametros=?id=" + clienteId 
					+ "~empleado=" + listaCes.get(0).nombreCompleto
					+ "~leyenda=" + configuracion.getVc_valor_propiedad()
					+ "~FechaInicio=" + "~FechaFin="
					+ "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}

	@RequestMapping(value = { "/mostrar_role" }, method = RequestMethod.GET)
	public ResponseEntity<?> tipoDeRole() {

		ArrayList<String> respuesta = new ArrayList<String>();

		try {
			// authentication
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");

			// usuario
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Usuario que realizó la busqueda: " + usuario.getVc_nombre_usuario());

			respuesta.add(usuario.getRol_idRol().getVc_nombre_rol());

			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(null);
		}
	}

}
