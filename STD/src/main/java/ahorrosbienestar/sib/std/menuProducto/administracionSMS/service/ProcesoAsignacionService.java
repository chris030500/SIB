package ahorrosbienestar.sib.std.menuproducto.administracionSMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.ProcesoAsignacion;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.repository.IProcesoAsignacionRepository;

@Service
public class ProcesoAsignacionService {
	
	private IProcesoAsignacionRepository iProcesoAsignacionRepository;

	@Autowired
	public ProcesoAsignacionService(IProcesoAsignacionRepository iProcesoAsignacionRepository) {
		this.iProcesoAsignacionRepository = iProcesoAsignacionRepository;
	}

	public List<ProcesoAsignacion> procesosId() {
        return iProcesoAsignacionRepository.obtProcesoAsignacion();
    }
}
