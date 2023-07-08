package ahorrosbienestar.sib.std.prevencion_pld.listas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.repository.ListasPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListasPldService {

	@Autowired
	private ListasPldRepository listasPldRepository;
	
	public ListasPldModel findById(Integer id) {
		return listasPldRepository.findById(id).orElse(null);
	}
	
	public ListasPldModel save(ListasPldModel model) {
		return listasPldRepository.save(model);
	}
	
	public List<ListasPldModel> listasActivas() {
		return listasPldRepository.listasActivas();
	}
	
}
