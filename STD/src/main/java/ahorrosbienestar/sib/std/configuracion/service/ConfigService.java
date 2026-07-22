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
	//Obtener informacion del servidor de reportes jasper
	public List<Configuracion> obtenerConfReportes(){
		return iConfiguracionRepository.obtenerConfReportes();
	}
	public Configuracion obtenerConfiguracion(Integer configuracionID) {
		return iConfiguracionRepository.findById(configuracionID).orElse(null);
	}

	public List<Configuracion>  obtenerUrlCSO() {
		return iConfiguracionRepository.obtenerUrlCSO();
	}
	
	public Configuracion valorPropiedad(String valor) {
		return iConfiguracionRepository.valorPropiedad(valor);
	}
	
	public Integer obtenerPeriodoBloqueoMinutos() {
	    return obtenerValorEntero("pBloqueo", 35);
	}

	public Integer obtenerPeriodoDiasPassword() {
	    Configuracion config = valorPropiedad("pDias");
	    return Integer.parseInt(config.getVc_valor_propiedad().trim());
	}

	public Integer obtenerIntentosMaximos() {

	    Configuracion config = valorPropiedad("intentos");

	    return Integer.parseInt(config.getVc_valor_propiedad().trim());
	}

	private Integer obtenerValorEntero(String propiedad, Integer valorDefault) {
	    Configuracion config = valorPropiedad(propiedad);

	    if (config == null || config.getVc_valor_propiedad() == null) {
	        return valorDefault;
	    }

	    try {
	        return Integer.parseInt(config.getVc_valor_propiedad().trim());
	    } catch (Exception e) {
	        return valorDefault;
	    }
	}
	
	

}
