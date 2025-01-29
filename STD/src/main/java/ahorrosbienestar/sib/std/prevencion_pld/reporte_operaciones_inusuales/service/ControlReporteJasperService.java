package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ControlReporteJasper;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.repository.IControlReporteJasperRepository;

@Service
public class ControlReporteJasperService {
	
	@Autowired
	private IControlReporteJasperRepository reporteJasperRepository;
	
	public ControlReporteJasper inactivarReporteJasper(int id, int estatus) {
		ControlReporteJasper reporteJasper = reporteJasperRepository.findById(id).orElse(null);
		if (reporteJasper != null) {
			reporteJasper.setiEstatusAnteriorControlReporte(reporteJasper.getiEstatusControlReporte());
			
			reporteJasper.setiEstatusControlReporte(estatus);
			
			return reporteJasperRepository.save(reporteJasper);
		}
		
		return null;
	}
	
	public ControlReporteJasper getReporteJasper(int idReporte) {
		return reporteJasperRepository.findById(idReporte).orElse(null);	
	}

}
