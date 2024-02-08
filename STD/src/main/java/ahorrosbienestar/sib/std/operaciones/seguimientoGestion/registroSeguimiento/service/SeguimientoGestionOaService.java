package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.repository.SeguimientoGestionOaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeguimientoGestionOaService {

	@Autowired
	private SeguimientoGestionOaRepository repository;

	public SeguimientoGestionOaModel save(SeguimientoGestionOaModel model) {
		return repository.save(model);
	}

	public SeguimientoGestionOaModel findById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<SeguimientoGestionOaModel> listaGestionPorOferta(int asignacion) {
		return repository.listaGestionPorOferta(asignacion);
	}
}
