package ahorrosbienestar.sib.std.credito.extraccioncampanias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.extraccioncampanias.model.ValBuroCreditoCampaniaModel;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.repository.ValBuroCreditoCampaniaRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class ValBuroCreditoCampaniaService {
	
	@Autowired
	private ValBuroCreditoCampaniaRepository repository;
	
	public ValBuroCreditoCampaniaModel save(ValBuroCreditoCampaniaModel model) {
		return repository.save(model);
	}
	
	public List<ValBuroCreditoCampaniaModel> saveAll(List<ValBuroCreditoCampaniaModel> model) {
        return repository.saveAll(model);
    }

}
