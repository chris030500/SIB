package ahorrosbienestar.sib.std.folios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;

@Controller
@RequestMapping("folios")
public class FoliosController {
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	@GetMapping("/folio")
	public String inicioFolio(Model model) {
		try {
			model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
			model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			return "folios/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}

}
