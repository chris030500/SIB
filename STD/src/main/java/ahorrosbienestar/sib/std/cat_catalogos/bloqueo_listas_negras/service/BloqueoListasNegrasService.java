package ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.model.BloqueoListasNegrasModel;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.repository.BloqueoListasNegrasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BloqueoListasNegrasService {
	
	@Autowired
	private BloqueoListasNegrasRepository repository;
	
	public List<BloqueoListasNegrasModel> listaCatBloqueo() {
		return repository.listaCatBloqueo();
	}
	
	public BloqueoListasNegrasModel buscarBloqueoPorId(int id) {
		return repository.buscarBloqueoPorId(id);
	}

}
