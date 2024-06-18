package ahorrosbienestar.sib.std.comunes.paramConstantes.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.paramConstantes.model.ConstantesParametros;
import ahorrosbienestar.sib.std.comunes.paramConstantes.repository.IConstantesParametros;

import java.util.HashMap;
import java.util.List;



@Service
public class ConstantesParametrosService {

@Autowired
private IConstantesParametros iConstantesParametros;

	public Map<String, String> obtenerConstantes() {
		Map<String, String> constantesMap = new HashMap<>();
		List<ConstantesParametros> constantes = iConstantesParametros.findAll();

		for (ConstantesParametros constante : constantes) {
			constantesMap.put(constante.getVc_clave(), constante.getVc_valor());
		}

    return constantesMap;
	}
	
	public String obtenerValorPorClave(String clave) {
		ConstantesParametros constante = iConstantesParametros.findByClave(clave);
	        return (constante != null) ? constante.getVc_valor() : null;
	    }
}
