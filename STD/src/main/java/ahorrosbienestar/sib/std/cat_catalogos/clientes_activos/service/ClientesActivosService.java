package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository.ClientesActivosRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientesActivosService {
	
	@Autowired
	private ClientesActivosRepository repository;
	
	public List<ClientesActivosModel> listaClientes(String valor) {
		return repository.listaClientes(valor);
	}

}
