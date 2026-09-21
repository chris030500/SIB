package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model.ClientesBloqueados;
import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.service.ClientesBloqueadosService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("clientes")
public class ClienteBloqueadosController {

	private static final Logger logger =
	        LoggerFactory.getLogger(ClienteBloqueadosController.class);
    private static final String ERROR_LOG = "Error: ";

    @Autowired
    private ClientesBloqueadosService clientesBloqueadosService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @GetMapping("/obtClientes")
    public ResponseEntity<List<ClientesBloqueados>> obtClientes() {
        try {
            logger.info("/obtClientes");
            identificarBloqueados();
            return ResponseEntity.ok(clientesBloqueadosService.obtClienteB());
        } catch (Exception e) {
            logger.error(ERROR_LOG + e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cambiarEstatus")
    public ResponseEntity<List<String>> cambiarEstatusRegla(
            @RequestParam(name = "idBloqueo") Integer idRegla,
            @RequestParam(name = "motivoBloqueo") String motivoBloqueo,
            @RequestParam(name = "estatus") Integer estatus) {

        logger.info("/cambiarEstatus");

        String mensaje = "Actualizo Estatus Correctamente.";
        List<String> respuesta = new ArrayList<>();

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(principal instanceof UserDetails)) {
                respuesta.add("Usuario no autenticado.");
                return ResponseEntity.status(401).body(respuesta);
            }

            UserDetails userDetails = (UserDetails) principal;
            List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

            if (usuario == null || usuario.isEmpty()) {
                respuesta.add("Usuario no encontrado.");
                return ResponseEntity.status(404).body(respuesta);
            }

            Optional<ClientesBloqueados> actualizarEstatus = clientesBloqueadosService.findById(idRegla);

            if (!actualizarEstatus.isPresent()) {
                respuesta.add("Registro no encontrado.");
                return ResponseEntity.status(404).body(respuesta);
            }

            ClientesBloqueados clienteBloqueado = actualizarEstatus.get();
            LocalDate fecha = LocalDate.now();

            clienteBloqueado.setI_estatus((estatus == 2) ? 1 : 2);

            if (estatus == 2) {
                clienteBloqueado.setDt_fecha_desbloqueo(fecha);
                clienteBloqueado.setI_usuario_desbloqueo(usuario.get(0));
                clienteBloqueado.setVc_motivo_desbloqueo(motivoBloqueo);
                clienteBloqueado.setDt_fecha_vencimiento(null);
            }

            clientesBloqueadosService.save(clienteBloqueado);

            respuesta.add(mensaje);
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error(ERROR_LOG + e.getMessage(), e);
            respuesta.add("Ocurrió un error al actualizar el estatus.");
            return ResponseEntity.internalServerError().body(respuesta);
        }
    }

    public void identificarBloqueados() {
        try {
            jdbcTemplate.update("CALL SIB.SP_CLIENTES_BLOQUEADOS()");
        } catch (Exception e) {
            logger.error(ERROR_LOG + e.getMessage(), e);
        }
    }
}