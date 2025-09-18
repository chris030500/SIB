package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.PeriodoEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.IPeriodoEbrRepository;

@Service
public class PeriodoEbrService {
	
	@Autowired
	private IPeriodoEbrRepository periodoRepository;
	
    public PeriodoEbr obtenerPeriodoEbr(int anio, LocalDate fechaInicio, LocalDate fechaFin) {
        return periodoRepository.obtenerPeriodoEbr(anio, fechaInicio, fechaFin)
                .orElseThrow(() -> new IllegalArgumentException("Las fechas no corresponden a un periodo válido"));
    }

}
