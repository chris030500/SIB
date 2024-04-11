package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.model.CodigoAccionModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.repository.CodigoAccionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CodigoAccionService {

	@Autowired
	private CodigoAccionRepository repository;
	
	public List<CodigoAccionModel> listaCodigosAccion() {
		return repository.findAll();
	}
	
	public List<CodigoAccionModel> listaCodigosAccionActivos() {
		return repository.listaCodigosAccion();
	}
	
	public CodigoAccionModel save(CodigoAccionModel model) {
		return repository.save(model);
	}
	
	public CodigoAccionModel buscarCodigoAccion(int idCodigo) {
		return repository.buscarCodigoAccion(idCodigo);
	}
	
}
