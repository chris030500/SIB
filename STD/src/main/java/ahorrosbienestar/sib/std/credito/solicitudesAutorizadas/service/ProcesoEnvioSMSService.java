package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ProcesoEnvioSMS;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository.IProcesoEnvioSMSRepository;

@Service
public class ProcesoEnvioSMSService {
	
	private static final Logger logger = Logger.getLogger(ProcesoEnvioSMSService.class);
	
	@Autowired
	private IProcesoEnvioSMSRepository gestionRepository;
	
	public List<Map<String, Object>> getAllGestionesSMS() {
		gestionRepository.setLocaleSpanish();
		return gestionRepository.getAllGestionesSMS();
	}
	
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer idProcess) {
		return gestionRepository.getCurrentProcessSMS(idProcess);
	}
	
	public ProcesoEnvioSMS updateProcessStatus(Integer idProcess, Integer status, Integer idUser) {
		try {
			
			ProcesoEnvioSMS procesoSMS = gestionRepository.findById(idProcess)
					.orElseThrow(() -> new EntityNotFoundException("GestiónSMS no encontrado con id " + idProcess));
			
			if (procesoSMS != null) {
				if (status == 4) {
					LocalDateTime currentDateTime = LocalDateTime.now();
					procesoSMS.setEstatusProceso(status);
					procesoSMS.setFechaCancelacion(currentDateTime);
					procesoSMS.setUserCancela(idUser);
					return gestionRepository.save(procesoSMS);
					
				} else {
					procesoSMS.setEstatusProceso(status);
					return gestionRepository.save(procesoSMS);
				}
			} 
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
		}
		
		return null;
	}
}
