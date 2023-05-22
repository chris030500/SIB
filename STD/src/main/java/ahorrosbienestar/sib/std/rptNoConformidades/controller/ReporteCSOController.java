package ahorrosbienestar.sib.std.rptNoConformidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.rptNoConformidades.service.ReporteCSOService;

@Controller
@RequestMapping("reporteFolio")
public class ReporteCSOController {

	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ReporteCSOService reporteCSOService;
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	
	@Autowired
	public ReporteCSOController(ReporteCSOService reporteCSOService) {
		this.reporteCSOService = reporteCSOService;
	} 
	
	@GetMapping("/noConformidades")
	public String inicioFolio(Model model) {
		try {
			model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
			model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			return "reporteFolio/noConformidades";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
}
