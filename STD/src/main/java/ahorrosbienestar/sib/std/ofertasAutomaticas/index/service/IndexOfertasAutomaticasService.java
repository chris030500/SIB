package ahorrosbienestar.sib.std.ofertasAutomaticas.index.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;

@Service
public class IndexOfertasAutomaticasService {
	
	private static final Logger logger = Logger.getLogger(IndexOfertasAutomaticasService.class);

	
	@Autowired 
	private JasperReportController jasperReportController;
	@Autowired 
	private IReporteRepository iReporteRepository;
	@Autowired
	private BasesService basesService;
	
	private static final String nombreReporte = "rpt_layout_Ofertas_Automaticas";
	
	public void generarArchivoOA() {
		try {
			String parametros="";
			String nombreArchivo = "rpt_layout_Ofertas_Automaticas";
			Bases datos = basesService.findById(2).get();
			String urlDirectorio = datos.getVc_ruta_proceso();
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			jasperReportController.generarArchivoRuta(datosReporte.getPk_id_reporte(), parametros, datosReporte.getVc_extension(), nombreArchivo, urlDirectorio);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			System.out.println("Error"+e.getMessage());
		}

	}
}
