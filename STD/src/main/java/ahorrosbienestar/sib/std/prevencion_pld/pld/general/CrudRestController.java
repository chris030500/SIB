package ahorrosbienestar.sib.std.prevencion_pld.pld.general;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.controller.AutorizacionRestController;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.dto.BitacoraPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.service.BitacoraPldService;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.service.EstatusPldService;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.controller.ExclusionPldRestController;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.dto.ExclusionPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ExclusionPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.service.ExclusionPldService;
import ahorrosbienestar.sib.std.prevencion_pld.formato.dto.FormatoPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.formato.model.FormatoPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.formato.service.FormatoPldService;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.service.ListasPldService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CrudRestController {

	@Autowired
	private ListasPldService listasPldService;

	@Autowired
	private EstatusPldService estatusPldService;

	@Autowired
	private ExclusionPldService exclusionPldService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FormatoPldService formatoPldService;

	@Autowired
	private BitacoraPldService bitacoraPldService;

	@Autowired
	private ExclusionPldRestController exclusionPldRestController;

	// Logger
	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	// Guardado exclusion
	@RequestMapping(value = {
			"/agregar_exclusion" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus guardarExclusion(@RequestBody ExclusionPldDTO exclusion) {
		try {

			Boolean existeExclusion = exclusionPldRestController.exclusionRegistrada(exclusion.getLista(),
					exclusion.getEstatus());
			if (!existeExclusion) {
				ExclusionPldModel model = new ExclusionPldModel();
				LocalDate fechaLocal = LocalDate.now();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Usuario usuario = usuarioService.findByNombre(auth.getName());
				logger.info("se encotró el usuario");
				ListasPldModel lista = listasPldService.findById(exclusion.getLista());
				logger.info("se encotró la lista");
				EstatusPldModel estatus = estatusPldService.findById(exclusion.getEstatus());
				logger.info("se encotró el estatus");
				model.setFk_lista(lista);
				model.setFk_estatus(estatus);
				model.setDt_fecha(fechaLocal);
				model.setFk_usuario_registro(usuario);
				exclusionPldService.save(model);
				logger.info("se guardó la exclusion");
				return HttpStatus.OK;
			} else {
				logger.info("ya existe esta exclusion");
				return HttpStatus.CONTINUE;
			}

		} catch (Exception e) {
			logger.info("no se guardó la exclusion");
			logger.info(e.getMessage());
			return HttpStatus.BAD_REQUEST;
		}
	}

	// Eliminar exclusion
	@RequestMapping(value = { "/eliminar_exclusion" }, method = RequestMethod.GET)
	public HttpStatus eliminarExclusion(@RequestParam("idExclusion") Integer idExclusion) {
		try {
			exclusionPldService.delete(idExclusion);
			logger.info("se eliminó la exclusion");
			return HttpStatus.OK;
		} catch (Exception e) {
			logger.info("no se eliminó la exclusion");
			logger.info(e.getMessage());
			return HttpStatus.BAD_REQUEST;
		}
	}

	// Guardado formato
	public HttpStatus guardarFormato(FormatoPldDTO formato) {
		try {
			FormatoPldModel model = new FormatoPldModel();
			LocalDate fechaLocal = LocalDate.now();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model.setVc_nombre(formato.getNombre());
			model.setVc_paterno(formato.getPaterno());
			model.setVc_materno(formato.getMaterno());
			model.setVc_curp(formato.getCurp());
			model.setVc_rfc(formato.getRfc());
			model.setVc_fecnac(formato.getFecnac());
			model.setVc_lista(formato.getLista());
			model.setVc_estatus(formato.getEstatus());
			model.setVc_dependencia(formato.getDependencia());
			model.setVc_puesto(formato.getPuesto());
			model.setVc_iddispo(formato.getIddispo());
			model.setVc_curpok(formato.getCurpok());
			model.setVc_idrel(formato.getIdrel());
			model.setVc_parentesco(formato.getParentesco());
			model.setVc_razonsoc(formato.getRazonsoc());
			model.setVc_rfcmoral(formato.getRfcmoral());
			model.setVc_issste(formato.getIssste());
			model.setVc_imss(formato.getImss());
			model.setVc_ingresos(formato.getIngresos());
			model.setVc_nomcomp(formato.getNomcomp());
			model.setVc_apellidos(formato.getApellidos());
			model.setVc_entidad(formato.getEntidad());
			model.setVc_genero(formato.getGenero());
			model.setDt_fecha(fechaLocal);
			model.setFk_usuario_registro(usuario);
			// formatoPldService.save(model);
			logger.info("se guardó el formato");
			return HttpStatus.OK;
		} catch (Exception e) {
			logger.info("no se guardó el formato");
			logger.info(e.getMessage());
			return HttpStatus.BAD_REQUEST;
		}
	}

	// Guardado bitacora
	public HttpStatus guardarBitacora(BitacoraPldDTO bitacora) {
		try {
			BitacoraPldModel model = new BitacoraPldModel();
			LocalDate fechaLocal = LocalDate.now();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model.setVc_nombre_archivo(bitacora.getNombre_archivo());
			model.setI_numero_registro(bitacora.getNumero_registro());
			model.setI_registros_excluidos(bitacora.getRegistros_excluidos());
			model.setI_registro_final(bitacora.getRegistro_final());
			model.setVc_duracion(bitacora.getDuracion());
			model.setVc_estatus(bitacora.getEstatus());
			model.setVc_comentarios(bitacora.getComentarios());
			model.setDt_fecha(fechaLocal);
			model.setFk_usuario_registro(usuario);
			// bitacoraPldService.save(model);
			logger.info("se guardó la bitacora");
			return HttpStatus.OK;
		} catch (Exception e) {
			logger.info("no se guardó la bitacora");
			logger.info(e.getMessage());
			return HttpStatus.BAD_REQUEST;
		}
	}

}
