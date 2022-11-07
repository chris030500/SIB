package ahorrosbienestar.sib.std.configuracion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void procActualizarSesion(int dias) {
		jdbcTemplate.update("call SIB.SP_ACTUALIZAR_SESION (?)", dias);
	}

	public int obtenerParametros(String contrasena, int longitud, int mayusculas, int minusculas, int numeros,
			int caracteres) {
		int mensaje = 0;
		int contarNum = 0;
		int contarMayus = 0;
		int contarMinus = 0;
		int contarCaracteres = 0;
		char clave;
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		if (contrasena.length() > longitud) {
			System.out.println("La contraseña excede el número de la longitud");
			
			return mensaje = 1;

		} else if (contrasena.length() == longitud) {
			System.out.println("Puede continuar, cumple con la longitud");
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
				System.out.println("Si cumple con los parametros requeridos");
				System.out.println("conteo de mayusculas: " + contarMayus);
				System.out.println("conteo de minusculas: " + contarMinus);
				System.out.println("conteo de numeros: " + contarNum);
				System.out.println("conteo de especiales: " + contarCaracteres);
				return mensaje = 2;
			} else {
				System.out.println("La contraseña debe de tener los siguientes parametros");
				System.out.println("Debe de tener " + mayusculas + " mayusculas y se han agregado " + contarMayus);
				System.out.println("Debe de tener " + minusculas + " minusculas y se han agregado " + contarMinus);
				System.out.println("Debe de tener " + numeros + " caracteres numericos y se han agregado " + contarNum);
				System.out.println("Debe de tener " + caracteres + " caracteres especiales y se han agregado " + contarCaracteres);
				return mensaje = 3;
			}


		} else if (contrasena.length() < longitud) {
			System.out.println("La contraseña es menor a la longitud establecida");
			return mensaje = 4;
		}

		return mensaje = 0;

	}
	
	public String mensajeParametros(String contrasena, int longitud, int mayusculas, int minusculas, int numeros,
			int caracteres) {
		String mensaje = "";
		int contarNum = 0;
		int contarMayus = 0;
		int contarMinus = 0;
		int contarCaracteres = 0;
		char clave;
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		if (contrasena.length() > longitud) {
			return mensaje = "La contraseña excede el número de la longitud";
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
				
				return mensaje = "Si cumple con los parametros requeridos";
			} else {
				
				return mensaje = "La contraseña debe de tener los siguientes parametros: \n" +
						"Debe de tener " + mayusculas + " mayusculas y se han agregado " + contarMayus+ "\n"+
						"Debe de tener " + minusculas + " minusculas y se han agregado " + contarMinus+ "\n" +
						"Debe de tener " + numeros + " caracteres numericos y se han agregado " + contarNum+ "\n"+
						"Debe de tener " + caracteres + " caracteres especiales y se han agregado " + contarCaracteres;
			}


		} else if (contrasena.length() < longitud) {
			
			return mensaje = "La contraseña es menor a la longitud establecida";
		}

		return mensaje = "No ha ingresado una contraseña";

	}
}
