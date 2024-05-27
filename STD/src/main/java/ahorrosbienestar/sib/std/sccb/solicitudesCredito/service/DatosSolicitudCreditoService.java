package ahorrosbienestar.sib.std.sccb.solicitudesCredito.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository.IDatosSolicitudCreditoRepository;

@Service
public class DatosSolicitudCreditoService {
	
	@Autowired
	private IDatosSolicitudCreditoRepository iDatosSolicitudRepository;
	
	public Optional<DatosSolicitudCredito> searchDatosSolicitud(Integer idSolicitud) {
		return iDatosSolicitudRepository.getDatosSolicitudCredito(idSolicitud);
	}

}
