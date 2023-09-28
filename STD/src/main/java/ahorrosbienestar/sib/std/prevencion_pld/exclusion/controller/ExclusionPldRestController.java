package ahorrosbienestar.sib.std.prevencion_pld.exclusion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ExclusionPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ValoresExclusionModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.service.ExclusionPldService;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.service.ValoresExclusionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ExclusionPldRestController {

	@Autowired
	private ExclusionPldService exclusionPldService;

	@Autowired
	private ValoresExclusionService valoresExclusionService;

	@RequestMapping(value = { "/exclusiones" }, method = RequestMethod.GET)
	public List<ValoresExclusionModel> mostrarExclusion() {
		return valoresExclusionService.listadoExclusion();
	}

	@RequestMapping(value = { "/exclusion_registrada" }, method = RequestMethod.GET)
	public Boolean exclusionRegistrada(@RequestParam("idLista") Integer idLista,
			@RequestParam("idEstatus") Integer idEstatus) {
		ExclusionPldModel model = exclusionPldService.exclusionRegistrada(idLista, idEstatus);
		if (model != null) {
			return true;
		} else {
			return false;
		}
	}

}
