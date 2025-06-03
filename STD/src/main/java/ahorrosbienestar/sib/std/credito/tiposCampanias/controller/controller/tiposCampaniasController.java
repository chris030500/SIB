package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
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
	
	
	@GetMapping("/tiposCampanias")
	public String mostrarSolicitudes(){
		
		return "credito/tiposCampanias";
	}
	
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
	
	@GetMapping("/obtenerCampania")
	@ResponseBody
	public TiposCampaniasModel obtenerCampania(@RequestParam("id") Integer id) {
	    return service.obtenerCampaniaPorId(id);
	}
	

}
