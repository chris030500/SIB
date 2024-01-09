package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.model.CodigoResultadoModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.repository.CodigoResultadoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CodigoResultadoService {
	
	@Autowired
	private CodigoResultadoRepository repository;
	
	public CodigoResultadoModel save(CodigoResultadoModel model) {
		return repository.save(model);
	}
	
	public List<CodigoResultadoModel> listaCodigoResultado() {
		return repository.findAll();
	}
	
	public List<CodigoResultadoModel> listaCodigoResultadoActivos() {
		return repository.listaCodigosResultado();
	}
	
	public CodigoResultadoModel buscarCodigoResultado(int idCodigo) {
		return repository.buscarCodigoResultado(idCodigo);
	}

}
