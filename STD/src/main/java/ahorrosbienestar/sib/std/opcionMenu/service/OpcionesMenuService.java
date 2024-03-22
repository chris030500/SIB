package ahorrosbienestar.sib.std.opcionMenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.opcionMenu.model.OpcionesMenu;
import ahorrosbienestar.sib.std.opcionMenu.repository.IOpcionesMenuRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OpcionesMenuService {
	
	@Autowired
	private IOpcionesMenuRepository iOpcionesMenuRepository;

	public List<OpcionesMenu> findAll() {
		return iOpcionesMenuRepository.findAll();
	}

	public int buscarRelacion(String nombrepantalla) {

		return iOpcionesMenuRepository.buscarRelacion(nombrepantalla);
	}
	
    public int buscarRelacionController(String nombreController) {
   	 
        return iOpcionesMenuRepository.buscarRelacionController(nombreController);
    }

}
