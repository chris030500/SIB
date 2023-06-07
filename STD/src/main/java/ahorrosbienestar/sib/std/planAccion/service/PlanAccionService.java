package ahorrosbienestar.sib.std.planAccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.planAccion.repository.IPlanAccionRepository;

@Service
public class PlanAccionService {

	private IPlanAccionRepository iPlanAccionRepository;
	
	@Autowired
	public PlanAccionService(IPlanAccionRepository iPlanAccionRepository) {
		this.iPlanAccionRepository = iPlanAccionRepository;
	}
	
	public int modificarPA(Integer idPA, String nombreActividad, Integer idEncargado, String dtFechaInicio,
			String dtFechaVencimiento, int pk_id_usuario,int confirmaResponsable) {
		return iPlanAccionRepository.modificarPA(idPA,nombreActividad,idEncargado,dtFechaInicio,dtFechaVencimiento,pk_id_usuario,confirmaResponsable);
		
	}

}
