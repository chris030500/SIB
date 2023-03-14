package ahorrosbienestar.sib.std.reportesJasper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
@Service
public class JasperReportService {

	private IReporteRepository iReporteRepository;
	
	@Autowired
	public JasperReportService(IReporteRepository iReporteRepository) {
		this.iReporteRepository = iReporteRepository;
	}
	
	public ReporteJasper findByNombre(String nombreReporte) {
		return iReporteRepository.findByNombre(nombreReporte);
	}
	
}
