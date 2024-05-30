package ahorrosbienestar.sib.std.sccb.consultaPagares.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.consultaPagares.model.ConsultaPagares;
import ahorrosbienestar.sib.std.sccb.consultaPagares.repository.IConsultaPagaresRepository;

@Service
public class ConsultaPagaresService {
	
	@Autowired
	private IConsultaPagaresRepository iPagaresRepository;
	
	public List<ConsultaPagares> searchByIdCreditoPagares(Integer idCredito) {
		return iPagaresRepository.getByIdCreditoConsultaPagares(idCredito);
	}
	
	public List<ConsultaPagares> searchByNoClientePagares(Integer noCliente) {
		return iPagaresRepository.getBynoClienteConsultaPagares(noCliente);
	}
}
