package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.HisTiposCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoColocacion;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service.ProductoColocacionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.HisTiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.TiposCampaniasRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("credito")

public class tiposCampaniasController {
	
	@Autowired
	private TiposCampaniasService service;
	
	@Autowired
	private HisTiposCampaniasService serviceHis;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ProductoColocacionService productoColocacionService;
	
	@Autowired
    private TiposCampaniasRepository repository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	List<Usuario> usuario = null;
	
	private static String rutaDireccion = "";
	private static final int opcBusquedaGrupo = 32;
	private static final int opcBusquedaGrupo2 = 33;
	
	
	@GetMapping("/tiposCampanias")
	public String mostrarSolicitudes(Model model){
		
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CREDITO_CAMPANIAS) == 1) {
				
				/////
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				UserDetails userDetails = (UserDetails) principal;
				usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				Integer idUsuario = usuario.get(0).getPk_id_usuario();
				//System.out.print("idUsuario:" + idUsuario);
				model.addAttribute("idUsuario", idUsuario);
				////
				
				rutaDireccion = Constantes.CREDITO_CAMPANIAS;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
		
		
		
		//return "credito/tiposCampanias";
	}
	
	//mostrar el listado de tipos campañas
	@GetMapping("/buscarCampanias")
    @ResponseBody  //Esto indica que el resultado debe devolverse como JSON
    public ResponseEntity<Map<String, Object>> listaCam(@RequestParam("valor") String valor) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<TiposCampaniasDTO> lista = new ArrayList<TiposCampaniasDTO>();
		try {
			lista = service.listaCampanias(valor);
			//logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
        //return service.listaCampanias(valor);
    }
	
	//mostrar los campos
	@GetMapping("/obtenerCampania")
	@ResponseBody
	public TiposCampaniasModel obtenerCampania(@RequestParam("id") Integer id) {
	    return service.obtenerCampaniaPorId(id);
	}
	
	//mostrar catalogos
	/*@GetMapping("/mostrarCatalogos")
	public String reportes(Model model) {

		try {
				model.addAttribute("cat_tipo_campanias", iCatalogoRepository.generarOpciones(opcBusquedaGrupo));
				//model.addAttribute("cat_reportes_pld_ext", iCatalogoRepository.generarOpciones(opcBusquedaGrupo2));
				// rutaDireccion = "reportesProductos/reportesProducto";
				rutaDireccion = "credito/tiposCampanias";
			
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error("Error:" + e.getMessage());
			return null;
		}
	}*/
	
	//llenar el combo de tipo campanias (temporales/recurrentes)
	@GetMapping("/api/catTipoCampanias")
	@ResponseBody
	public List<Catalogo> getCatalogoTipoCampanias() {
	    return iCatalogoRepository.generarOpciones(opcBusquedaGrupo);
	}
	
	//llenar el combo de convivencia entre campañas (si/no)
	@GetMapping("/api/catConvivenciaCamp")
	@ResponseBody
	public List<Catalogo> getCatalogoConvivenciaCamp() {
	    return iCatalogoRepository.generarOpciones(opcBusquedaGrupo2);
	}
	
	//llenar el combo de productos oferta
	@GetMapping("/api/catProductosCamp")
	@ResponseBody
	public ResponseEntity<List<ProductoColocacion>> getProductosCamp() {
        return ResponseEntity.ok(productoColocacionService.obtenerProductosActivos());
    }
	
	//llenar el combo opcion multiple de productos
	/*@GetMapping("/api/catProductos")
	@ResponseBody
	public List<ProductoColocacion> getCatalogoProductos() {
	    return productoColocacionService.getProductosColocacion();
	}*/
		
	@GetMapping("/api/catProductos")
	@ResponseBody
    public ResponseEntity<List<ProductoColocacion>> getProductos() {
        return ResponseEntity.ok(productoColocacionService.obtenerProductosActivos());
    }
	
	//llenar las opciones de es renovacion
	@GetMapping("/api/catEsRenovacion")
	@ResponseBody
	public List<Catalogo> getCatalogoEsRenovacion() {
	    return iCatalogoRepository.generarOpciones(opcBusquedaGrupo2);
	}
	
	//llenar las opciones de es renovacion
	@GetMapping("/api/catEsRenovacionOferta")
	@ResponseBody
	public List<Catalogo> getCatalogoEsRenovacionOferta() {
	    return iCatalogoRepository.generarOpciones(opcBusquedaGrupo2);
	}
	
	//llenar las opciones de es activa
	@GetMapping("/api/catActiva")
	@ResponseBody
	public List<Catalogo> getCatalogoActiva() {
	    return iCatalogoRepository.generarOpciones(opcBusquedaGrupo2);
	}
	
	//update de datos campaña
	/*@PostMapping("/api/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody TiposCampaniasModel campania) {
        // Verifica si existe
        Optional<TiposCampaniasModel> existente = repository.findById(campania.getPk_id_tipo_campania());
        if (existente.isPresent()) {
            repository.save(campania); // Actualiza todos los campos
            return ResponseEntity.ok("Actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaña no encontrada");
        }
    }*/
	
	@PostMapping("/api/actualizar")
	/*public ResponseEntity<String> actualizar(@RequestBody TiposCampaniasModel campania) {
	    boolean actualizado = service.actualizarCampania(campania);
	    if (actualizado) {
	        return ResponseEntity.ok("Actualizado correctamente");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaña no encontrada o sin cambios");
	    }
	}*/
	
	public ResponseEntity<String> actualizar(@RequestBody TiposCampaniasModel campania) {
	    try {
	        boolean actualizado = service.actualizarCampania(campania);
	        if (actualizado) {
	            return ResponseEntity.ok("Actualizado correctamente");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaña no encontrada o sin cambios");
	        }
	    } catch (Exception e) {
	        // Log the error here to get more information about what went wrong
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar la campaña: " + e.getMessage());
	    }
	}
	
	//alta de campaña
	/*@PostMapping("/api/crear")
	public ResponseEntity<String> crearCampania(@RequestBody TiposCampaniasModel campania) {
	    try {
	        repository.save(campania);
	        return ResponseEntity.ok("Campaña creada correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear campaña: " + e.getMessage());
	    }
	}*/
	@PostMapping("/api/crear")
	public ResponseEntity<String> crear(@RequestBody TiposCampaniasModel campania) {
	    try {
	        //boolean creada = service.crearCampania(campania);
	        
	    	if(service.save(campania) !=null) {
				return ResponseEntity.ok("Campaña guardada correctamente");
			}else {
				return ResponseEntity.ok("Error al guardar campania");
			}
	    	
	        /*if (creada) {
	            return ResponseEntity.ok("Campaña creada correctamente");
	        } else {
	        	
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la campaña");
	        }*/
	    } catch (Exception e) {
	    	//System.out.print("Error al crear campaña:::: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear campaña: " + e.getMessage());
	    }
	}
	
	@PostMapping("/api/crearHis")
	public ResponseEntity<String> crearHis(@RequestBody HisTiposCampaniasModel campania) {
	    try {
	        boolean creada = serviceHis.crearHisCampania(campania);
	        if (creada) {
	            return ResponseEntity.ok("Campaña creada correctamente");
	        } else {
	        	
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el historico la campaña");
	        }
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear el historico campaña: " + e.getMessage());
	    }
	}
	
	public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				//logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            //logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}
	


}
