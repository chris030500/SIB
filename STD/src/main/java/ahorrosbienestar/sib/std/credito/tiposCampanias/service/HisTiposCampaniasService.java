package ahorrosbienestar.sib.std.credito.tiposCampanias.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.HisTiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.repository.HisTiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class HisTiposCampaniasService {
	
	@Autowired
	private HisTiposCampaniasRepository repository;
	
	@Transactional
	public boolean crearHisCampania(HisTiposCampaniasModel c) {
	    int filas = repository.insertarHisCampania(
	    	c.getVc_json_entrada(),
	    	c.getVc_tabla(),
	    	c.getI_user_reg()
	    );
	    return filas > 0;
	}

}
