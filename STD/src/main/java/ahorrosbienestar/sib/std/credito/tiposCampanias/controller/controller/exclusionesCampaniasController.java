package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.ExclusionMunicipioService;

@Controller
@RequestMapping("/credito")
public class exclusionesCampaniasController {
	
	@Autowired
	private ExclusionMunicipioService exclusionMunicipioService;
	

	@GetMapping("/exclusiones")
	public String mostrarExclusiones() {
		System.out.print("Si entra");
		return "credito/exclusiones"; // Thymeleaf buscará en templates/credito/exclusiones.html
	}

	@PostMapping("/cargarMunicipios")
	public ResponseEntity<String> cargarExcel(@RequestParam("archivo") MultipartFile archivo) {
	    try {
	        exclusionMunicipioService.procesarExcel(archivo);
	        return ResponseEntity.ok("Archivo procesado correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al procesar el archivo: " + e.getMessage());
	    }
	}

}
