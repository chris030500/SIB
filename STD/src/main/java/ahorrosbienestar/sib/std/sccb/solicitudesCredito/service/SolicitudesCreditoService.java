package ahorrosbienestar.sib.std.sccb.solicitudesCredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository.ISolicitudesCreditoRepository;

@Service
public class SolicitudesCreditoService {
	
	@Autowired
	private ISolicitudesCreditoRepository iSolicitudesCreditoRepository;
	
	public List<SolicitudesCredito> searchByIdCreditoSolicitudes(Integer idCredito) {
		return iSolicitudesCreditoRepository.getByIdCreditoSolicitudes(idCredito);
	}
	
	public List<SolicitudesCredito> searchByNoClienteSolicitudes(Integer noCliente) {
		return iSolicitudesCreditoRepository.getByNoClienteSolicitudes(noCliente);
	}
	
	public List<SolicitudesCredito> searchByNomClienteSolicitudes(String nomCliente) {
		return iSolicitudesCreditoRepository.getByNomClienteSolicitudes(nomCliente);
	}
}
