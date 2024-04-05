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
		String rutaPagina ="";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		
		int permisoAdmin = datosUsuario().size()>0?datosUsuario().get(0).getPermisoAdmin():0;
		int permisoConsulta = datosUsuario().size()>0?datosUsuario().get(0).getPermisoConsulta():0;
		
		int permisoUsuario = (permisoAdmin >0)?1:(permisoConsulta>0)?2:0;
		int permisoTelefono =  configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_TELEFONOS,userDetails.getUsername());
		int permisoCodAccionR = configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_COD_ACCION_TELEFONO,userDetails.getUsername());
		try {
			if(permisoUsuario >0 || permisoTelefono > 0 || permisoCodAccionR > 0) {
				model.addAttribute("permisoTelefono",permisoTelefono);
				model.addAttribute("permisoCodAccionR",permisoCodAccionR);
				model.addAttribute("permisoUsuario",permisoUsuario);
				rutaPagina = Constantes.RUTA_CONFIGURACION_VENTAS;
			}else {
				rutaPagina = Constantes.RUTA_ERROR_PERMISO;
			}
			return rutaPagina;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return  Constantes.RUTA_ERROR;
		}
	}
	
	@GetMapping("/seguimientoGestion")
	public String ModuloSeguimientoGestion(Model model) {
		String rutaPagina ="";
		
		int permisoAdmin = datosUsuario().size()>0?datosUsuario().get(0).getPermisoAdmin():0;
		int permisoConsulta = datosUsuario().size()>0?datosUsuario().get(0).getPermisoConsulta():0;
		List<SucursalXEmpleadoSeguimiento> listaSursales = citasVencidosService.obtenerSucursales(obtenerEmpleadoLogueado());
		int sucursalUsu = (listaSursales !=null && listaSursales.size()>0)?listaSursales.get(0).getI_sucursal():0;
		//prioridad de permiso
		int permisoUsuario = (permisoAdmin >0)?1:(permisoConsulta>0)?2:0;
		try {
			if(sucursalUsu != 0 || permisoUsuario >0) {
				model.addAttribute("permisoUsuario",permisoUsuario);
				model.addAttribute("sucursalInicial",sucursalUsu);
				model.addAttribute("sucursales", listaSursales);
				rutaPagina = Constantes.RUTA_SEGUIMIENTO_GESTION;
			}else {
				rutaPagina = Constantes.RUTA_ERROR_PERMISO;
			}

			return rutaPagina;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return  Constantes.RUTA_ERROR;
		}
	}

	@GetMapping("/reportes")
	public String ModuloReportes(Model model) {
		String rutaPagina ="";
		int permisoAdmin = datosUsuario().size()>0?datosUsuario().get(0).getPermisoAdmin():0;
		int permisoConsulta = datosUsuario().size()>0?datosUsuario().get(0).getPermisoConsulta():0;
		List<SucursalXEmpleadoSeguimiento> listaSursales = citasVencidosService.obtenerSucursales(obtenerEmpleadoLogueado());
		int sucursalUsu = (listaSursales !=null && listaSursales.size()>0)?listaSursales.get(0).getI_sucursal():0;
		//prioridad de permiso
		int permisoUsuario = (permisoAdmin >0)?1:(permisoConsulta>0)?2:0;
		try {
			if(sucursalUsu != 0 || permisoUsuario >0) {
				List<Catalogo> listaEstatus = catalogoService.generarOpciones(Constantes.CATALOGO_GESTION);
				List<Catalogo> listaBusqueda = catalogoService.generarOpciones(Constantes.CATALOGO_OPCIONES_BUSQUEDA);
				
				model.addAttribute("permisoUsuario",permisoUsuario);
				model.addAttribute("sucursalInicial",sucursalUsu);
				model.addAttribute("sucursales", listaSursales);
				model.addAttribute("listaEstatus", listaEstatus);
				model.addAttribute("listaBusqueda", listaBusqueda);
				
				rutaPagina = Constantes.RUTA_VENTAS_REPORTE;
			}else {
				rutaPagina = Constantes.RUTA_ERROR_PERMISO;
			}
			return rutaPagina;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return  Constantes.RUTA_ERROR;
		}
	
	}
	
	public String obtenerUsuarioLogueado() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			
			return userDetails.getUsername();
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return null;
		}
	}
	
	public int obtenerEmpleadoLogueado() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			
			return usuario.getI_id_empleado();
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return 0;
		}
	}
	
	public List<MicrofinUsuarioDTO> datosUsuario() {
		List<MicrofinUsuarioDTO> lista = new ArrayList<MicrofinUsuarioDTO>();
		//
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.findByNombre(auth.getName());
		logger.info("se encotró el usuario");
		//
		lista = microfinUsuarioService.obtenerUsuario(usuario.getI_id_empleado());
		logger.info("se llenó la lista");
		return lista.size()>0?lista:null;
	}
	
}
