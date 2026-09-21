package ahorrosbienestar.sib.std.menuProducto.conf.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.model.ConfiguracionSistema;
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.service.ConfiguracionSistemaService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.usuarios.service.MicrofinUsuarioService;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("parametros")
public class ParametrosConfigController {

	private static final Logger logger =
	        LoggerFactory.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private ICatalogoRepository iCatalogoRepository;

	@Autowired
	private BasesService basesService;

	@Autowired
	private ConfiguracionSistemaService configuracionService;

	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ConfiguracionSistemaService configuracionSistemaService;
	
	@Autowired
	private MicrofinUsuarioService microfinUsuarioService;
	
	
	private int catTipoEstatus = 16;
	private int catTipoComodines = 17;
	private int catTipoAsignacion = 21;
	private int catTipoCliente = 18;
	private int catTipoCartera = 19;
	private int catTipoPrioridad = 20;

	private static final String nombrePantalla = "Parametros de Configuracion";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion = "";
	List<Usuario> usuario = null;


	@GetMapping("/configuracion")
	public String paramsConfiguracion(Model model) throws IOException {
	    try {
	        String username = obtenerUsernameAutenticado();

	        if (username == null) {
	            logger.warn("Usuario no autenticado.");
	            return rutaError;
	        }

	        usuario = iUsuarioRepository.findByNombreUsuario(username);

	        if (usuario == null || usuario.isEmpty()) {
	            logger.warn("No se encontró usuario: " + username);
	            return rutaError;
	        }

	        if (validarAcceso(username) == 1) {
	            List<Bases> bases = basesService.findAll();
	            List<ConfiguracionSistema> configuraciones = configuracionService.obtenerConfiguraciones();
	            List<Usuario> usuarios = usuarioService.findAll();

	            model.addAttribute("cat_tipo_estatus", iCatalogoRepository.generarOpciones(catTipoEstatus));
	            model.addAttribute("cat_tipo_comodines", iCatalogoRepository.generarOpciones(catTipoComodines));
	            model.addAttribute("cat_tipo_asignacion", iCatalogoRepository.generarOpciones(catTipoAsignacion));
	            model.addAttribute("cat_tipo_cliente", iCatalogoRepository.generarOpciones(catTipoCliente));
	            model.addAttribute("cat_tipo_cartera", iCatalogoRepository.generarOpciones(catTipoCartera));
	            model.addAttribute("cat_tipo_prioridad", iCatalogoRepository.generarOpciones(catTipoPrioridad));
	            model.addAttribute("bases", bases);
	            model.addAttribute("usuarios", usuarios);
	            model.addAttribute("puestos", microfinUsuarioService.obtenerPuestos());
	            model.addAttribute("permiso_desbloqueo",
	                    configuracionSistemaService.buscarConfiguracion(Constantes.USUARIOS_DESBLOQUEO, username));
	            model.addAttribute("permiso_admin",
	                    configuracionSistemaService.buscarConfiguracion(Constantes.USUARIOS_ADMIN_OFERTAS, username));
	            model.addAttribute("permiso_reglas",
	                    configuracionSistemaService.buscarConfiguracion(Constantes.USUARIOS_REGLAS_ASIGNACION, username));
	            model.addAttribute("permiso_admin_sms",
	                    configuracionSistemaService.buscarConfiguracion(Constantes.USUARIOS_SMS, username));
	            model.addAttribute("permiso_exclusiones",
	                    configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_EXCLUSION_NOMINA, username));
	            model.addAttribute("lista_param_conf", configuraciones);

	            rutaDireccion = "menuProductos/inicioParametros";
	        } else {
	            rutaDireccion = rutaError;
	        }

	        return rutaDireccion;

	    } catch (Exception e) {
	        logger.error("Error al cargar parámetros de configuración.", e);
	        return rutaError;
	    }
	}

	public int validarAcceso(String username) {

	    List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(username);

	    if (usuario == null || usuario.isEmpty()) {
	        logger.warn("No se encontró usuario para validar acceso: " + username);
	        return 0;
	    }

	    return iRelRolOpcionesRepository.validarAcceso(
	            usuario.get(0).getRol_idRol().getPk_id_rol(),
	            opcionesMenuService.buscarRelacion(nombrePantalla)
	    );
	}

	private String obtenerUsernameAutenticado() {

	    if (SecurityContextHolder.getContext().getAuthentication() == null) {
	        return null;
	    }

	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof UserDetails) {
	        return ((UserDetails) principal).getUsername();
	    }

	    return null;
	}

}
