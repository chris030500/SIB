package ahorrosbienestar.sib.std.sccb.historialCobranza.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.Participantes;
import ahorrosbienestar.sib.std.sccb.historialCobranza.repository.IParticipantesRepository;

@Service
public class ParticipantesService {
	
	@Autowired
	private IParticipantesRepository iParticipantesRepository;
	
	public List<Participantes> searchByIdCreditoParticipantes(Integer idCredito) {
		
		return iParticipantesRepository.getByIdCreditoParticipantes(idCredito);
	}
	
 }
