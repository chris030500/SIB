package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.RegistrosAsignacion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository.IRegistrosAsignacionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrosAsignacionService {
	
	@Autowired
	private IRegistrosAsignacionRepository iRegistrosAsignacionRepository;
	
	public List<RegistrosAsignacion> obtRegistroParametros(){
		return iRegistrosAsignacionRepository.obtRegistroParametros();
	}

}
