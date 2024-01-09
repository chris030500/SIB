package ahorrosbienestar.sib.std.cat_catalogos.opc_busqueda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cat_catalogos.opc_busqueda.model.OpcBusquedaModel;
import ahorrosbienestar.sib.std.cat_catalogos.opc_busqueda.repository.OpcBusquedaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OpcBusquedaService {

	@Autowired
	private OpcBusquedaRepository repository;
	
	public List<OpcBusquedaModel> busquedaPorGrupo(int id) {
		return repository.busquedaPorGrupo(id);
	}
	
	public OpcBusquedaModel buscarPorId(int id) {
		return repository.buscarPorId(id);
	}
	
}
