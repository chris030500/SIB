package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.TiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class TiposCampaniasService {

	@Autowired
	private TiposCampaniasRepository repository;
	public List<TiposCampaniasModel> listaCampanias(String valor) {
		return repository.listaCampanias(valor);
	}
	
}
