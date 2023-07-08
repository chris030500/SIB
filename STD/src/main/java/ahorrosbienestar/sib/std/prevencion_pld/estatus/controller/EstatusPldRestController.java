package ahorrosbienestar.sib.std.prevencion_pld.estatus.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.service.EstatusPldService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EstatusPldRestController {
	
	@Autowired
	private EstatusPldService estatusPldService;
	
	@RequestMapping(value = {"/catalogo_estatus"}, method = RequestMethod.GET)
	public List<EstatusPldModel> mostrarCatalogo(@RequestParam("idLista") Integer idLista) {
		return estatusPldService.relacionEstatus(idLista);
	}

}
