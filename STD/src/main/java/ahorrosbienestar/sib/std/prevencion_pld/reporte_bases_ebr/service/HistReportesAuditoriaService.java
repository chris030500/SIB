package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.IHistReportesAuditoriaRepository;

@Service
public class HistReportesAuditoriaService {
	
	@Autowired
	private IHistReportesAuditoriaRepository rptAuditoriaRepository;
	
	public List<Integer> listaAnioAuditorias() {
		return rptAuditoriaRepository.obtenerAñoAuditorias();
	}

}
