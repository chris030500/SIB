package ahorrosbienestar.sib.std.prevencion_pld.exclusion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ExclusionPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.repository.ExclusionPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExclusionPldService {
	
	@Autowired
	private ExclusionPldRepository exclusionPldRepository;
	
	public ExclusionPldModel findById(Integer id) {
		return exclusionPldRepository.findById(id).orElse(null);
	}
	
	public ExclusionPldModel save(ExclusionPldModel model) {
		return exclusionPldRepository.save(model);
	}
	
	public void delete(Integer id) {
		exclusionPldRepository.deleteById(id);
	}
	
	public List<ExclusionPldModel> todoExclusion() {
		return exclusionPldRepository.findAll();
	}
	
	public ExclusionPldModel exclusionRegistrada(Integer idLista, Integer idEstatus) {
		return exclusionPldRepository.exclusionRegistrada(idLista, idEstatus);
	}

}
