package ahorrosbienestar.sib.std.prevencion_pld.listas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.service.ListasPldService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ListasPldRestController {
	
	@Autowired
	private ListasPldService listasPldService;
	
	@RequestMapping(value = {"/catalogo_listas"}, method = RequestMethod.GET)
	public List<ListasPldModel> mostrarCatalogo() {
		return listasPldService.listasActivas();
	}

}
