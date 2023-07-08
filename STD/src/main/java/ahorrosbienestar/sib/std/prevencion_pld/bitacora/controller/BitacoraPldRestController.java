package ahorrosbienestar.sib.std.prevencion_pld.bitacora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.service.BitacoraPldService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BitacoraPldRestController {

	@Autowired
	private BitacoraPldService bitacoraPldService;
	
	@RequestMapping(value = {"/datos_bitacora"}, method = RequestMethod.GET)
	public List<BitacoraPldModel> listaBitacora() {
		return bitacoraPldService.todoBitacora();
	}
	
}
