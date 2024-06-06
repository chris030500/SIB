package ahorrosbienestar.sib.std.sccb.solicitudesCredito.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository.ISolicitudesCreditoRepository;

@Service
public class SolicitudesCreditoService {
	
	@Autowired
	private ISolicitudesCreditoRepository iSolicitudesCreditoRepository;
	
	public List<Map<String, Object>> searchByIdCreditoSolicitudes(Integer idCredito) {
		return iSolicitudesCreditoRepository.getByIdCreditoSolicitudes(idCredito);
	}
	
	public List<Map<String, Object>> searchByNoClienteSolicitudes(Integer noCliente) {
		return iSolicitudesCreditoRepository.getByNoClienteSolicitudes(noCliente);
	}
	
	public List<Map<String, Object>> searchByNomClienteSolicitudes(String nomCliente, Integer idCredito) {
		return iSolicitudesCreditoRepository.getByNomClienteSolicitudes(nomCliente, idCredito);
	}
}
