package ahorrosbienestar.sib.std.cobranza.formatoBanorte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.formatoBanorte.model.FormatoBanorteModel;
import ahorrosbienestar.sib.std.cobranza.formatoBanorte.repository.FormatoBanorteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FormatoBanorteService {
	
	@Autowired
	private FormatoBanorteRepository formatoBanorteRepository;
	
	public List<FormatoBanorteModel> listaDeDatos() {
		return formatoBanorteRepository.findAll();
	}

}
