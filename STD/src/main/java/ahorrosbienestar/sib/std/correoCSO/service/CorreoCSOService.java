package ahorrosbienestar.sib.std.correoCSO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.correoCSO.model.CorreoCSO;
import ahorrosbienestar.sib.std.correoCSO.repository.ICorreoCSORepository;
import ahorrosbienestar.sib.std.folios.model.Folios;

@Service
public class CorreoCSOService {
	@Autowired
	private ICorreoCSORepository iCorreoCSORepository;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	public CorreoCSOService(ICorreoCSORepository iCorreoCSORepository) {
		this.iCorreoCSORepository = iCorreoCSORepository;
	}

	public List<CorreoCSO> registrarCorreo(int pk_id_observacion) {
				
		List<CorreoCSO> listaCorreos = catalogosCEService.datosCorreo(pk_id_observacion);
		return iCorreoCSORepository.saveAll(listaCorreos);
	}

}
