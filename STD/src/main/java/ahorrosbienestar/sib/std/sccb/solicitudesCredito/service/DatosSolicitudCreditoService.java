package ahorrosbienestar.sib.std.sccb.solicitudesCredito.service;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository.IDatosSolicitudCreditoRepository;

@Service
public class DatosSolicitudCreditoService {
	
	@Autowired
	private IDatosSolicitudCreditoRepository iDatosSolicitudRepository;
	
	public Optional<Map<String, Object>> searchDatosSolicitud(Integer idSolicitud) {
		return iDatosSolicitudRepository.getDatosSolicitudCredito(idSolicitud);
	}

	public Optional<DatosSolicitudCredito> searchDatosSolicitudByIdCredito(Integer idCredito) {
		return iDatosSolicitudRepository.getDatosSolicitudByIdCredito(idCredito);
	}
	

}
