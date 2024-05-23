package ahorrosbienestar.sib.std.sccb.solicitudesCredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository.ISolicitudesCreditoRepository;

@Service
public class SolicitudesCreditoService {
	@Autowired
	private ISolicitudesCreditoRepository iSolicitudesRepository;
	
	public List<SolicitudesCredito> findByCredito(Integer idCredito) {
		return iSolicitudesRepository.findByIdCredito(idCredito);
	}
	
}
