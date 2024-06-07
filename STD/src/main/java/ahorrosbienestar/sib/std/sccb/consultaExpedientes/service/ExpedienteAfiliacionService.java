package ahorrosbienestar.sib.std.sccb.consultaExpedientes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository.IExpedienteAfiliacionRepository;

@Service
public class ExpedienteAfiliacionService {
	
	@Autowired
	private IExpedienteAfiliacionRepository iExpedienteAfiliacionService;
	
	public Optional<ExpedienteAfiliacion> searchExpedienteAfiliacionByNomCliente(String nomCliente) {
		return iExpedienteAfiliacionService.getExpedienteAfiliacionByName(nomCliente);
	}
	
	public List<Map<String, Object>> searchByNameExpedienteAfiliacion(String nombre) {
		return iExpedienteAfiliacionService.getByNameExpedienteAfiliacion(nombre);
	}
}
