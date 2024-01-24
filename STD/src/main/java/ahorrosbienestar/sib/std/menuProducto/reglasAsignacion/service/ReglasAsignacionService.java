package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IReglasAsignacionRepository;

@Service
public class ReglasAsignacionService {
	private static final Logger logger = Logger.getLogger(ReglasAsignacionService.class);
	private IReglasAsignacionRepository iReglasAsignacionRepository;
	
	@Autowired
	public ReglasAsignacionService(IReglasAsignacionRepository iReglasAsignacionRepository) {
		this.iReglasAsignacionRepository = iReglasAsignacionRepository;
	}

	public List<ReglasAsignacion> obtReglasAsignacion() {
		return iReglasAsignacionRepository.obtReglasAsignacion();
	}
	
	public ReglasAsignacion save(ReglasAsignacion reglasAsignacion) {
		try {
			return iReglasAsignacionRepository.save(reglasAsignacion);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	public Optional<ReglasAsignacion> findById(Integer idRegla){
		 try {
			 return iReglasAsignacionRepository.findById(idRegla);
		 } catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	 }
	
}
