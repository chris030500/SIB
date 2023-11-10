package ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.model.HistorialModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.repository.HistorialRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HistorialService {

	@Autowired
	private HistorialRepository historialRepository;

	public List<HistorialModel> historialDeBusqueda() {
		return historialRepository.historialDeBusqueda();
	}

}
