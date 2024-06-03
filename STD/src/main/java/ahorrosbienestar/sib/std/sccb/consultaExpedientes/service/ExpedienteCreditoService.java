package ahorrosbienestar.sib.std.sccb.consultaExpedientes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository.IExpedienteCreditoRepository;

@Service
public class ExpedienteCreditoService {
	
	@Autowired
	private IExpedienteCreditoRepository iExpedienteCreditoRepository;
	
	public Optional<ExpedienteCredito> searchExpedienteCreditoByIdCredito(Integer idCredito) {
		return iExpedienteCreditoRepository.getExpedienteCreditoByIdCredito(idCredito);
	}
 }
