package ahorrosbienestar.sib.std.seguimientoFolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.seguimientoFolio.repository.ISeguimientoFolioRepository;

@Service
public class SeguimientoFolioService {

	private ISeguimientoFolioRepository iSeguimientoFolioRepository;
	
	@Autowired
	public SeguimientoFolioService(ISeguimientoFolioRepository iSeguimientoFolioRepository) {
		this.iSeguimientoFolioRepository = iSeguimientoFolioRepository;
	}

	public int modificarSeguimiento(Integer idSeguimiento, String desc_seguimiento, Integer estatus, Integer usuario) {
		
		return iSeguimientoFolioRepository.modificarSeguimiento(idSeguimiento,desc_seguimiento,estatus,usuario);
	}
	
	
}
