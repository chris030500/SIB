package ahorrosbienestar.sib.std.credito.procesosCampanias.controller.service;

import java.util.List;

import org.springframework.stereotype.Service;


import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.TiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcesosCampaniasService {

	private TiposCampaniasRepository tiposCampaniasRepository;
	
	public List<TiposCampaniasModel> getTiposCampaniasModel() {
		return tiposCampaniasRepository.findAll();
	}
	
}
