package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.NumClienteModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository.NumClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NumClienteService {

	@Autowired
	private NumClienteRepository repository;

	public List<NumClienteModel> listaClientes(String valor) {
		return repository.listaClientes(valor);
	}

}
