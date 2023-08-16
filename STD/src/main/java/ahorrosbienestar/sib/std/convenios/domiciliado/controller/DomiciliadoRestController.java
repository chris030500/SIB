package ahorrosbienestar.sib.std.convenios.domiciliado.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.convenios.domiciliado.dto.ClabeDTO;
import ahorrosbienestar.sib.std.convenios.domiciliado.model.CuentasModel;
import ahorrosbienestar.sib.std.convenios.domiciliado.service.CuentasService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DomiciliadoRestController {
	
	@Autowired
	private CuentasService cuentasService;
	
	@RequestMapping(value = {"/recibir_clabe"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<ClabeDTO> datosCuentaClabe(@RequestBody List<ClabeDTO> listaClabeDTO) {
		List<ClabeDTO> listaRes = new ArrayList<ClabeDTO>();
		listaClabeDTO.forEach(clabe -> {
			CuentasModel model = cuentasService.datosCuentaClabe(clabe.getClabe());
			ClabeDTO dto = new ClabeDTO();
			dto.setClienteId(model.getClienteId());
			dto.setClabe(model.getClabe());
			dto.setCreditoId("0" + model.getCreditoId());
			dto.setRfcOficial(model.getRfcOficial());
			listaRes.add(dto);
		});
		return listaRes;
	}

}
