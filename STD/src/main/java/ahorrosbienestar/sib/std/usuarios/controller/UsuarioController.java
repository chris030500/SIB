package ahorrosbienestar.sib.std.usuarios.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

 
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired 
    private RolService rolService;
    
    @Autowired 
    private ConfigService configService;
    
    @Autowired 
    private HistContrasenaService histContrasenaService;
    
    @Autowired
    private ConfiguracionService configuracionService;
    
    @Autowired
    private OpcionesMenuService opcionesMenuService;
    
    @Autowired
    private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    
    private static final String nombrePantalla = "Usuarios";
    private static final String rutaError = "errores/error_403";
    private String rutaDireccion="";
    //protected final Log logger = LogFactory.getLog(this.getClass());
    private static final Logger logger =
            LoggerFactory.getLogger(UsuarioController.class);
 
    // Inicio de usuarios
    @GetMapping("/formulario")
    public String InicioUsuario(Model model) {
        logger.info("Ejecutando formulario");
        
        if(validarAcceso() == 1) {
            Usuario usuario = new Usuario();
            HistContrasena hc = new HistContrasena();
            List<Rol> roles = rolService.findByRolActivo();
            //Rol roles = new Rol();
            //logger.info("hc: " + hc.getUsr_historico_contrasena_id().getPk_id_usuario());
            model.addAttribute("historico", hc);
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", roles);
            
            // Mostramos todos los usuarios
            model.addAttribute("usuarios", usuarioService.findAll());
            rutaDireccion = "usuario/usuarios";
        }else {
            rutaDireccion = rutaError;
        }
        return rutaDireccion;
    }
 
    @GetMapping("/altaUsuario")
    public String AltaUsuario(Model model) throws IOException {
        logger.info("Ejecutando alta usuario");
        List<Rol> roles = rolService.findByRolActivo();
        String contrasena = configService.findAll().get(5).getVc_valor_propiedad().toString();
//        InputStream inputStream = new FileInputStream("src/main/resources/contrasena.properties");
//        Properties properties = new Properties();
//        properties.load(inputStream);
        
        model.addAttribute("contrasena", contrasena);
        model.addAttribute("roles", roles);
        return "usuario/altaUsuario";
    }
 
    @ResponseBody
    @RequestMapping(value = "usuario/update", method = RequestMethod.PUT)
    public void update(@ModelAttribute("usuario") Usuario usuario) {
        logger.info("Ejecutando editar usuario");
        usuarioService.save(usuario);
    }
 
    @ResponseBody
    @RequestMapping(value = "/usuario/loadEntity/{id}", method = RequestMethod.GET)
    public Optional<Usuario> loadEntity(@PathVariable("id") Integer id) {
        logger.info("Ejecutando loadEntity en usuarioController");
        logger.info("Entro en loadEntity en usuarioController");
        return usuarioService.findById(id);
    }
    
    @ResponseBody
    @RequestMapping(value = "/usuario/cambioContra/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> loadCambioContra(@PathVariable("id") Integer id) {

        logger.info("Ejecutando loadCambioContra en usuarioController");
        logger.info("Id usuario: " + id);

        Optional<Usuario> usuario = usuarioService.findById(id);

        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("No se encontró información del usuario seleccionado.");
        }

        return ResponseEntity.ok(usuario.get());
    }
    
    @GetMapping("/mensajeContrasena")
    public ResponseEntity<?> mensajeContrasena(@RequestParam("contrasena") String contrasena) throws IOException {
//        InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");
//        Properties properties = new Properties();
//        properties.load(inputStream);
        logger.info("Ejecutando mensajeContrasena");
        String longitud = configService.findAll().get(0).getVc_valor_propiedad();
        String mayusculas = configService.findAll().get(4).getVc_valor_propiedad();
        String minusculas = configService.findAll().get(3).getVc_valor_propiedad();
        String numeros = configService.findAll().get(2).getVc_valor_propiedad();
        String caracteres = configService.findAll().get(1).getVc_valor_propiedad();
 
        return ResponseEntity.ok(configuracionService.mensajeParametros(contrasena, Integer.parseInt(longitud),
                Integer.parseInt(mayusculas), Integer.parseInt(minusculas), Integer.parseInt(numeros),
                Integer.parseInt(caracteres)));
    }
    
    @ResponseBody
    @RequestMapping(value = "/guardarCambioContra", method = RequestMethod.POST)
    public ResponseEntity<?> guardarCambioContra(
            @RequestParam("idUsuario") Integer idUsuario,
            @RequestParam("contrasena") String contrasena) {

        try {

            Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);

            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.badRequest()
                        .body("No se encontró el usuario.");
            }

            Usuario usuario = usuarioOpt.get();

            HistContrasena historicoActual =
                    histContrasenaService.obtenerContrasenaActiva(
                            usuario.getPk_id_usuario()
                    );

            if (historicoActual != null) {
                historicoActual.setVc_indicador_activo(0);
                histContrasenaService.save(historicoActual);
            }

            HistContrasena nuevoHistorico = new HistContrasena();

            nuevoHistorico.setVc_contrasenia(contrasena);

            nuevoHistorico.setDt_vig_cont_inicio(LocalDate.now());

            // OBLIGAR CAMBIO DE CONTRASEÑA
            nuevoHistorico.setDt_vig_cont_fin(
                    LocalDate.now().minusDays(1)
            );

            nuevoHistorico.setVc_indicador_activo(1);

            nuevoHistorico.setUsr_historico_contrasena_id(usuario);

            nuevoHistorico.setUsr_id_Usr(usuario);

            histContrasenaService.save(nuevoHistorico);

            usuario.setI_intentos_fallidos(0);
            usuario.setI_estatus_cuenta(1);
            usuario.setI_estatus_sesion(0);

            usuarioService.save(usuario);

            return ResponseEntity.ok(
                    "Contraseña actualizada correctamente."
            );

        } catch (Exception e) {

            logger.error("Error al cambiar contraseña", e);

            return ResponseEntity.badRequest()
                    .body("Error al cambiar contraseña.");
        }
    }
    
    public int validarAcceso() {
        try {
            Object principal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            if (!(principal instanceof UserDetails)) {
                logger.warn("No existe usuario autenticado para validar acceso.");
                return 0;
            }

            UserDetails userDetails = (UserDetails) principal;

            List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

            if (usuario == null || usuario.isEmpty()) {
                logger.warn("No se encontró usuario para validar acceso: " + userDetails.getUsername());
                return 0;
            }

            int relacion = opcionesMenuService.buscarRelacion(nombrePantalla);

            if (relacion <= 0) {
                logger.warn("No se encontró relación de pantalla: " + nombrePantalla);
                return 0;
            }

            return iRelRolOpcionesRepository.validarAcceso(
                    usuario.get(0).getRol_idRol().getPk_id_rol(),
                    relacion
            );

        } catch (Exception e) {
            logger.error("Error al validar acceso de usuario", e);
            return 0;
        }
    }
    
 
}