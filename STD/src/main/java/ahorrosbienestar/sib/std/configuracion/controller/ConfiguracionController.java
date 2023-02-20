package ahorrosbienestar.sib.std.configuracion.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ConfiguracionService configuracionService;

	@Autowired
	private HistContrasenaService histContrasenaService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ConfigService configService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	// Cargamos la vista de la configuracion de los parametros de la contraseña

	@GetMapping("/contrasena")
	public String inicio(Model model) throws IOException {
		logger.info("Ejecutando contrasena");

		model.addAttribute("titulo", "configuracion");
		model.addAttribute("longitud", configService.findAll().get(0).getVc_valor_propiedad());
		model.addAttribute("caracteres", configService.findAll().get(1).getVc_valor_propiedad());
		model.addAttribute("numeros", configService.findAll().get(2).getVc_valor_propiedad());
		model.addAttribute("minusculas", configService.findAll().get(3).getVc_valor_propiedad());
		model.addAttribute("mayusculas", configService.findAll().get(4).getVc_valor_propiedad());

		return "configuracion/configuracionContrasena";
	}

	// Guardamos la configuracion de los parametros de la contraseña
	@PostMapping("/guardarConfContrasena")
	public String guardarConfContrasena(Model model, @RequestParam(name = "longitud", required = false) String longitud,
			@RequestParam(name = "mayusculas", required = false) String mayusculas,
			@RequestParam(name = "minusculas", required = false) String minusculas,
			@RequestParam(name = "numeros", required = false) String numeros,
			@RequestParam(name = "caracteres", required = false) String caracteres) {

		logger.info("Ejecutando guardarConfContrasena");
		configService.cambiarLongitud(longitud);
		configService.cambiarCaracteres(caracteres);
		configService.cambiarMayusculas(mayusculas);
		configService.cambiarMinusculas(minusculas);
		configService.cambiarNumeros(numeros);

		return "redirect:/configuracion/contrasena";
	}

	// Cargamos la vista de la configuracion del bloqueo de sesiones
	@GetMapping("/bloqueoSesiones")
	public String sesiones(Model model) throws IOException {
		logger.info("Ejecutando Sesiones");
		model.addAttribute("titulo", "Configuración de sesiones");
		model.addAttribute("pBloqueo", configService.findAll().get(6).getVc_valor_propiedad());
		model.addAttribute("pDias", configService.findAll().get(7).getVc_valor_propiedad());
		model.addAttribute("intentos", configService.findAll().get(8).getVc_valor_propiedad());

		return "configuracion/bloqueoSesiones";
	}

	@PostMapping("/guardarBloqueoSesiones")
	public String guardarBloqueoSesiones(@RequestParam(name = "intento") String intento,
			@RequestParam(name = "pBloqueo") String pBloqueo, @RequestParam(name = "pDias") String pDias) {
		logger.info("Ejecutando guardarBloqueoSesiones");
//		System.out.println("Intentos: " + intento);
//		System.out.println("pBloqueo: " +pBloqueo);
//		System.out.println("pdias: " + pDias);
		configService.cambiarPeriodoBloqueo(pBloqueo);
		configService.cambiarPeriodoDias(pDias);
		configService.cambiarIntentos(intento);
		configuracionService.procActualizarSesion(Integer.parseInt(pDias));

		return "redirect:/configuracion/bloqueoSesiones";
	}

	// Mostrar apartado de sesiones inactivas
	@GetMapping("/sesionesInactiva")
	public String sesionesInactivas(Model model) throws IOException {
		logger.info("Ejecutando SesionesInactivas");
		model.addAttribute("titulo", "Sesiones inactivas");
		return "configuracion/bloqueoInactividad";
	}

	// Desbloqueo de sesión o cuenta del usuario
	@PostMapping("/cambioSesiones")
	public String cambioSesiones(@RequestParam(name = "desbloqueo", required = true) String desbloqueo,
			@RequestParam(name = "selectUsuario", required = true) String[] selectUsuario) {
		logger.info("Ejecutando CambioSesiones");
		Integer i = Integer.parseInt(selectUsuario[0]);

		if (desbloqueo.equals("desbloquearSesion")) {
			// System.out.println("Entro en desbloquear sesion");
			logger.info("Ejecutando desbloquear sesion: " + i);
			usuarioService.activarUsuarioSesion(i);

		} else if (desbloqueo.equals("desbloquearCuenta")) {
			// System.out.println("Entro en desbloquear cuenta");
			logger.info("Ejecutando desbloquear cuenta: " + i);
			usuarioService.activarUsuarioCuenta(i);
		}
		return "redirect:/configuracion/sesionesInactiva";
	}

	// Se agregan los parametros de la contraseña
	@GetMapping("/generarContrasena")
	public String generarContrasena(Model model) throws IOException {

		logger.info("Ejecutando GenerarContrasena ");
//		model.addAttribute("longitud", properties.getProperty("longitud"));
//		model.addAttribute("mayusculas", properties.getProperty("mayusculas"));
//		model.addAttribute("minusculas", properties.getProperty("minusculas"));
//		model.addAttribute("numeros", properties.getProperty("numeros"));
//		model.addAttribute("caracteres", properties.getProperty("caracteres"));
		model.addAttribute("longitud", configService.findAll().get(0).getVc_valor_propiedad());
		model.addAttribute("caracteres", configService.findAll().get(1).getVc_valor_propiedad());
		model.addAttribute("numeros", configService.findAll().get(2).getVc_valor_propiedad());
		model.addAttribute("minusculas", configService.findAll().get(3).getVc_valor_propiedad());
		model.addAttribute("mayusculas", configService.findAll().get(4).getVc_valor_propiedad());
		return "configuracion/generacionContrasena";
	}

	// Se guarda la contraseña que se haya establecido
	@PostMapping("/guardarContrasena")
	public String guardarContrasena(@RequestParam("contrasena") String contrasena, HistContrasena histContrasena)
			throws IOException {
		logger.info("Ejecutando guardarContrasena ");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		LocalDate localDate = LocalDate.parse(fechaActual);
		// Properties properties = new Properties();
		// Primero consultamos la contraseña que se ha almacenado con anterioridad
		// InputStream inputStream = new
		// FileInputStream("src/main/resources/contrasena.properties");
		// properties.load(inputStream);
		// String contrasenaAnterior = properties.getProperty("contrasena");
		String contrasenaAnterior = configService.findAll().get(5).getVc_valor_propiedad();
		System.out.println("contrasenaAnterior: " + contrasenaAnterior);
		System.out.println("contraseña con Bcryp: " + passwordEncoder.encode(contrasenaAnterior));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Obtenemos el nombre del usuario autentificado
		Usuario usu = usuarioService.findByNombre(auth.getName());
		
		// Se agregan los campos para la tabla historico de contraseña
		histContrasena.setVc_contrasenia(passwordEncoder.encode(contrasenaAnterior));
		histContrasena.setDt_vig_cont_inicio(localDate);
		histContrasena.setVc_indicador_activo(1);
		histContrasena.setDt_vig_cont_fin(localDate);
		histContrasena.setUsr_historico_contrasena_id(usu);
		// System.out.println("Historico de contraseña: " + histContrasena);
		histContrasenaService.save(histContrasena);
		// histContrasena.setUsr_historico_contrasena_id(1L).get();

		String contrasenia = contrasena;
		// System.out.println("Contraseña nueva: " + contrasena);
		logger.info("Usuario que cambia la contraseña: " + auth);
		logger.info("Contraseña nueva " + contrasena);
		configService.cambiarContrasenaDefault(contrasenia);

		return "redirect:/configuracion/generarContrasena";

	}

	// Mostrar los mensajes que le corresponden al momento de agregar la contraseña
	@GetMapping("/mensajeContrasena")
	public ResponseEntity<?> mensajeContrasena(@RequestParam("contrasena") String contrasena) throws IOException {
		logger.info("mensajeContrasena");
		return ResponseEntity.ok(configuracionService.mensajeParametros(contrasena,
				Integer.parseInt(configService.findAll().get(0).getVc_valor_propiedad()),
				Integer.parseInt(configService.findAll().get(4).getVc_valor_propiedad()),
				Integer.parseInt(configService.findAll().get(3).getVc_valor_propiedad()),
				Integer.parseInt(configService.findAll().get(2).getVc_valor_propiedad()),
				Integer.parseInt(configService.findAll().get(1).getVc_valor_propiedad())));
	}

}
