package ahorrosbienestar.sib.std.menuProducto.administracionSMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository.IProcesoAsignacionRepository;

@Service
public class ProcesoAsignacionService {
	private IProcesoAsignacionRepository iProcesoAsignacionRepository;

	public List<Integer> procesosId() {
        return iProcesoAsignacionRepository.obtProcesoAsignacion();
    }
}
