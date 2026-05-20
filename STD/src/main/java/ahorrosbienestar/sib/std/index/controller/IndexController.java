package ahorrosbienestar.sib.std.index.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.configuracion.login.service.LoginService;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaDtoService;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
public class IndexController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistContrasenaDtoService histContrasenaDtoService;

	@Autowired
	private HistContrasenaService histContrasenaService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ConfiguracionService configuracionService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private LoginService loginService;

	// protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(IndexController.class);

	@RequestMapping(value = { "/", "/inicio" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth.getName();
		Usuario usuario = usuarioService.findByNombre(nombreUsuario);
		int estatus = usuario.getI_estatus_sesion();
		logger.info("Estatus: " + estatus);
		if (auth != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(auth.getName()));
			loginService.spActivarSesion(nombreUsuario);
			logger.info("Nombre del usuario: " + nombreUsuario);
			int estatusFecha = histContrasenaDtoService.obtenerUsuarioAndFecha(nombreUsuario).get(0).valorFecha;
			logger.info("Estatus Fecha: " + estatusFecha);
			logger.info("Estatus Fecha: " + estatusFecha);
			if (estatusFecha == 1) {
				logger.info("Debe de cambiar la contraseña");
				// logger.info("Debe de cambiar la contraseña");

				return "redirect:/cambio_contrasena";
			}
			logger.info("Ingreso al sistema");
		}

//		logger.info("Cambia el estatus de sesión a activo (1)");
		// logger.info("Puede acceder al sistema");
		// loginService.spActivarSesion(nombreUsuario);
		return "index";
	}

	@RequestMapping(value = { "/cambio_contrasena" }, method = RequestMethod.GET)
	public String cambioContrasena(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Cambio de contraseña");
		return "cambioContrasena";
	}

	// Metodo que guarda la contraseña nueva al momento de almacenarla
	@RequestMapping(value = { "/guardar_contrasena" }, method = RequestMethod.POST)
	public ResponseEntity<?> guardarCambioContrasena(
	        @RequestParam("password") String password,
	        HttpServletRequest request,
	        HttpServletResponse response) throws IOException {

	    logger.info("Ejecutando guardarCambioContrasena");

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    Usuario usu = usuarioService.findByNombre(auth.getName());

	    String valorUltimasContrasenas = configService.findAll().get(8).getVc_valor_propiedad().toString();

	    List<HistContrasena> ultimasContrasenas =
	            histContrasenaService.findTopByUsuarioByFechaDesc(
	                    usu.getPk_id_usuario(),
	                    Integer.parseInt(valorUltimasContrasenas)
	            );

	    boolean coincideContrasena = false;

	    for (HistContrasena historicoContrasena : ultimasContrasenas) {
	        if (BCrypt.checkpw(password, historicoContrasena.getVc_contrasenia())) {
	            coincideContrasena = true;
	            break;
	        }
	    }

	    if (coincideContrasena) {
	        logger.error("La nueva contraseña coincide con una de las últimas contraseñas");
	        return ResponseEntity.badRequest().body("La nueva contraseña coincide con una de las últimas 3 contraseñas");
	    }

	    logger.info("Puede realizar el cambio de contraseña");

	    histContrasenaService.bajaContrasena(usu.getPk_id_usuario());

	    String pDias = configService.findAll().get(7).getVc_valor_propiedad().toString();

	    LocalDate fechaInicio = LocalDate.now();
	    LocalDate fechaVencimiento = fechaInicio.plusDays(1);

	    HistContrasena hc = new HistContrasena();
	    hc.setDt_vig_cont_inicio(fechaInicio);
	    hc.setDt_vig_cont_fin(fechaVencimiento);
	    hc.setVc_indicador_activo(1);
	    hc.setUsr_historico_contrasena_id(usu);
	    hc.setVc_contrasenia(passwordEncoder.encode(password));

	    HistContrasena guardada = histContrasenaService.save(hc);

	    // CERRAR SESIÓN CORRECTAMENTE PARA NO BLOQUEAR AL USUARIO
	    if (auth != null && auth.isAuthenticated()) {
	        String username = auth.getName();

	        loginService.spDesactivarSesion(username);

	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }

	    SecurityContextHolder.clearContext();

	    if (request.getSession(false) != null) {
	        request.getSession(false).invalidate();
	    }

	    return ResponseEntity.ok(guardada);
	}

	// Mostrar los mensajes que le corresponden al momento de agregar la contraseña
	@GetMapping("/mensajeContrasena")
	public ResponseEntity<?> mensajeContrasena(@RequestParam("contrasena") String contrasena) throws IOException {
//		InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");
//
//		Properties properties = new Properties();
//		properties.load(inputStream);
		logger.info("Ejecutando mensajeContrasena");
		String longitud = configService.findAll().get(0).getVc_valor_propiedad();
		String mayusculas = configService.findAll().get(4).getVc_valor_propiedad();
		String minusculas = configService.findAll().get(3).getVc_valor_propiedad();
		String numeros = configService.findAll().get(2).getVc_valor_propiedad();
		String caracteres = configService.findAll().get(1).getVc_valor_propiedad();

		return ResponseEntity.ok(configuracionService.mensajeParametros(contrasena, Integer.parseInt(longitud),
				Integer.parseInt(mayusculas), Integer.parseInt(minusculas), Integer.parseInt(numeros),
				Integer.parseInt(caracteres)));
	}
	
	
	

}
