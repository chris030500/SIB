package ahorrosbienestar.sib.std.index.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.repository.IAccesoRepository;

@Service
public class AccesoService {
	private IAccesoRepository iAccesoRepository;
	
	@Autowired
	public AccesoService(IAccesoRepository iAccesoRepository) {
		this.iAccesoRepository = iAccesoRepository;
	}
	
	//Metodo para dar de alta usuario
	public Acceso save(Acceso acceso) {
		return iAccesoRepository.save(acceso);
	}

	public List<Acceso> findAll(){
		return iAccesoRepository.findAll();
	}
}
