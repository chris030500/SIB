package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.ListaSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.repository.ListaSeguimientoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListaSeguimientoService {

	@Autowired
	private ListaSeguimientoRepository repository;

	public List<ListaSeguimientoModel> listaSeguimiento(int idSucursal, String usuario) {
		return repository.listaSeguimiento(idSucursal,usuario);
	}
	
	public List<ListaSeguimientoModel> busquedaPorSeguimiento(String nombre, String oferta, String referencia, String cliente) {
		return repository.busquedaPorSeguimiento(nombre, oferta, referencia, cliente);
	}

	public List<ListaSeguimientoModel> busquedaPorSeguimientoSucursal(String nombre, String oferta, String referencia, int sucursal) {
		return repository.busquedaPorSeguimientoSucursal(nombre, oferta, referencia, sucursal);

	}

}
