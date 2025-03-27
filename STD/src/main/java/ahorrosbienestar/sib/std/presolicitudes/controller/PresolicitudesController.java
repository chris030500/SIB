package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.operaciones.reportes.controller.ReportesRestController;
import ahorrosbienestar.sib.std.presolicitudes.dto.CondicionesDTO;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;
import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;
import ahorrosbienestar.sib.std.presolicitudes.service.ClienteActivoService;
import ahorrosbienestar.sib.std.presolicitudes.service.CondicionesService;
import ahorrosbienestar.sib.std.presolicitudes.service.EmpresaService;
import ahorrosbienestar.sib.std.presolicitudes.service.PuestoService;
import ahorrosbienestar.sib.std.presolicitudes.service.SolicitudCreditoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

@Controller
@AllArgsConstructor
@RequestMapping("configuracionSolicitudes")
public class PresolicitudesController {

	private final Logger logger = Logger.getLogger(ReportesRestController.class); // Corregido para no ser estático

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ClienteActivoService clienteActivoService;

	@Autowired
	private PuestoService puestoService;

	@Autowired
	private CondicionesService condicionesService;
	
	@Autowired
    private SolicitudCreditoService solicitudCreditoService;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	private static final String nombrePantalla = "Presolicitudes";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion = "";
	private int idEmpleado = 0;
	private int sucur = 0;
	List<Usuario> usuario = null;
	
	@GetMapping("/solicitudes")
	public String FiltradoQEQ() throws IOException{
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			idEmpleado = usuario.get(0).getI_id_empleado();
			sucur = catalogosCEService.obtenerSucursalEmpleado(idEmpleado);
			System.out.print(idEmpleado);
			System.out.print(sucur);
			if (validarAcceso() == 1) {
				
				
				rutaDireccion = "presolicitudes_nomina/presolicitudes";
			} else {
				rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = { "/lista_empresas" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaGestiones() {
		Map<String, Object> respuesta = new HashMap<>();
		List<EmpresaSIB> listaCatalogos = new ArrayList<>();
		String mensajeError = null;
		try {
			listaCatalogos = empresaService.obtenerTodas();
			logger.info("Se llenó la lista");
			respuesta.put("lista", listaCatalogos);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// Manejo adecuado de excepciones
			logger.error("Error: " + e.getMessage(), e);
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@GetMapping("empresa/{nombre}")
    public ResponseEntity<EmpresaSIB> obtenerEmpresaId(@PathVariable String nombre) {
        try {
        	EmpresaSIB id = empresaService.obtenerIdEmpresa(nombre);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	@GetMapping("/lista_empleados")
	public ResponseEntity<List<Empleado>> obtenerClientesPorEmpresaEmpleado(@RequestParam Integer empresaEmpleado) {
		System.out.print(empresaEmpleado);
		List<Empleado> clientes = clienteActivoService.obtenerClientesPorEmpresaEmpleado(empresaEmpleado);
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/lista_productos")
	public ResponseEntity<List<String>> obtenerProductos(@RequestParam Integer empresa, @RequestParam(required = false) Integer puesto) {
	    List<String> productos;
	    
	    if (puesto != 1111) {
	        productos = condicionesService.obtenerProductosUnicosPorPuesto(empresa, puesto);
	    } else {
	        productos = condicionesService.obtenerProductosUnicosPorEmpresa(empresa);
	    }
	    
	    return ResponseEntity.ok(productos);
	}


	@GetMapping("/lista_plazos")
	public ResponseEntity<List<String>> obtenerPlazosPorProductoYEmpresa(@RequestParam String producto,
			@RequestParam Integer empresa) {
		List<String> plazos = condicionesService.obtenerPlazosPorProductoYEmpresa(producto, empresa);
		return ResponseEntity.ok(plazos);
	}

	@GetMapping("/obtener_puestos")
	public ResponseEntity<List<Puesto>> obtenerPuestos(@RequestParam("empresa") Integer empresa) {
		List<Puesto> puestos = puestoService.obtenerTodosLosPuestos(empresa);
		return ResponseEntity.ok(puestos);
	}

	@GetMapping("/condiciones")
	public ResponseEntity<CondicionesDTO> obtenerCondiciones(@RequestParam("producto") String producto,
			@RequestParam("empresa") Integer empresa, @RequestParam("tipoemp") Integer tipoEmp) {

		try {
			CondicionesDTO condiciones = condicionesService.obtenerCondiciones(producto, empresa, tipoEmp);

			if (condiciones != null) {
				return ResponseEntity.ok(condiciones); // Devuelve 200 con el DTO
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Devuelve 204 si no hay datos
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Manejo de error 500
		}
	}
	
	@PostMapping("/crearSolicitud")
	public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudCredito solicitud) {
	    try {
	    	solicitud.setEjecutivo(idEmpleado);
	    	solicitud.setSucursal(sucur);
	    	solicitud.setEstatus(1);
	        // Intentar guardar la nueva solicitud
	        SolicitudCredito nuevaSolicitud = solicitudCreditoService.guardarSolicitud(solicitud);
	        return ResponseEntity.ok(nuevaSolicitud);
	    } catch (Exception e) {
	        // Imprimir el error en la consola
	        System.out.println("Error al crear la solicitud de crédito: " + e.getMessage());
	        
	        // Enviar la respuesta de error al cliente
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error al crear la solicitud de crédito: " + e.getMessage());
	    }
	}
	
	public int validarAcceso() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
				opcionesMenuService.buscarRelacion(nombrePantalla));

	}
	
	@GetMapping("/listarSolicitudes")
	public ResponseEntity<?> listarSolicitudes(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	    try {
	        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	        Page<SolicitudCredito> solicitudes = solicitudCreditoService.listarSolicitudes(pageable);
	        return ResponseEntity.ok(solicitudes);
	    } catch (Exception e) {
	        System.out.println("Error en listarSolicitudes: " + e.getMessage());
	        e.printStackTrace(); // Agrega esto para ver más detalles en los logs
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error al listar solicitudes: " + e.getMessage());
	    }
	}
	
	@GetMapping("solicitud/{id}")
    public ResponseEntity<SolicitudCredito> obtenerSolicitudPorId(@PathVariable Integer id) {
        try {
            SolicitudCredito solicitud = solicitudCreditoService.obtenerSolicitudPorId(id);
            return ResponseEntity.ok(solicitud);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
