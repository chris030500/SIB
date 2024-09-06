package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IConveniosNominaRepository;

@Service
public class ConveniosNominaService {
	
	@Autowired
	private IConveniosNominaRepository iConvenioNominaRepository;
	
	public List<Map<String, Object>> getConveniosNominaActivos() {
		return iConvenioNominaRepository.getConveniosNominaActivos();
	}
	
	public List<Map<String, Object>> getConveniosNominaDepurar() {
		return iConvenioNominaRepository.getConveniosNominaDepurar();
	}

}
