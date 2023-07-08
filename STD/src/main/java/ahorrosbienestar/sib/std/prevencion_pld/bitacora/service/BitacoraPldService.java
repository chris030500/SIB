package ahorrosbienestar.sib.std.prevencion_pld.bitacora.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.repository.BitacoraPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BitacoraPldService {

	@Autowired
	private BitacoraPldRepository bitacoraPldRepository;
	
	public BitacoraPldModel findById(Integer id) {
		return bitacoraPldRepository.findById(id).orElse(null);
	}
	
	public BitacoraPldModel save(BitacoraPldModel model) {
		return bitacoraPldRepository.save(model);
	}
	
	public List<BitacoraPldModel> todoBitacora() {
		return bitacoraPldRepository.findAll();
	}
	
}
