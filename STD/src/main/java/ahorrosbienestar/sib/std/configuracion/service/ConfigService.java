package ahorrosbienestar.sib.std.configuracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;

@Service
public class ConfigService {

	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	public ConfigService(IConfiguracionRepository iConfiguracionRepository) {
		this.iConfiguracionRepository = iConfiguracionRepository;
	}

	// Metodo para buscar todos los usuarios
	public List<Configuracion> findAll() {
		return iConfiguracionRepository.findAll();
	}

	// Metodo para cambiar el valor de la longitud
	public int cambiarLongitud(String longitud) {
		return iConfiguracionRepository.cambiarLongitud(longitud);
	}

	// Metodo para cambiar el valor de los caracteres
	public int cambiarCaracteres(String caracteres) {
		return iConfiguracionRepository.cambiarCaracteres(caracteres);
	}

	// Metodo para cambiar el valor de los numeros
	public int cambiarNumeros(String numeros) {
		return iConfiguracionRepository.cambiarNumeros(numeros);
	}

	// Metodo para cambiar el valor de las minusculas
	public int cambiarMinusculas(String minusculas) {
		return iConfiguracionRepository.cambiarMinusculas(minusculas);
	}

	// Metodo para cambiar el valor de las mayusculas
	public int cambiarMayusculas(String mayusculas) {
		return iConfiguracionRepository.cambiarMayusculas(mayusculas);
	}

	// Metodo para cambiar el valor del periodo de bloqueo
	public int cambiarPeriodoBloqueo(String pBloqueo) {
		return iConfiguracionRepository.cambiarPeriodoBloqueo(pBloqueo);
	}

	// Metodo para cambiar el valor del periodo de dias
	public int cambiarPeriodoDias(String pDias) {
		return iConfiguracionRepository.cambiarPeriodoDias(pDias);
	}

	// Metodo para cambiar el valor del periodo de intentos de inicio de sesion
	public int cambiarIntentos(String intentos) {
		return iConfiguracionRepository.cambiarIntentos(intentos);
	}

	// Metodo para cambiar el valor de la contraseña por default
	public int cambiarContrasenaDefault(String contrasena) {
		return iConfiguracionRepository.cambiarContrasenaDefault(contrasena);
	}
	
	public List<Configuracion> obtenerInfoBDCore(){
		return iConfiguracionRepository.obtenerInfoBDCore();
	}
	
	public Configuracion obtenerConfiguracion(Integer configuracionID) {
		return iConfiguracionRepository.findById(configuracionID).orElse(null);
	}

}
