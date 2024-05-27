package ahorrosbienestar.sib.std.sccb.historialCobranza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialAsignacion;
import ahorrosbienestar.sib.std.sccb.historialCobranza.repository.IHistorialAsignacionRepository;

@Service
public class HistorialAsignacionService {
	
	@Autowired
	private IHistorialAsignacionRepository iHistorialAsignacionRepository;
	
	public List<HistorialAsignacion> searchByIdCreditoHistorialAsig(Integer idCredito) {
		return iHistorialAsignacionRepository.getByIdCreditoHistorialAsignacion(idCredito);
	}
}
