package ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.model.AccionRealizarModel;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.repository.AccionRealizarRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccionRealizarService {

	@Autowired
	private AccionRealizarRepository repository;

	public List<AccionRealizarModel> listaCatAccionRealizar() {
		return repository.listaCatAccionRealizar();
	}
	
	public AccionRealizarModel buscarAccionRealizarId(int id) {
		return repository.buscarAccionRealizarId(id);
	}

}
