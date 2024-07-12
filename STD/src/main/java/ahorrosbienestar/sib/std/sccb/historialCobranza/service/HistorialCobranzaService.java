package ahorrosbienestar.sib.std.sccb.historialCobranza.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialCobranza;
import ahorrosbienestar.sib.std.sccb.historialCobranza.repository.IHistorialCobranzaRepository;

@Service
public class HistorialCobranzaService {
	
	@Autowired
	private IHistorialCobranzaRepository iHistorialCobranzaRepository;
	
	public Optional<HistorialCobranza> searchDetalleExpediente(Integer idSolicitud) {
		
		return iHistorialCobranzaRepository.getDetalleExpediente(idSolicitud);
	}
	
	public Optional<Map<String, Object>> searchDatosGeneralesHistorialCobranza(Integer idCredito) {
		
		return iHistorialCobranzaRepository.getDatosGeneralesHistorialCobranza(idCredito);
	}
}
