package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model.ClientesBloqueados;
import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.repository.IClientesBloqueadosRepository;

@Service
public class ClientesBloqueadosService {
	private static final Logger logger = Logger.getLogger(ClientesBloqueadosService.class);
	
	private IClientesBloqueadosRepository iClientesBloqueadosRepository;
	
	
	@Autowired
	public ClientesBloqueadosService(IClientesBloqueadosRepository iClientesBloqueadosRepository) {
		this.iClientesBloqueadosRepository = iClientesBloqueadosRepository;
	}
	
	public List<ClientesBloqueados> obtClienteB(){
		try {
			return iClientesBloqueadosRepository.buscarClientesBloqueados();
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
			return null;
		}
		
	}
	
	public Optional<ClientesBloqueados> findById(Integer idBloqueo){
		try {
			return iClientesBloqueadosRepository.findById(idBloqueo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public ClientesBloqueados save(ClientesBloqueados clientesBloqueados) {
		try {
			return iClientesBloqueadosRepository.save(clientesBloqueados);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
