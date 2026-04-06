package ahorrosbienestar.sib.std.index.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
	public ResponseEntity<?> guardarCambioContrasena(@RequestParam("password") String password) throws IOException {
		logger.info("Ejecutando guardarCambioContrasena");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Buscamos la información del usuario
		Usuario usu = usuarioService.findByNombre(auth.getName());
		String valorUltimasContrasenas = configService.findAll().get(8).getVc_valor_propiedad().toString();

		List<HistContrasena> ultimasContrasenas = histContrasenaService.findTopByUsuarioByFechaDesc(usu.getPk_id_usuario(),
				Integer.parseInt(valorUltimasContrasenas));
		boolean coincideContrasena = false;
		for (HistContrasena historicoContrasena : ultimasContrasenas) {
			if (BCrypt.checkpw(password, historicoContrasena.getVc_contrasenia())) {
				coincideContrasena = true;

				ResponseEntity.notFound().build();

				// return ResponseEntity.ok("La nueva contraseña coincide con una de las últimas
				// 3 contraseñas");
				break;// La nueva contraseña coincide con una de las últimas 3 contraseñas
			}
		}
		
		if (coincideContrasena) {
			logger.error("La nueva contraseña coincide con una de las últimas " + "contraseñas");
			return ResponseEntity.badRequest().body("La nueva contraseña coincide con una de las últimas 3 contraseñas");
		}
		logger.info("Puede realizar el cambio de contraseña");

		// logger.info("Password: " + password);

		logger.info("Pk_Usu: " + usu.getPk_id_usuario());
		// Con el id del usuario vamos a dar de baja la contraseña que se tiene
		int hisContrasena = histContrasenaService.bajaContrasena(usu.getPk_id_usuario());

		// Vamos a obtener el parametro de los días de vigencia de la contraseña
//		Properties properties = new Properties();
//		InputStream inputStream = new FileInputStream("src/main/resources/confSesion.properties");
//		properties.load(inputStream);
//
//		properties.getProperty("pDias");

		String pDias = configService.findAll().get(7).getVc_valor_propiedad().toString();
		logger.info("Días que va a estar activa la contraseña: " + pDias);
		// Establecemos el formato de la fecha
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Obtenemos la fecha en String
		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		// Casteamos la fechaActual a localDate
		LocalDate localDate = LocalDate.parse(fechaActual);
		LocalDate localDateHoy = LocalDate.parse(fechaActual);
		// Vamos a agregarle los días de vigencía conforme al parametro ya establecido.

		localDate = localDate.plusDays(Long.parseLong(pDias));

		HistContrasena hc = new HistContrasena();
		hc.setDt_vig_cont_fin(localDate);
		hc.setDt_vig_cont_inicio(localDateHoy);
		hc.setVc_indicador_activo(1);
		hc.setUsr_historico_contrasena_id(usu);
		hc.setVc_contrasenia(passwordEncoder.encode(password));
		// histContrasenaService.save(hc);
		logger.info("Guardar en historico");
		// return null;
		return ResponseEntity.ok(histContrasenaService.save(hc));
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
