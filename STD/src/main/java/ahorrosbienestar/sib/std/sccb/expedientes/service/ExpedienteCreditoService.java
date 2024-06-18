package ahorrosbienestar.sib.std.sccb.expedientes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.expedientes.model.ExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.expedientes.repository.IExpedienteCreditoRepository;

@Service
public class ExpedienteCreditoService {
	
	@Autowired
	private IExpedienteCreditoRepository iExpedienteCreditoRepository;
	
	public Optional<ExpedienteCredito> searchExpedienteCreditoByIdCredito(Integer idCredito) {
		return iExpedienteCreditoRepository.getExpedienteCreditoByIdCredito(idCredito);
	}
	
	public List<Map<String, Object>> searcNamesExpedienteCredito(String nombre) {
		return iExpedienteCreditoRepository.getNamesExpedienteCredito(nombre);
	}
 }
