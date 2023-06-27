package ahorrosbienestar.sib.std.firma_doct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.firma_doct.model.FirmaDocto;
import ahorrosbienestar.sib.std.firma_doct.repository.IFirmaDoctoRepository;

@Service
public class FirmaDoctoService {

	private IFirmaDoctoRepository iFirmaDoctoRepository;
	
	@Autowired
	public FirmaDoctoService(IFirmaDoctoRepository iFirmaDoctoRepository) {
		this.iFirmaDoctoRepository = iFirmaDoctoRepository;
	}
	
	public FirmaDocto save(FirmaDocto firmaDocto) {
		return iFirmaDoctoRepository.save(firmaDocto);
	}
}
