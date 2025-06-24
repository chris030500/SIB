package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesosPEPsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcesosPEPsService {

	@Autowired
	private ProcesosPEPsRepository procesosPEPsRepository;
	
	public ProcesosPEPsModel save(ProcesosPEPsModel model) {
		return procesosPEPsRepository.save(model);
	}
}
