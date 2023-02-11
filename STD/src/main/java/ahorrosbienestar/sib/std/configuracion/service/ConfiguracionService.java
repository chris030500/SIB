package ahorrosbienestar.sib.std.configuracion.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	public void procActualizarSesion(int dias) {
		logger.info("Ejecutando actualizar sesión");
		jdbcTemplate.update("call SIB.SP_ACTUALIZAR_SESION (?)", dias);
	}

	public int obtenerParametros(String contrasena, int longitud, int mayusculas, int minusculas, int numeros,
			int caracteres) {
		logger.info("Ejecutando obtener parametros");
		int mensaje = 0;
		int contarNum = 0;
		int contarMayus = 0;
		int contarMinus = 0;
		int contarCaracteres = 0;
		char clave;
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		if (contrasena.length() > longitud) {
			logger.info("mensaje :" + mensaje);
			return mensaje = 1;

		} else if (contrasena.length() == longitud) {

			for (int i = 0; i < contrasena.length(); i++) {
				clave = contrasena.charAt(i);
				String passValue = String.valueOf(clave);
				if (passValue.matches("[A-Z]")) {
					contarMayus++;
				} else if (passValue.matches("[a-z]")) {
					contarMinus++;
				} else if (passValue.matches("[0-9]")) {
					contarNum++;
				} else if (specialChars.contains(String.valueOf(clave))) {
					contarCaracteres++;
				}
			}

			if (contarMayus == mayusculas && contarMinus == minusculas && contarNum == numeros
					&& contarCaracteres == caracteres) {
				logger.info("mensaje :" + mensaje);
				return mensaje = 2;
			} else {
				logger.info("mensaje :" + mensaje);
				return mensaje = 3;
			}

		} else if (contrasena.length() < longitud) {
			logger.info("mensaje :" + mensaje);
			return mensaje = 4;
		}
		logger.info("mensaje :" + mensaje);
		return mensaje = 0;

	}

	public String mensajeParametros(String contrasena, int longitud, int mayusculas, int minusculas, int numeros,
			int caracteres) {
		logger.info("mensaje parametros:");
		String mensaje = "";
		int contarNum = 0;
		int contarMayus = 0;
		int contarMinus = 0;
		int contarCaracteres = 0;
		char clave;
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		if (contrasena.length() > longitud) {
			logger.info("mensaje :" + mensaje);
			return mensaje = "La contraseña excede el número de la longitud (" + longitud + " caracteres)";
		} else if (contrasena.length() == longitud) {

			for (int i = 0; i < contrasena.length(); i++) {
				clave = contrasena.charAt(i);
				String passValue = String.valueOf(clave);
				if (passValue.matches("[A-Z]")) {
					contarMayus++;
				} else if (passValue.matches("[a-z]")) {
					contarMinus++;
				} else if (passValue.matches("[0-9]")) {
					contarNum++;
				} else if (specialChars.contains(String.valueOf(clave))) {
					contarCaracteres++;
				}
			}

			if (contarMayus == mayusculas && contarMinus == minusculas && contarNum == numeros
					&& contarCaracteres == caracteres) {
				logger.info("mensaje :" + mensaje);
				return mensaje = "Si cumple con los parametros requeridos";
			} else {
				logger.info("mensaje :" + mensaje);
				return mensaje = "La contraseña debe de tener los siguientes parametros: \n" + "Debe de tener "
						+ mayusculas + " mayusculas y se han agregado " + contarMayus + "\n" + "Debe de tener "
						+ minusculas + " minusculas y se han agregado " + contarMinus + "\n" + "Debe de tener "
						+ numeros + " caracteres numericos y se han agregado " + contarNum + "\n" + "Debe de tener "
						+ caracteres + " caracteres especiales y se han agregado " + contarCaracteres;
			}

		} else if (contrasena.length() < longitud) {
			logger.info("mensaje :" + mensaje);
			return mensaje = "La contraseña es menor a la longitud establecida(" + longitud + " caracteres)";
		}
		logger.info("mensaje :" + mensaje);
		return mensaje = "No ha ingresado una contraseña";

	}

	public static String getMD5(String contrasena) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(contrasena.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
