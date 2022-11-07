package ahorrosbienestar.sib.std.configuracion.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.configuracion.model.ConfContrasena;
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

	// Cargamos la vista de la configuracion de los parametros de la contraseña

	@GetMapping("/contrasena")
	public String Inicio(Model model) throws IOException {
		model.addAttribute("titulo", "configuracion");

		// Establecemos el nombre del archivo properties
		InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");

		Properties properties = new Properties();
		properties.load(inputStream);
		model.addAttribute("longitud", properties.getProperty("longitud"));
		model.addAttribute("mayusculas", properties.getProperty("mayusculas"));
		model.addAttribute("minusculas", properties.getProperty("minusculas"));
		model.addAttribute("numeros", properties.getProperty("numeros"));
		model.addAttribute("caracteres", properties.getProperty("caracteres"));

		return "configuracion/configuracionContrasena";
	}

	// Guardamos la configuracion de los parametros de la contraseña
	@PostMapping("/guardarConfContrasena")
	public String guardarConfContrasena(Model model, @RequestParam(name = "longitud", required = false) String longitud,
			@RequestParam(name = "mayusculas", required = false) String mayusculas,
			@RequestParam(name = "minusculas", required = false) String minusculas,
			@RequestParam(name = "numeros", required = false) String numeros,
			@RequestParam(name = "caracteres", required = false) String caracteres) {

		String max = longitud;

		String minUppercase = mayusculas;

		String minLowercase = minusculas;

		String numDigitos = numeros;

		String especial = caracteres;

		// Establecemos el nombre del archivo properties
		final String PROPERTIES_FILE = "confContrasena.properties";

		Calendar lastAccess = null;

		Properties properties = new Properties();

		try {
			properties.load(new BufferedReader(new FileReader(PROPERTIES_FILE)));
			max = properties.getProperty("longitud", max);
			minUppercase = properties.getProperty("mayusculas", mayusculas);
			minLowercase = properties.getProperty("minusculas", minusculas);
			numDigitos = properties.getProperty("numeros", numeros);
			especial = properties.getProperty("caracteres", caracteres);
			// La fecha y hora del último acceso está guardado como tiempo en milisegundos
			long lastAccessInMillis = Long.valueOf(properties.getProperty("lastAccess"));
			lastAccess = Calendar.getInstance();
			lastAccess.setTimeInMillis(lastAccessInMillis);
		} catch (Exception ex) {
			// Si hay algún error, por ejemplo que no exista el archivo de propiedades

		}

		// Muestra en pantalla los valores de las variables language y lastAccess
		System.out.println("longitud: " + longitud);
		if (lastAccess != null) {
			String strLastAccess = (DateFormat.getDateTimeInstance()).format(lastAccess.getTime());
			System.out.println("Ultimo acceso: " + strLastAccess);
		} else {
			System.out.println("Es la primera vez que usa esta aplicación");
		}

		properties.setProperty("longitud", longitud);
		properties.setProperty("mayusculas", mayusculas);
		properties.setProperty("minusculas", minusculas);
		properties.setProperty("numeros", numeros);
		properties.setProperty("caracteres", caracteres);
		// Toma el valor que se almacenará en la propiedad lastAccess
		// en función de la hora actual, y en formato de milisegundos
		lastAccess = Calendar.getInstance();
		String lastAccessInMillis = String.valueOf(lastAccess.getTimeInMillis());
		properties.setProperty("lastAccess", lastAccessInMillis);

		try {
			// Crea el archivo de propiedades
			properties.store(new BufferedWriter(new FileWriter("src/main/resources/" + PROPERTIES_FILE)),
					"Configuración de contraseña");
		} catch (IOException ex) {
			System.out.println("Error: No se ha podido crear el archivo de propiedades");
		}
		// redirAttrs.addFlashAttribute("message", "This is message from flash");
		return "redirect:/configuracion/contrasena";
	}

	// Cargamos la vista de la configuracion del bloqueo de sesiones
	@GetMapping("/bloqueoSesiones")
	public String Sesiones(Model model) throws IOException {
		model.addAttribute("titulo", "Configuración de sesiones");

		InputStream inputStream = new FileInputStream("src/main/resources/confSesion.properties");

		Properties properties = new Properties();
		properties.load(inputStream);
		model.addAttribute("intentos", properties.getProperty("intentos"));
		model.addAttribute("pBloqueo", properties.getProperty("pBloqueo"));
		model.addAttribute("pDias", properties.getProperty("pDias"));
		return "configuracion/bloqueoSesiones";
	}

	@PostMapping("/guardarBloqueoSesiones")
	public String guardarBloqueoSesiones(Model model, @RequestParam(name = "intentos") String intentos,
			@RequestParam(name = "pBloqueo") String pBloqueo, @RequestParam(name = "pDias") String pDias) {

		String numIntentos = intentos;

		String peridoBloqueo = pBloqueo;

		String peridoDias = pDias;

		final String PROPERTIES_FILE = "confSesion.properties";

		Calendar ultimoAcceso = null;

		Properties properties = new Properties();

		try {
			properties.load(new BufferedReader(new FileReader(PROPERTIES_FILE)));
			numIntentos = properties.getProperty("intentos", intentos);
			peridoBloqueo = properties.getProperty("pBloqueo", pBloqueo);
			peridoDias = properties.getProperty("pDias", pDias);
			;
			// La fecha y hora del último acceso está guardado como tiempo en milisegundos
			long ultimoAccesoMillis = Long.valueOf(properties.getProperty("ultimoAcceso"));
			ultimoAcceso = Calendar.getInstance();
			ultimoAcceso.setTimeInMillis(ultimoAccesoMillis);
		} catch (Exception ex) {
			// Si hay algún error, por ejemplo que no exista el archivo de propiedades

		}

		// Muestra en pantalla los valores de las variables language y lastAccess
		System.out.println("intentos: " + intentos);
		System.out.println("pDias: " + pDias);
		if (ultimoAcceso != null) {
			String strultimoAcceso = (DateFormat.getDateTimeInstance()).format(ultimoAcceso.getTime());
			System.out.println("Ultimo acceso: " + strultimoAcceso);
		} else {
			System.out.println("Es la primera vez que usa esta aplicación");
		}

		properties.setProperty("intentos", intentos);
		properties.setProperty("pBloqueo", pBloqueo);
		properties.setProperty("pDias", pDias);

		// Toma el valor que se almacenará en la propiedad ultimoAcceso
		// en función de la hora actual, y en formato de milisegundos
		ultimoAcceso = Calendar.getInstance();
		String ultimoAccesoMillis = String.valueOf(ultimoAcceso.getTimeInMillis());
		properties.setProperty("lastAccess", ultimoAccesoMillis);

		try {
			// Crea el archivo de propiedades
			properties.store(new BufferedWriter(new FileWriter("src/main/resources/" + PROPERTIES_FILE)),
					"Configuración de sesiones");
		} catch (IOException ex) {
			System.out.println("Error: No se ha podido crear el archivo de propiedades");
		}
		configuracionService.procActualizarSesion(Integer.parseInt(pDias));

		return "redirect:/configuracion/bloqueoSesiones";
	}

	// Mostrar apartado de sesiones inactivas
	@GetMapping("/sesionesInactiva")
	public String SesionesInactivas(Model model) throws IOException {
		model.addAttribute("titulo", "Sesiones inactivas");
		return "configuracion/bloqueoInactividad";
	}

	// Desbloqueo de sesión o cuenta del usuario
	@PostMapping("/cambioSesiones")
	public String CambioSesiones(@RequestParam(name = "desbloqueo", required = true) String desbloqueo,
			@RequestParam(name = "selectUsuario", required = true) String[] selectUsuario) {

//		System.out.println("Usted a seleccionado la opción: " +desbloqueo);
//		System.out.println("Ha seleccionado el usuario : " + Arrays.toString(selectUsuario).toString());
		Integer i = Integer.parseInt(selectUsuario[0]);
//		System.out.println("i :" + i);
		if (desbloqueo.equals("desbloquearSesion")) {
			System.out.println("Entro en desbloquear sesion");
			usuarioService.activarUsuarioSesion(i);

		} else if (desbloqueo.equals("desbloquearCuenta")) {
			System.out.println("Entro en desbloquear cuenta");
			usuarioService.activarUsuarioCuenta(i);
		}
		return "redirect:/configuracion/sesionesInactiva";
	}

	// Se agregan los parametros de la contraseña
	@GetMapping("/generarContrasena")
	public String GenerarContrasena(Model model) throws IOException {

		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");
		properties.load(inputStream);
		model.addAttribute("longitud", properties.getProperty("longitud"));
		model.addAttribute("mayusculas", properties.getProperty("mayusculas"));
		model.addAttribute("minusculas", properties.getProperty("minusculas"));
		model.addAttribute("numeros", properties.getProperty("numeros"));
		model.addAttribute("caracteres", properties.getProperty("caracteres"));
		return "configuracion/generacionContrasena";
	}

	// Se guarda la contraseña que se haya establecido
	@PostMapping("/guardarContrasena")
	public String GuardarContrasena(@RequestParam("contrasena") String contrasena, HistContrasena histContrasena) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
        String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
        LocalDate localDate = LocalDate.parse(fechaActual);
		Properties properties = new Properties();
		//Primero consultamos la contraseña que se ha almacenado con anterioridad
		InputStream inputStream = new FileInputStream("src/main/resources/contrasena.properties");
		properties.load(inputStream);
		String contrasenaAnterior = properties.getProperty("contrasena");
		System.out.println("contrasenaAnterior: " + contrasenaAnterior);
		
		//Se agregan los campos para cada campo del historico de contraseña
		//NOTA: FALTA AGREGAR EL USUARIO QUE REALIZA CIERTA MODIFICACIÓN Y LA FECHA DE VIGENCIA
		histContrasena.setVc_contrasenia(contrasenaAnterior);
		histContrasena.setDt_vig_cont_inicio(localDate);
		histContrasena.setVc_indicador_activo("ACTIVO");
		histContrasena.setDt_vig_cont_fin(localDate);
		System.out.println("Historico de contraseña: " + histContrasena);
		histContrasenaService.save(histContrasena);
		//histContrasena.setUsr_historico_contrasena_id(1L).get();
		
		String contrasenia = contrasena;
		// Establecemos el nombre del archivo properties
		final String PROPERTIES_FILE = "contrasena.properties";
		
		Calendar lastAccess = null;
		
		try {
			//Cargamos el archivo properties
			properties.load(new BufferedReader(new FileReader(PROPERTIES_FILE)));
			contrasenia = properties.getProperty("contrasena", contrasenia);
			// La fecha y hora del último acceso está guardado como tiempo en milisegundos
			long lastAccessInMillis = Long.valueOf(properties.getProperty("lastAccess"));
			lastAccess = Calendar.getInstance();
			lastAccess.setTimeInMillis(lastAccessInMillis);
		} catch (Exception ex) {
			// Si hay algún error, por ejemplo que no exista el archivo de propiedades

		}
		
		if (lastAccess != null) {
			String strLastAccess = (DateFormat.getDateTimeInstance()).format(lastAccess.getTime());
			System.out.println("Ultimo acceso: " + strLastAccess);
		} else {
			System.out.println("Es la primera vez que usa esta aplicación");
		}
		
		properties.setProperty("contrasena", contrasena);
		lastAccess = Calendar.getInstance();
		String lastAccessInMillis = String.valueOf(lastAccess.getTimeInMillis());
		properties.setProperty("lastAccess", lastAccessInMillis);
		try {
			// Crea el archivo de propiedades
			properties.store(new BufferedWriter(new FileWriter("src/main/resources/" + PROPERTIES_FILE)), "Configuración de sesiones");
		} catch (IOException ex) {
			System.out.println("Error: No se ha podido crear el archivo de propiedades");
		}
		
		return "redirect:/configuracion/generarContrasena";

	}

	// Mostrar los mensajes que le corresponden al momento de agregar la contraseña
	@GetMapping("/mensajeContrasena")
	public ResponseEntity<?> mensajeContrasena(@RequestParam("contrasena") String contrasena) throws IOException {
		InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");

		Properties properties = new Properties();
		properties.load(inputStream);

		return ResponseEntity.ok(
				configuracionService.mensajeParametros(contrasena, Integer.parseInt(properties.getProperty("longitud")),
						Integer.parseInt(properties.getProperty("mayusculas")),
						Integer.parseInt(properties.getProperty("minusculas")),
						Integer.parseInt(properties.getProperty("numeros")),
						Integer.parseInt(properties.getProperty("caracteres"))));
	}


}
