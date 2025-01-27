package ahorrosbienestar.sib.std.smsconfig.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.GestionSMS;
import ahorrosbienestar.sib.std.smsconfig.repository.IGestionSMSRepository;

@Service
public class GestionSMSService {
	
	private static final Logger logger = Logger.getLogger(GestionSMSService.class);
	
	@Autowired
	private IGestionSMSRepository gestionRepository;
	
	public List<Map<String, Object>> getGestionesSMSByTipoCredito(Integer tipoProceso, String tipoCredito) {
		gestionRepository.setLocaleSpanish(); // Configurar el idioma
		return gestionRepository.getGestionesSMSByTipoCredito(tipoProceso, tipoCredito);
	}
	
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer tipoProceso, Integer idProcess) {
		gestionRepository.setLocaleSpanish();
		return gestionRepository.getCurrentProcessSMS(tipoProceso, idProcess);
	}
	
	public GestionSMS updateProcessStatus(Integer idProcess, Integer status, Integer idUser) {
		try {
			
			GestionSMS procesoSMS = gestionRepository.findById(idProcess)
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
