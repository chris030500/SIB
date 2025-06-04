package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoColocacion;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service.ProductoColocacionService;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import lombok.AllArgsConstructor;

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
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ProductoColocacionService productoColocacionService;
	
	private static String rutaDireccion = "";
	private static final int opcBusquedaGrupo = 32;
	private static final int opcBusquedaGrupo2 = 33;
	
	
	@GetMapping("/tiposCampanias")
	public String mostrarSolicitudes(){
		
		return "credito/tiposCampanias";
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
	

}
