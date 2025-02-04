package ahorrosbienestar.sib.std.smsconfig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.smsconfig.repository.IDetalleGestionSMSRepository;

@Service
public class DetalleGestionSMSService {
	
	@Autowired
	private IDetalleGestionSMSRepository detalleRepository;
	
	public void updateEnviarMensaje(Integer idProceso, String enviarSMS) {
		detalleRepository.updateEnviarMensaje(idProceso, enviarSMS);
	}
}
