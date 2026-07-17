package ahorrosbienestar.sib.std.contratos.service;

import javax.sql.DataSource;

import jakarta.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contratos.repository.IReporteContratoRepository;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;

@Service
public class ReporteContratoService implements IReporteContratoRepository {

    private static final Logger logger = Logger.getLogger(ReporteContratoService.class);

    @Autowired
    private DataSource datasource;

    @Autowired
    JasperReportController jasperReportController;

    @Override
    @NotNull
    public ResponseEntity<Resource> export(int id, int solicitud) {
		
		try {
			/*
	        //final File file = ResourceUtils.getFile("classpath:reports/rpt_contrato_std.jasper");
	        //final File imgLogo = ResourceUtils.getFile("classpath:reports/logoNegro.jpg");
	        //final JasperReport report = (JasperReport) JRLoader.loadObject(file);

	        //final HashMap<String, Object> parameters = new HashMap<>();
	        //parameters.put("nombreCliente", pedido.getCliente().getNombreCompletoCiente());
	        //parameters.put("imgLogo", new FileInputStream(imgLogo));
	        //parameters.put("ID", id);
	        
	       // JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, datasource.getConnection());
	        //JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, new JREmptyDataSource());
			byte[] reporte = jasperReportController.reporteJasper();
	        
	        
	        String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
	        StringBuilder stringBuilder = new StringBuilder().append("Contrato_Apertura:");
	        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
	            .filename(stringBuilder.append(solicitud)
	            	.append("_")
	                .append(sdf)
	                .append(".xls")
	                .toString())
	            .build();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(contentDisposition);
	        return ResponseEntity.ok().contentLength((long) reporte.length)
	            .contentType(MediaType.APPLICATION_PDF)
	            .headers(headers).body(new ByteArrayResource(reporte));
	        */
	        
			 return null;

        } catch (Exception e) {
            logger.error("Error al exportar reporte de contrato. ID: " + id + ", solicitud: " + solicitud, e);
            return null;
        }
    }
}
