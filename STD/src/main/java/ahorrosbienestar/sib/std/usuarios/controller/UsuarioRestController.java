package ahorrosbienestar.sib.std.usuarios.controller;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.time.LocalDateTime;
 
import jakarta.validation.Valid;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
 
 
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import groovyjarjarantlr4.v4.parse.ANTLRParser.sync_return;
 
@RestController
@RequestMapping("usuario")
public class UsuarioRestController {
 
    @Autowired
    private UsuarioService usuarioService;
 
    @Autowired
    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
 
    @Autowired
    private RolService rolService;
 
    @Autowired
    private ConfigService configService;
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 
    @Autowired
    private HistContrasenaService histContrasenaService;
    
 
    //protected final Log logger = LogFactory.getLog(this.getClass());
    private static final Logger logger = Logger.getLogger(UsuarioRestController.class);
 
    // Dar de alta usuario utilizando metodo POST
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuario usuario) {
        logger.info("Ingreso en save usuario");
        Usuario usu = new Usuario();
        usu = usuarioService.save(usuario);
        return ResponseEntity.ok(usu);
    }
 
//    @PutMapping("/{id}")
//    public ResponseEntity<?> edit(@RequestBody @Valid Usuario usuario, @PathVariable(name = "id") Integer id) {
//        // Creamos Usuario
//        Usuario u = new Usuario();
//        // Buscamos el Usuario
//        u = usuarioService.findById(id).get();
//
//        if (u != null) {
//            return ResponseEntity.ok(usuarioService.save(usuario));
//        }
//        return ResponseEntity.notFound().build();
//
//    }
 
    // Metodo para desactivar al usuario (cambia estatus de sesion)
    @GetMapping("/activarUsuario/{id}")
    public ResponseEntity<?> activarUsuarioSesion(@PathVariable("id") Integer id) throws IOException {
        logger.info("Ingreso en activarUsuarioSesion");
        logger.info("activar sesión usuario: " + id);
        return ResponseEntity.ok(usuarioService.activarUsuarioSesion(id));
    }
 
    // Metodo para obtener listado de todos los usuarios
    @GetMapping
    public ResponseEntity<?> findAll() {
        logger.info("Ingreso en findAll Usuarios");
        return ResponseEntity.ok(usuarioService.findAll());
    }
 
    // Metodo para obtener listado de todos los usuarios
    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<?> findByNombreUsuario(@PathVariable(name = "nombreUsuario") String nombreUsuario) {
        logger.info("Ingreso en findByNombreUsuario");
        return ResponseEntity.ok(usuarioService.findByNombreUsuario(nombreUsuario));
    }
 
    // Buscar usuario por nombre y trae toda su información
    @GetMapping("/nombreUsuario/{nombre}")
    public ResponseEntity<?> findByUsuarioNombre(@PathVariable(name = "nombre") String nombre) {
        logger.info("Ingreso en findByUsuarioNombre");
        return ResponseEntity.ok(usuarioService.findByUsuarioNombre(nombre));
    }
 
    // Buscar usuario por nombre (true = existe, false = no existe)
    @GetMapping("/nombre/{nombreUsuario}")
    public boolean findByNombreUsuarioBoolean(@PathVariable("nombreUsuario") String nombreUsuario) {
        logger.info("Ingreso en buscar usuario por nombre");
        logger.info("nombre usuario a buscar: " + nombreUsuario);
        Usuario u = new Usuario();
        u = usuarioService.findByNombreUsuarioBoolean(nombreUsuario);
        if (u != null) {
            return true;
        } else if (u == null) {
            return false;
        }
        return false;
 
    }
 
    @GetMapping("/idEmpleado/{idEmpleado}")
    public boolean findByIdEmpleadoBoolean(@PathVariable("idEmpleado") Integer idEmpleado) {
        logger.info("Ingreso en findByIdEmpleadoBoolean");
        Usuario u = new Usuario();
        u = usuarioService.findByIdEmpleadoBoolean(idEmpleado);
        if (u != null) {
            return true;
        } else if (u == null) {
            return false;
        }
        return false;
 
    }
 
    // Guardar Usuario
    @RequestMapping(value = { "/guardar_usuario" }, method = RequestMethod.POST)
    public ResponseEntity<?> guardarUsuario(@RequestParam("EmpleadoId") Integer EmpleadoId,
            @RequestParam("nombreUsuario") String nombreUsuario, @RequestParam("roles") int rol) throws IOException {
        logger.info("Ingreso en guardar_usuario");
        logger.info("EmpleadoId: " + EmpleadoId);
        logger.info("nombreUsuario: " + nombreUsuario);
        logger.info("rol: " + rol);
 
        Usuario usuario = new Usuario();
        usuario.setI_estatus_usuario(1);
        usuario.setI_estatus_sesion(0);
        usuario.setI_estatus_cuenta(1);
        usuario.setVc_nombre_usuario(nombreUsuario);
        usuario.setI_intentos_fallidos(0);
        Rol r = rolService.findById(rol).get();
        usuario.setRol_idRol(r);
        usuario.setI_id_empleado(EmpleadoId);
        logger.info("Datos usuario: ");
        logger.info("Estatus cuenta: " + usuario.getI_estatus_cuenta());
        logger.info("Estatus sesión: " + usuario.getI_estatus_sesion());
        logger.info("Estatus usuario: " + usuario.getI_estatus_usuario());
        logger.info("Estatus intentos fallidos: " + usuario.getI_intentos_fallidos());
        logger.info("Usuario PK: " + usuario.getPk_id_usuario());
        logger.info("Nombre Usuario: " + usuario.getVc_nombre_usuario());
        logger.info("Rol: " + usuario.getRol_idRol().getVc_nombre_rol());
 
        return ResponseEntity.ok(usuarioService.save(usuario));
    }
 
    @RequestMapping(value = { "/editar_usuario" }, method = RequestMethod.POST)
    public ResponseEntity<?> cambiarNombreUsuario(@RequestParam("usuario") String usuario,
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("rol") Integer idRol) {
        try {
            logger.info("Usuario: " + usuario);
            logger.info("IdUsuario: " + idUsuario);
            logger.info("Ingreso en cambiarNombreUsuario");
            logger.info("Usuario: " + usuario);
            logger.info("IdUsuario: " + idUsuario);
            logger.info("idRol:" + idRol);
            if (usuario != null && idUsuario != null) {
                logger.info("Puede realizar el cambio de usuario");
                //logger.info("Puede realizar el cambio de usuario");
                Usuario usu = usuarioService.findById(idUsuario).get();
                
                
                return ResponseEntity.ok(usuarioService.cambiarNombreUsuario(usuario, idUsuario, idRol));
                
                
                
            }
            // logger.info("No puede realizar el cambio de usuario");
            logger.info("No puede realizar el cambio de usuario");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error inesperado durante la operación.", e);
            return ResponseEntity.notFound().build();
        }
        
 
    }
 
    @RequestMapping(value = { "/editar_usuario/contrasenia" }, method = RequestMethod.POST)
    public ResponseEntity<?> cambiarContraseniaUsuario(@RequestParam("usuario") String usuario,
            @RequestParam("idUsuario") Integer idUsuario, @RequestParam("password") String password) {
        logger.info("Usuario: " + usuario);
        logger.info("IdUsuario: " + idUsuario);
        logger.info("Ingreso en cambiarContraseniaUsuario");
        logger.info("Usuario: " + usuario);
        logger.info("IdUsuario: " + idUsuario);
        
        String valorUltimasContrasenas = configService.findAll().get(8).getVc_valor_propiedad().toString();
        //System.out.println("valorUltimasContrasenas: " + valorUltimasContrasenas);
        
        if (usuario != null && idUsuario != null) {
            List<HistContrasena> ultimasContrasenas = histContrasenaService.findTopByUsuarioByFechaDesc(idUsuario, Integer.parseInt(valorUltimasContrasenas));
            boolean coincideContrasena = false;
            for (HistContrasena historicoContrasena : ultimasContrasenas) {
                if (BCrypt.checkpw(password, historicoContrasena.getVc_contrasenia())) {
                    coincideContrasena = true;
                    
                    ResponseEntity.notFound().build();
                    
                    //return ResponseEntity.ok("La nueva contraseña coincide con una de las últimas 3 contraseñas");
                    break;// La nueva contraseña coincide con una de las últimas 3 contraseñas
                }
            }
            
            if (coincideContrasena) {
                logger.error("La nueva contraseña coincide con una de las últimas " + "contraseñas");
                return ResponseEntity.badRequest().body("La nueva contraseña coincide con una de las últimas 3 contraseñas");
            }
            logger.info("Puede realizar el cambio de contraseña");
 
            Usuario usu = usuarioService.findById(idUsuario).get();
            histContrasenaService.bajaContrasena(usu.getPk_id_usuario());
            int diasVigencia = configService.obtenerPeriodoDiasPassword();
 
            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaVencimiento = fechaInicio.plusDays(diasVigencia);
 
            logger.info("Días de vigencia de contraseña: " + diasVigencia);
            logger.info("Fecha de inicio: " + fechaInicio);
            logger.info("Fecha de vencimiento: " + fechaVencimiento);
 
            HistContrasena hc = new HistContrasena();
            hc.setDt_vig_cont_inicio(fechaInicio);
            hc.setDt_vig_cont_fin(fechaVencimiento);
            hc.setVc_indicador_activo(1);
            hc.setUsr_historico_contrasena_id(usu);
            hc.setVc_contrasenia(passwordEncoder.encode(password));
            // histContrasenaService.save(hc);
            logger.info("Guardar en historico");
            // return null;
            return ResponseEntity.ok(histContrasenaService.save(hc));
        }
        logger.info("No puede realizar el cambio de usuario");
        return ResponseEntity.notFound().build();
 
    }
 
 
 
    @RequestMapping(value = { "/baja_usuario" }, method = RequestMethod.POST)
    public ResponseEntity<?> bajaUsuario(@RequestParam("usuario") Integer usuario) {
        logger.info("Usuario: " + usuario);
        return ResponseEntity.ok(usuarioService.bajaUsuarioEstatus(usuario));
    }
 
    @RequestMapping(value = { "/reactivar_usuario" }, method = RequestMethod.POST)
    public ResponseEntity<?> reactivarUsuario(@RequestParam("usuario") Integer usuario) {
        logger.info("Usuario: " + usuario);
        return ResponseEntity.ok(usuarioService.activarUsuarioEstatus(usuario));
    }
    
    @RequestMapping(value = { "/reactivar_usuario_sesion/{id}" }, method = RequestMethod.POST)
    public ResponseEntity<?> reactivarUsuarioSesion(@PathVariable("id") Integer id) throws IOException {
        logger.info("Ingreso en activarUsuarioSesion");
        logger.info("activar sesión usuario: " + id);
        return ResponseEntity.ok(usuarioService.activarUsuarioSesion(id));
    }
 
}