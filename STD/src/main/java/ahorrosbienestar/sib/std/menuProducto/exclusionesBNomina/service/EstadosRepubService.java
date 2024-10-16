package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IEstadosRepubRepository;

@Service
public class EstadosRepubService {
	
	@Autowired
	private IEstadosRepubRepository iEstadosRepubRepository;
	
	public List<Map<String, Object>> getAllEstadosRepub() {
		return iEstadosRepubRepository.getEstadosRepub();
	}

}
