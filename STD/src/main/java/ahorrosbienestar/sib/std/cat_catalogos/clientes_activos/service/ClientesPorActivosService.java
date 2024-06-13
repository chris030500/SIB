package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesPorSucursal;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository.ClientesPorSucursalRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientesPorActivosService {
	@Autowired
	private ClientesPorSucursalRepository clientesPorSucursalRepository;
	
	public List<ClientesPorSucursal> listaClientesFiltro(int  idSucursal) {
		return clientesPorSucursalRepository.listaClientesFiltro(idSucursal);
	}

}
