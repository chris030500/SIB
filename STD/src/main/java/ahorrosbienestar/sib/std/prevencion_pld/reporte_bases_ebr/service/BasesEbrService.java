package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.BasesEbrRepository;

@Service
public class BasesEbrService {
	
	@Autowired
	private BasesEbrRepository basesEbrRepository;
	
	public void ejecutarVoBo(int idUsuario) {
		basesEbrRepository.ejecutarVoBo(idUsuario);
	}
}
