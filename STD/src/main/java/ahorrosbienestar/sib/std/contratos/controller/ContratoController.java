package ahorrosbienestar.sib.std.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.contratos.model.Contrato;
import ahorrosbienestar.sib.std.contratos.service.ContratoService;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

	private ContratoService contratoService;

	@Autowired
	public ContratoController(ContratoService contratoService) {
		this.contratoService = contratoService;
	}

	@GetMapping("/generar_contrato")
	public String InicioContrato(Model model) {

		Contrato contrato = new Contrato();
		model.addAttribute("contrato", contrato);
		model.addAttribute("contrato", contratoService.findAll());
		return "contratos/generar_contrato";
	}
	
}
