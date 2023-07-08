package ahorrosbienestar.sib.std.prevencion_pld.estatus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.repository.EstatusPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstatusPldService {

	@Autowired
	private EstatusPldRepository estatusPldRepository;
	
	public EstatusPldModel findById(Integer id) {
		return estatusPldRepository.findById(id).orElse(null);
	}
	
	public EstatusPldModel save(EstatusPldModel model) {
		return estatusPldRepository.save(model);
	}
	
	public List<EstatusPldModel> relacionEstatus(Integer idLista) {
		return estatusPldRepository.relacionEstatus(idLista);
	}
	
	public List<EstatusPldModel> estatusActivos() {
		return estatusPldRepository.estatusActivos();
	}
	
}
