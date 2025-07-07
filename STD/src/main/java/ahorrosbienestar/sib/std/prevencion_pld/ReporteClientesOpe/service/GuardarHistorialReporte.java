package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.HistorialReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GuardarHistorialReporte {

    private final HistorialReporteRepository historialRepo;

    public GuardarHistorialReporte(HistorialReporteRepository historialRepo) {
        this.historialRepo = historialRepo;
    }

    public void registrarHistorial() {
        HistorialReporte historial = new HistorialReporte();

        historialRepo.save(historial);
    }

	public List<HistorialReporte> findAll() {
		return historialRepo.findAll();
	}
	
	public List<HistorialReporte> procesoVobo() {
		return historialRepo.procesoVobo();
	}
}


