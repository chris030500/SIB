package ahorrosbienestar.sib.std.folios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.folios.model.Folios;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;

@Service
public class FolioService {

	private IFolioRepository iFolioRepository;
	
	@Autowired
	public FolioService(IFolioRepository iFolioRepository) {
		this.iFolioRepository = iFolioRepository;
	}
	
	public int existeFolio(Integer noficio) {
		return iFolioRepository.existeFolio(noficio);
	}

	public Folios save(Folios altaFolio) {
		return iFolioRepository.save(altaFolio);
		
	}
	
	//Metodo para buscar todos los usuarios
	public List<Folios> findAll(){
		return iFolioRepository.findAll();
	}
	
}
