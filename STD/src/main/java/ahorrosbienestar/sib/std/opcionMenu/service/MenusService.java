package ahorrosbienestar.sib.std.opcionMenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.opcionMenu.model.Menus;
import ahorrosbienestar.sib.std.opcionMenu.repository.IMenusRepository;

@Service
public class MenusService {
	private IMenusRepository iMenusRepository;
	
	@Autowired
	public MenusService(IMenusRepository iMenusRepository) {
		this.iMenusRepository = iMenusRepository;
	}
	
	public List<Menus> findAll(){
		return iMenusRepository.findAll();
	}
	
}
