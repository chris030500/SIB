package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model.ClientesBloqueados;
import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.repository.IClientesBloqueadosRepository;

@Service
public class ClientesBloqueadosService {
	private static final Logger logger =
	        LoggerFactory.getLogger(ClientesBloqueadosService.class);
	
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
			logger.error("Ocurrió un error durante el proceso", e);
			return Collections.emptyList();
		}
		
	}
	
	public Optional<ClientesBloqueados> findById(Integer idBloqueo){
		try {
			return iClientesBloqueadosRepository.findById(idBloqueo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Optional.empty();
		}
	}
	
	public ClientesBloqueados save(ClientesBloqueados clientesBloqueados) throws DataAccessException{
		try {
			return iClientesBloqueadosRepository.save(clientesBloqueados);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("Error al guardar el cliente bloqueado: " + e.getMessage(), e);
	        throw e; // Relanza la excepción para que el código cliente pueda manejarla
		}
	}
}
