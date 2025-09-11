package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.VoboBasesEbrRepository;

@Service
public class VoboBasesEbrService {
	
	@Autowired
	private VoboBasesEbrRepository voboRepository;
	
	public void ejecutarVoBo(int idUsuario) {
		voboRepository.ejecutarVoBo(idUsuario);
	}

}
