package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.AsignacionOfertas;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository.AsignacionOfertasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AsignacionOfertasService {

	@Autowired
	private AsignacionOfertasRepository repository;

	public AsignacionOfertas findById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	public AsignacionOfertas save(AsignacionOfertas model) {
		return repository.save(model);
	}

}
