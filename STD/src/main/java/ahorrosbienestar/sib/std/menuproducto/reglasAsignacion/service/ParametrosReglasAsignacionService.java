package ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.service;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.model.ParametrosReglasAsignacion;
import ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.repository.IParametrosReglasAsignacionRepository;

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
			return Collections.emptyList(); // Devuelve una lista vacía en caso de excepción
		}
	}
		
	public ParametrosReglasAsignacion save(ParametrosReglasAsignacion parametrosReglasAsignacion) throws Exception{
		try {
			return iParametrosReglasAsignacion.save(parametrosReglasAsignacion);

		} catch (Exception e) {
			logger.error("Error al guardar los parámetros de reglas de asignación: " + e.getMessage(), e);
	        throw new Exception("Error al guardar los parámetros de reglas de asignación", e);
		}
	}

	public Optional<ParametrosReglasAsignacion> findById(Integer idParametros) {
		try {
			return iParametrosReglasAsignacion.findById(idParametros);
		} catch (Exception e) {
			logger.error("Error al buscar el parámetro con ID " + idParametros + ": " + e.getMessage(), e);
	        return Optional.empty(); // Devuelve un Optional vacío en caso de excepción
		}
	}

	public int existeRegistro(Integer slAsignar, Integer tipoCliente, Integer slcTipoCartera,Integer idRegla, Integer tipoPrioridad) {
		try {
			return iParametrosReglasAsignacion.existeRegistro(slAsignar,tipoCliente,slcTipoCartera,idRegla,tipoPrioridad);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 1;
		}
	}

	public int existeRegistroAct(Integer slAsignar, Integer tipoCliente, Integer slcTipoCartera, Integer tipoPrioridad) {
		try {
			return iParametrosReglasAsignacion.existeRegistroAct(slAsignar,tipoCliente,slcTipoCartera,tipoPrioridad);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 1;
		}
	}

	
}
