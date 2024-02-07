package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.service.ClientesBloqueadosService;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
@RequestMapping("clientes")
public class ClienteBloqueadosController {

	private static final Logger logger = Logger.getLogger(ClienteBloqueadosController.class);
	
	@Autowired
	private ClientesBloqueadosService clientesBloqueadosService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/obtClientes")
	public ResponseEntity<?> obtClientes(){
		try {
			logger.info("/obtClientes");
			identificarBloqueados();
			return ResponseEntity.ok(clientesBloqueadosService.obtClienteB());
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}

	
	}
	
	public void identificarBloqueados() {
		try {
			jdbcTemplate.update("CALL SIB.SP_CLIENTES_BLOQUEADOS()");
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
}
