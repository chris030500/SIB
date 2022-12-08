package ahorrosbienestar.sib.std.carga_documentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;


@Controller
public class CargaDocumentosController {
	
	@Autowired
	private CoreBancarioService coreBancarioService;

	@GetMapping("/carga_documentos")
	public String InicioUsuario() {
		
		return "documentos/carga_documentos";
	
	}
	
}
