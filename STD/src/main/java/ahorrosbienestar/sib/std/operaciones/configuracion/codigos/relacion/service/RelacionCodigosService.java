package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.repository.RelacionCodigosRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RelacionCodigosService {

	@Autowired
	private RelacionCodigosRepository repository;

	public RelacionCodigosModel save(RelacionCodigosModel model) {
		return repository.save(model);
	}

	public RelacionCodigosModel buscarRelacionPorId(int id) {
		return repository.findById(id).orElse(null);
	}

	public List<RelacionCodigosModel> listaRelaciones() {
		return repository.findAll();
	}

}
