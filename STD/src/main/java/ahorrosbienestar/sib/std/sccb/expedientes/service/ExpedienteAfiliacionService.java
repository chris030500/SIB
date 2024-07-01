package ahorrosbienestar.sib.std.sccb.expedientes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.expedientes.model.ExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.expedientes.repository.IExpedienteAfiliacionRepository;

@Service
public class ExpedienteAfiliacionService {
	
	@Autowired
	private IExpedienteAfiliacionRepository iExpedienteAfiliacionService;
	
	public Optional<Map<String, Object>> searchExpedienteAfiliacionByNomCliente(String nombre) {
		return iExpedienteAfiliacionService.getExpedienteAfiliacionByName(nombre);
	}
	
	public List<Map<String, Object>> searchByNameExpedienteAfiliacion(String nombre) {
		return iExpedienteAfiliacionService.getByNameExpedienteAfiliacion(nombre);
	}
}
