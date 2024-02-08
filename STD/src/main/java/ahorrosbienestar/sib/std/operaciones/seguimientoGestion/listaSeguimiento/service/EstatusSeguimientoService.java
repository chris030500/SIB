package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.EstatusSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.repository.EstatusSeguimientoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstatusSeguimientoService {

	@Autowired
	private EstatusSeguimientoRepository repository;

	public List<EstatusSeguimientoModel> listaDeEstatus() {
		return repository.listaDeEstatus();
	}

}
