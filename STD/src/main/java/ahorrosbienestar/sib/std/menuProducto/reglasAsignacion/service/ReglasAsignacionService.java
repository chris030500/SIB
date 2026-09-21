package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IReglasAsignacionRepository;

@Service
public class ReglasAsignacionService {
	private static final Logger logger =
	        LoggerFactory.getLogger(ReglasAsignacionService.class);
	private IReglasAsignacionRepository iReglasAsignacionRepository;
	
	@Autowired
	public ReglasAsignacionService(IReglasAsignacionRepository iReglasAsignacionRepository) {
		this.iReglasAsignacionRepository = iReglasAsignacionRepository;
	}

	public List<ReglasAsignacion> obtReglasAsignacion() {
		return iReglasAsignacionRepository.obtReglasAsignacion();
	}
	
	public ReglasAsignacion save(ReglasAsignacion reglasAsignacion) throws Exception{
		try {
			return iReglasAsignacionRepository.save(reglasAsignacion);
		} catch (Exception e) {
			 logger.error("Error al guardar la regla de asignación: " + e.getMessage(), e);
		     throw new Exception("Error al guardar la regla de asignación", e);
		}
	}
	public Optional<ReglasAsignacion> findById(Integer idRegla){
		 try {
			 return iReglasAsignacionRepository.findById(idRegla);
		 } catch (Exception e) {
			logger.error("Error al buscar la regla de asignación con ID " + idRegla + ": " + e.getMessage(), e);
			return Optional.empty(); 
		}
		
	 }

	public int existeActivo() {
		return iReglasAsignacionRepository.existeActivo();
	}
	
}
