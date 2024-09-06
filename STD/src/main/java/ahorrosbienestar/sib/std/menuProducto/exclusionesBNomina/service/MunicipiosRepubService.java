package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IMunicipiosRepubRepository;

@Service
public class MunicipiosRepubService {
	
	@Autowired
	private IMunicipiosRepubRepository iMunicipiosRepubRepository;
	
	public List<Map<String, Object>> getMunicipiosRepubById(Integer idEstado) {
		return iMunicipiosRepubRepository.getMunicipiosRepub(idEstado);
	}

}
