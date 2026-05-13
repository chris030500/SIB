package ahorrosbienestar.sib.std.operaciones.configuracion.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.service.ConfiguracionSistemaService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.dto.SucursalXEmpleadoSeguimiento;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.service.CitasVencidosService;
import ahorrosbienestar.sib.std.usuarios.dto.MicrofinUsuarioDTO;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.MicrofinUsuarioService;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("operaciones")
public class OpConfiguracionController {

    private static final Logger logger = Logger.getLogger(OpConfiguracionController.class);

    @Autowired
    private MicrofinUsuarioService microfinUsuarioService;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private CitasVencidosService citasVencidosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConfiguracionSistemaService configuracionSistemaService;

    @GetMapping("/configuracion")
    public String ModuloOperacion(Model model) {
        try {
            String rutaPagina = "";

            logger.error("===== ENTRO A MODULO CONFIGURACION =====");

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            }

            logger.error("username: " + username);

            List<MicrofinUsuarioDTO> datosUsuario = datosUsuario();

            logger.error("datosUsuario size: " + datosUsuario.size());

            int permisoAdmin = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoAdmin() : 0;
            int permisoConsulta = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoConsulta() : 0;

            logger.error("permisoAdmin (raw): " + permisoAdmin);
            logger.error("permisoConsulta (raw): " + permisoConsulta);

            int permisoUsuario = (permisoAdmin > 0) ? 1 : (permisoConsulta > 0 ? 2 : 0);

            int permisoTelefono = configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_TELEFONOS, username);
            int permisoCodAccionR = configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_COD_ACCION_TELEFONO, username);

            logger.error("permisoUsuario (calculado): " + permisoUsuario);
            logger.error("permisoTelefono: " + permisoTelefono);
            logger.error("permisoCodAccionR: " + permisoCodAccionR);
            logger.error("========================================");

            logger.error("ENTRA A PANTALLA CONFIGURACION");

            model.addAttribute("permisoTelefono", 1);
            model.addAttribute("permisoCodAccionR", 1);
            model.addAttribute("permisoUsuario", 1);

            rutaPagina = Constantes.RUTA_CONFIGURACION_VENTAS;

            return rutaPagina;

        } catch (Exception e) {
            logger.error("ERROR EN MODULO CONFIGURACION", e);
            return Constantes.RUTA_ERROR;
        }
    }

    @GetMapping("/seguimientoGestion")
    public String ModuloSeguimientoGestion(Model model) {
        try {
            String rutaPagina = "";

            List<MicrofinUsuarioDTO> datosUsuario = datosUsuario();
            int permisoAdmin = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoAdmin() : 0;
            int permisoConsulta = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoConsulta() : 0;

            List<SucursalXEmpleadoSeguimiento> listaSursales = citasVencidosService.obtenerSucursales(obtenerEmpleadoLogueado());
            int sucursalUsu = (listaSursales != null && !listaSursales.isEmpty()) ? listaSursales.get(0).getI_sucursal() : 0;

            int permisoUsuario = (permisoAdmin > 0) ? 1 : (permisoConsulta > 0 ? 2 : 0);

            if (sucursalUsu != 0 || permisoUsuario > 0) {
                model.addAttribute("permisoUsuario", permisoUsuario);
                model.addAttribute("sucursalInicial", sucursalUsu);
                model.addAttribute("sucursales", listaSursales);
                rutaPagina = Constantes.RUTA_SEGUIMIENTO_GESTION;
            } else {
                rutaPagina = Constantes.RUTA_ERROR_PERMISO;
            }

            return rutaPagina;
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
            return Constantes.RUTA_ERROR;
        }
    }

    @GetMapping("/reportes")
    public String ModuloReportes(Model model) {
        try {
            String rutaPagina = "";

            List<MicrofinUsuarioDTO> datosUsuario = datosUsuario();
            int permisoAdmin = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoAdmin() : 0;
            int permisoConsulta = (!datosUsuario.isEmpty()) ? datosUsuario.get(0).getPermisoConsulta() : 0;

            List<SucursalXEmpleadoSeguimiento> listaSursales = citasVencidosService.obtenerSucursales(obtenerEmpleadoLogueado());
            int sucursalUsu = (listaSursales != null && !listaSursales.isEmpty()) ? listaSursales.get(0).getI_sucursal() : 0;

            int permisoUsuario = (permisoAdmin > 0) ? 1 : (permisoConsulta > 0 ? 2 : 0);

            if (sucursalUsu != 0 || permisoUsuario > 0) {
                List<Catalogo> listaEstatus = catalogoService.generarOpciones(Constantes.CATALOGO_GESTION);
                List<Catalogo> listaBusqueda = catalogoService.generarOpciones(Constantes.CATALOGO_OPCIONES_BUSQUEDA);

                model.addAttribute("permisoUsuario", permisoUsuario);
                model.addAttribute("sucursalInicial", sucursalUsu);
                model.addAttribute("sucursales", listaSursales);
                model.addAttribute("listaEstatus", listaEstatus);
                model.addAttribute("listaBusqueda", listaBusqueda);

                rutaPagina = Constantes.RUTA_VENTAS_REPORTE;
            } else {
                rutaPagina = Constantes.RUTA_ERROR_PERMISO;
            }

            return rutaPagina;
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
            return Constantes.RUTA_ERROR;
        }
    }

    public String obtenerUsuarioLogueado() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return SecurityContextHolder.getContext().getAuthentication().getName();
            }
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
            return null;
        }
    }

    public int obtenerEmpleadoLogueado() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.findByNombre(auth.getName());

            return (usuario != null) ? usuario.getI_id_empleado() : 0;
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
            return 0;
        }
    }

    public List<MicrofinUsuarioDTO> datosUsuario() {
        try {
            List<MicrofinUsuarioDTO> lista = new ArrayList<>();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.findByNombre(auth.getName());
            logger.info("se encontró el usuario");

            if (usuario != null) {
                lista = microfinUsuarioService.obtenerUsuario(usuario.getI_id_empleado());
            }

            logger.info("se llenó la lista");
            return (lista != null) ? lista : new ArrayList<>();
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR, e);
            return new ArrayList<>();
        }
    }
}