package ahorrosbienestar.sib.std.docto_envio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.docto_envio.repository.IDoctEnvioRepository;


@Service
public class DoctEnvioService {
private IDoctEnvioRepository iDoctEnvioRepository;
	
	@Autowired
	public DoctEnvioService(IDoctEnvioRepository iDoctEnvioRepository) {
		this.iDoctEnvioRepository = iDoctEnvioRepository;
	}
	
	//Metodo para guardar la solicitud de envio
	public DoctEnvio save(DoctEnvio doctEnvio) {
		return iDoctEnvioRepository.save(doctEnvio);
	}
}
