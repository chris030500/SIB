package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglas;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IParametrosReglaRepository;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ParametrosReglasService {

	@Autowired
	private IParametrosReglaRepository iParametrosReglaRepository;

	
	public List<ParametrosReglas> obtRegistroParametros(Integer idRegla){
		return iParametrosReglaRepository.obtParametros(idRegla);
	}
}