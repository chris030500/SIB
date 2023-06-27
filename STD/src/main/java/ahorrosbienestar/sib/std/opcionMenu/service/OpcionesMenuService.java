package ahorrosbienestar.sib.std.opcionMenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.opcionMenu.model.OpcionesMenu;
import ahorrosbienestar.sib.std.opcionMenu.repository.IOpcionesMenuRepository;

@Service
public class OpcionesMenuService {
	private IOpcionesMenuRepository iOpcionesMenuRepository;
	
	@Autowired
	public OpcionesMenuService(IOpcionesMenuRepository iOpcionesMenuRepository) {
		this.iOpcionesMenuRepository = iOpcionesMenuRepository;
	}
	
	public List<OpcionesMenu> findAll(){
		return iOpcionesMenuRepository.findAll();
	}
	
}
