package ahorrosbienestar.sib.std.prevencion_pld.pld.general;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.autorizacion.controller.AutorizacionRestController;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.dto.BitacoraPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.service.BitacoraPldService;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.service.EstatusPldService;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.controller.ExclusionPldRestController;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.dto.ExclusionPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ExclusionPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.service.ExclusionPldService;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.service.ListasPldService;
import ahorrosbienestar.sib.std.prevencion_pld.pld.dto.RolPldDTO;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CrudRestController {

	private final static int REPOSITORIO_FORMATO = 64;
	private final static String ARCHIVO_FINAL = "FormatoTerceros";
	private final static int GESTOR = 65;
	private final static int AUXILIAR = 66;
	private final static int CONSULTOR = 67;
	private final static String EXTERNO = "EXTERNO";

	@Autowired
	private ConfigService configService;

	@Autowired
	private ListasPldService listasPldService;

	@Autowired
	private EstatusPldService estatusPldService;

	@Autowired
	private ExclusionPldService exclusionPldService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ExclusionPldRestController exclusionPldRestController;

	@Autowired
	private BitacoraPldService bitacoraPldService;

	// Logger
	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	// Guardado exclusion
	@RequestMapping(value = {
			"/agregar_exclusion" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus guardarExclusion(@RequestBody ExclusionPldDTO exclusion) {
		try {
			if (exclusion.getEstatus() == 0) {
				// Todos los estatus
				List<EstatusPldModel> listaEstatus = estatusPldService.estatusDeUnaLista(exclusion.getLista());
				listaEstatus.forEach(estatus -> {
					Boolean existeExclusion = exclusionPldRestController.exclusionRegistrada(exclusion.getLista(),
							estatus.getPk_id_estatus());
					if (!existeExclusion) {
						ExclusionPldModel model = new ExclusionPldModel();
						LocalDate fechaLocal = LocalDate.now();
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						Usuario usuario = usuarioService.findByNombre(auth.getName());
						logger.info("se encotró el usuario");
						ListasPldModel lista = listasPldService.findById(exclusion.getLista());
						logger.info("se encotró la lista");
						model.setFk_lista(lista);
						model.setFk_estatus(estatus);
						model.setDt_fecha(fechaLocal);
						model.setFk_usuario_registro(usuario);
						exclusionPldService.save(model);
						logger.info("se guardó la exclusion");
					}
				});
				return HttpStatus.OK;
			} else {
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

	// Guardado bitacora
	@RequestMapping(value = {
			"/agregar_bitacora" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RolPldDTO guardarBitacora(@RequestBody BitacoraPldDTO bitacora) {
		RolPldDTO repositorio = new RolPldDTO();
		try {
			Configuracion configuracion = configService.obtenerConfiguracion(REPOSITORIO_FORMATO);
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
			bitacoraPldService.save(model);
			logger.info("se guardó la bitacora");
			repositorio.setNombreRol(
					configuracion.getVc_valor_propiedad() + ARCHIVO_FINAL + "-" + fechaLocal.toString() + ".txt");
			return repositorio;
		} catch (Exception e) {
			logger.info("no se guardó la bitacora");
			logger.info(e.getMessage());
			repositorio.setNombreRol(e.getMessage());
			return repositorio;
		}
	}

	@RequestMapping(value = { "/mostral_rol" }, method = RequestMethod.GET)
	public RolPldDTO identificarRol() {
		RolPldDTO rol = new RolPldDTO();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			Configuracion configuracion = configService.valorPropiedad(Integer.toString(usuario.getRol_idRol().getPk_id_rol()));
			logger.info("se encontró la configuración");
			switch (configuracion.getPk_id_configuracion()) {
			case GESTOR:
				logger.info("gestor");
				rol.setNombreRol(configuracion.getVc_valor_propiedad());
				break;
			case AUXILIAR:
				logger.info("auxiliar");
				rol.setNombreRol(configuracion.getVc_valor_propiedad());
				break;
			case CONSULTOR:
				logger.info("consultor");
				rol.setNombreRol(configuracion.getVc_valor_propiedad());
				break;
			default:
				break;
			}
			return rol;
		} catch (Exception e) {
			logger.info(e.getMessage());
			rol.setNombreRol(EXTERNO);
			return rol;
		}
	}

}
