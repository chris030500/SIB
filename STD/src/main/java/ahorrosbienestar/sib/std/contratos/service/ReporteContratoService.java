package ahorrosbienestar.sib.std.contratos.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import ahorrosbienestar.sib.std.contratos.repository.IReporteContratoRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReporteContratoService implements IReporteContratoRepository {
	
	@Autowired
	private DataSource datasource;
	
	
	@Override
	@NotNull
	public ResponseEntity<Resource> export(int id, int solicitud) {
		
		try {
	        final File file = ResourceUtils.getFile("classpath:reports/rpt_contrato_std.jasper");
	        final File imgLogo = ResourceUtils.getFile("classpath:reports/logoNegro.jpg");
	        final JasperReport report = (JasperReport) JRLoader.loadObject(file);

	        final HashMap<String, Object> parameters = new HashMap<>();
	        //parameters.put("nombreCliente", pedido.getCliente().getNombreCompletoCiente());
	        parameters.put("imgLogo", new FileInputStream(imgLogo));
	        parameters.put("ID", id);
	        
	        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, datasource.getConnection());
	        //JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, new JREmptyDataSource());
	        byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
	        
	        
	        String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
	        StringBuilder stringBuilder = new StringBuilder().append("Contrato_Apertura:");
	        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
	            .filename(stringBuilder.append(solicitud)
	            	.append("_")
	                .append(sdf)
	                .append(".pdf")
	                .toString())
	            .build();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDisposition(contentDisposition);
	        return ResponseEntity.ok().contentLength((long) reporte.length)
	            .contentType(MediaType.APPLICATION_PDF)
	            .headers(headers).body(new ByteArrayResource(reporte));
	      } catch (Exception e) {
	        e.printStackTrace();
	        System.out.print(""+e.getMessage());
	      }
		return null;
	}
	

}
