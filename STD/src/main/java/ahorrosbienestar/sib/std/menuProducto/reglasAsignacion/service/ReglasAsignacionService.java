package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository.IReglasAsignacionRepository;

@Service
public class ReglasAsignacionService {
	
	private IReglasAsignacionRepository iReglasAsignacionRepository;
	
	@Autowired
	public ReglasAsignacionService(IReglasAsignacionRepository iReglasAsignacionRepository) {
		this.iReglasAsignacionRepository = iReglasAsignacionRepository;
	}

	public List<ReglasAsignacion> obtReglasAsignacion() {
		return iReglasAsignacionRepository.obtReglasAsignacion();
	}
}
