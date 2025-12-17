package ahorrosbienestar.sib.std.catalogos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;

@Service
public class CatalogoService {

	private ICatalogoRepository  iCatalogoRepository;
	
	@Autowired
	public CatalogoService(ICatalogoRepository iCatalogoRepository) {
		this.iCatalogoRepository = iCatalogoRepository;
	}
	
	public List<Catalogo> generarOpciones(Integer id){
		return iCatalogoRepository.generarOpciones(id);
	}
	
	public Catalogo buscarPorId(Integer id) {
		return iCatalogoRepository.findById(id).orElse(null);
	}
	
	public List<Object[]> listaTipoBuro(Integer id_group, Integer tipo_campania) {
        return iCatalogoRepository.listaTipoBuro(id_group, tipo_campania);
    }
}
