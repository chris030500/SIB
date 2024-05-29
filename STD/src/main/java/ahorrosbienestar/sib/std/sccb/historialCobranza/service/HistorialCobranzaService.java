package ahorrosbienestar.sib.std.sccb.historialCobranza.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialCobranza;
import ahorrosbienestar.sib.std.sccb.historialCobranza.repository.IHistorialCobranzaRepository;

@Service
public class HistorialCobranzaService {
	
	@Autowired
	private IHistorialCobranzaRepository iHistorialCobranzaRepository;
	
	public Optional<HistorialCobranza> searchDetalleExpediente(Integer idExpediente) {
		
		return iHistorialCobranzaRepository.getDetalleExpediente(idExpediente);
	}
	
	public Optional<HistorialCobranza> searchDatosGeneralesHistorialCobranza(Integer idExpediente) {
		
		return iHistorialCobranzaRepository.getDatosGeneralesHistorialCobranza(idExpediente);
	}
}
