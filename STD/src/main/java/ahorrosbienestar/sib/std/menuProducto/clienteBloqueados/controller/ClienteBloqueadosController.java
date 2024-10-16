package ahorrosbienestar.sib.std.menuproducto.clienteBloqueados.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.menuproducto.clienteBloqueados.model.ClientesBloqueados;
import ahorrosbienestar.sib.std.menuproducto.clienteBloqueados.service.ClientesBloqueadosService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("clientes")
public class ClienteBloqueadosController {

	private static final Logger logger = Logger.getLogger(ClienteBloqueadosController.class);
	
	@Autowired
	private ClientesBloqueadosService clientesBloqueadosService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
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
	
	@PostMapping("/cambiarEstatus")
	public ResponseEntity<?> cambiarEstatusRegla(
			@RequestParam(name = "idBloqueo") Integer idRegla,
			@RequestParam(name = "motivoBloqueo") String motivoBloqueo,
			@RequestParam(name = "estatus") Integer estatus
			){
		logger.info("/cambiarEstatus");
		String mensaje ="Actualizo Estatus Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());			
			LocalDate fecha = LocalDate.now();
			
			Optional<ClientesBloqueados> actualizarEstatus = clientesBloqueadosService.findById(idRegla);
			
			actualizarEstatus.get().setI_estatus((estatus==2)?1:2);
			if(estatus == 2) {
				actualizarEstatus.get().setDt_fecha_desbloqueo(fecha);
				actualizarEstatus.get().setI_usuario_desbloqueo(usuario.get(0));
				actualizarEstatus.get().setVc_motivo_desbloqueo(motivoBloqueo);
				actualizarEstatus.get().setDt_fecha_vencimiento(null);
			}
			clientesBloqueadosService.save(actualizarEstatus.get());
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
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
