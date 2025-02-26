package ahorrosbienestar.sib.std.presolicitudes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("presolicitudes")
public class presolicitudes {
	
	@GetMapping("/registro")
	public String FiltradoQEQ() {
		return "presolicitudes_nomina/presolicitudes";
	}

}
