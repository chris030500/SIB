package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.Parametrosimulador;
import ahorrosbienestar.sib.std.presolicitudes.repository.ParametrosimuladorRepository;

@Service
public class ParametrosimuladorService {

	@Autowired
	private ParametrosimuladorRepository parametrosimuladorRepository;

	@Autowired
	public ParametrosimuladorService(ParametrosimuladorRepository parametrosimuladorRepository) {
		this.parametrosimuladorRepository = parametrosimuladorRepository;
	}
	
	public List<Parametrosimulador> obtFrecuencias() {
		return parametrosimuladorRepository.findAll();
	}
}
