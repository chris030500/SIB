package ahorrosbienestar.sib.std.credito.extraccioncampanias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.extraccioncampanias.model.VigenciaBuroCampaniasModel;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.repository.VigenciaBuroCampaniasRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class VigenciaBuroCampaniasService {
	
	@Autowired
	private VigenciaBuroCampaniasRepository repository;
	
	public VigenciaBuroCampaniasModel save(VigenciaBuroCampaniasModel model) {
		return repository.save(model);
	}
	
	public List<VigenciaBuroCampaniasModel> saveAll(List<VigenciaBuroCampaniasModel> model) {
        return repository.saveAll(model);
    }

}

