package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.ListaSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.repository.ListaSeguimientoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListaSeguimientoService {

	@Autowired
	private ListaSeguimientoRepository repository;

	public List<ListaSeguimientoModel> listaSeguimiento() {
		return repository.listaSeguimiento();
	}
	
	public List<ListaSeguimientoModel> busquedaPorSeguimiento(@Param("nombre") String nombre, @Param("oferta") String oferta, @Param("referencia") String referencia) {
		return repository.busquedaPorSeguimiento(nombre, oferta, referencia);
	}

}
