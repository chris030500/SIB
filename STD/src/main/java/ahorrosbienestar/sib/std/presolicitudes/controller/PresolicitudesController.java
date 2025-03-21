package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.operaciones.reportes.controller.ReportesRestController;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;
import ahorrosbienestar.sib.std.presolicitudes.service.ClienteActivoService;
import ahorrosbienestar.sib.std.presolicitudes.service.EmpresaService;
import ahorrosbienestar.sib.std.presolicitudes.service.PuestoService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("configuracionSolicitudes")
public class PresolicitudesController {
	
	private final Logger logger = Logger.getLogger(ReportesRestController.class);  // Corregido para no ser estático
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ClienteActivoService clienteActivoService;
	
	@Autowired
    private PuestoService puestoService;
	
	@GetMapping("/solicitudes")
	public String FiltradoQEQ() {
		return "presolicitudes_nomina/presolicitudes";
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
	
	@GetMapping("/lista_empleados")
	public ResponseEntity<List<Empleado>> obtenerClientesPorEmpresaEmpleado(
            @RequestParam Integer empresaEmpleado) {
		System.out.print(empresaEmpleado);
        List<Empleado> clientes = clienteActivoService.obtenerClientesPorEmpresaEmpleado(empresaEmpleado);
        return ResponseEntity.ok(clientes);
    }
	
	@GetMapping("/obtener_puestos")
    public ResponseEntity<List<Puesto>> obtenerPuestos() {
        List<Puesto> puestos = puestoService.obtenerTodosLosPuestos();
        return ResponseEntity.ok(puestos);
    }
}
