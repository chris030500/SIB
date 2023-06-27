package ahorrosbienestar.sib.std.opcionMenu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;

@Service
public class RelRolOpcionesService {
	private IRelRolOpcionesRepository iRelRolOpciones;
	
	@Autowired
	public RelRolOpcionesService(IRelRolOpcionesRepository iRelRolOpciones) {
		this.iRelRolOpciones = iRelRolOpciones;
	}
	
}
