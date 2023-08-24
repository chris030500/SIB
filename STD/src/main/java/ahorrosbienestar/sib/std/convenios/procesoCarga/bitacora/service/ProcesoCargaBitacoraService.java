package ahorrosbienestar.sib.std.convenios.procesoCarga.bitacora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.convenios.procesoCarga.bitacora.model.ProcesoCargaBitacoraModel;
import ahorrosbienestar.sib.std.convenios.procesoCarga.bitacora.repository.ProcesoCargaBitacoraRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcesoCargaBitacoraService {
	
	@Autowired
	private ProcesoCargaBitacoraRepository procesoCargaBitacoraRepository;
	
	public List<ProcesoCargaBitacoraModel> listaBitacora() {
		return procesoCargaBitacoraRepository.listaBitacora();
	}

}
