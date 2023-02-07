package ahorrosbienestar.sib.std.config_ws.cat_conf_ws.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.service.CatConfWSService;

@Controller
public class CatConfWSController {
	
	@Autowired
	private CatConfWSService catConfWSService;
	
	@GetMapping("/catConfWs/obtenerParamWSAuten")
	public ResponseEntity<?> obtenerParamWSAuten(){
		
		String parametro1 = catConfWSService.findById(1).get().getVc_valor_parametro1();
		String parametro2 = catConfWSService.findById(1).get().getVc_valor_parametro2();
		String parametro3 = catConfWSService.findById(1).get().getVc_valor_parametro3();
		
		List<String> parametros = new ArrayList<>(Arrays.asList(parametro1, parametro2, parametro3));
		return ResponseEntity.ok(parametros);
	}

}
