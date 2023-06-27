package ahorrosbienestar.sib.std.configuracion.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:confContrasena.properties")
public class ConfContrasena {
	
	@Value("${longitud}")
	private String longitud;
	
	@Value("${mayusculas}") 
	private String mayusculas;
	
	@Value("${minusculas}") 
	private String minusculas;
	
	@Value("${numeros}") 
	private String numeros;
	
	@Value("${caracteres}") 
	private String caracteres;
	

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getMayusculas() {
		return mayusculas;
	}

	public void setMayusculas(String mayusculas) {
		this.mayusculas = mayusculas;
	}

	public String getMinusculas() {
		return minusculas;
	}

	public void setMinusculas(String minusculas) {
		this.minusculas = minusculas;
	}

	public String getNumeros() {
		return numeros;
	}

	public void setNumeros(String numeros) {
		this.numeros = numeros;
	}

	public String getCaracteres() {
		return caracteres;
	}

	public void setCaracteres(String caracteres) {
		this.caracteres = caracteres;
	}
	
	
	
}
