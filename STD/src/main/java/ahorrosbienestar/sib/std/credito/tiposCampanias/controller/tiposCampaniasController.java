package ahorrosbienestar.sib.std.credito.tiposCampanias.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import lombok.AllArgsConstructor;
import java.util.List;

@Controller
@RequestMapping("credito")
@AllArgsConstructor
public class tiposCampaniasController {
	
	@Autowired
	private TiposCampaniasService service;
	
	
	@GetMapping("/tiposCampanias")
	public String mostrarSolicitudes(){
		
		return "credito/tiposCampanias";
	}
	
	@GetMapping("/buscarCampanias")
    @ResponseBody  //Esto indica que el resultado debe devolverse como JSON
    public List<TiposCampaniasModel> buscarCampanias(@RequestParam String valor) {
        return service.listaCampanias(valor);
    }
	
    

}
