package ahorrosbienestar.sib.std.carga_documentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CargaDocumentosController {

	@GetMapping("/carga_documentos")
	public String InicioUsuario() {
		return "documentos/carga_documentos";
	}
}
