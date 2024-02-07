package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IParametrosReglasAsignacionRepository;

@Service
public class ParametrosReglasAsignacionService {
	private static final Logger logger = Logger.getLogger(ParametrosReglasAsignacionService.class);
	private IParametrosReglasAsignacionRepository iParametrosReglasAsignacion;
	
	@Autowired
	public ParametrosReglasAsignacionService(IParametrosReglasAsignacionRepository iParametrosReglasAsignacion) {
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
		
	public ParametrosReglasAsignacion save(ParametrosReglasAsignacion parametrosReglasAsignacion) {
		try {
			return iParametrosReglasAsignacion.save(parametrosReglasAsignacion);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Optional<ParametrosReglasAsignacion> findById(Integer idParametros) {
		try {
			return iParametrosReglasAsignacion.findById(idParametros);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public int existeRegistro(Integer slAsignar, Integer tipoCliente, Integer slcTipoCartera,Integer idRegla) {
		try {
			return iParametrosReglasAsignacion.existeRegistro(slAsignar,tipoCliente,slcTipoCartera,idRegla);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 1;
		}
	}

	
}
