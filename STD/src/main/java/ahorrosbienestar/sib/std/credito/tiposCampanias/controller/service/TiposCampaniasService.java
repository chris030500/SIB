package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.TiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class TiposCampaniasService {

	@Autowired
	private TiposCampaniasRepository repository;
	public List<TiposCampaniasDTO> listaCampanias(String valor) {
		List<Object[]> resultados = repository.listaCampanias(valor);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
		//return repository.listaCampanias(valor);
	}
	
	public TiposCampaniasModel obtenerCampaniaPorId(Integer id) {
	    return repository.mostrarCampania(id);
	}
	
}
