package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IParametrosReglasAsignacion;

@Service
public class ParametrosReglasAsignacionService {
	private static final Logger logger = Logger.getLogger(ParametrosReglasAsignacionService.class);
	private IParametrosReglasAsignacion iParametrosReglasAsignacion;
	
	@Autowired
	public ParametrosReglasAsignacionService(IParametrosReglasAsignacion iParametrosReglasAsignacion) {
		this.iParametrosReglasAsignacion = iParametrosReglasAsignacion;
	}

	public List<ParametrosReglasAsignacion> obtRegParams(Integer idRegla) {
		try {
			return iParametrosReglasAsignacion.obtRegParams(idRegla);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	

	
}
