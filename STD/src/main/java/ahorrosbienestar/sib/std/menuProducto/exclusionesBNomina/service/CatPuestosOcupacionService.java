package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.ICatPuestosOcupacionRepository;

@Service
public class CatPuestosOcupacionService {
	
	@Autowired
	private ICatPuestosOcupacionRepository iCatPuestosOcupacionRepository;
	
	public List<Map<String, Object>> getPuestosOcupacionActivos() {
		return iCatPuestosOcupacionRepository.getPuestosOcupacionActivos();
	}

}
