package ahorrosbienestar.sib.std.comunes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Component
public class generarJasper {
	
	
	private static final String REPORT_FOLDER= "reports";
	private static final String JASPER = ".jasper";
	
	
	public ByteArrayOutputStream export(String fileName, String tipoReporte,Map<String, Object> params,Connection con){
		
			
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ClassPathResource resource = new ClassPathResource(REPORT_FOLDER+File.separator+fileName+JASPER);
		
		try {
			InputStream inputStream = resource.getInputStream();
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params,con);
			if(tipoReporte.equalsIgnoreCase(Constantes.getREPORTE_EXCEL().toString())) {
				
				JRXlsExporter export = new JRXlsExporter();
				export.setExporterInput(new SimpleExporterInput(jasperPrint));
				export.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setDetectCellType(true);
	            configuration.setCollapseRowSpan(true);
	            export.setConfiguration(configuration);
	            
	            export.exportReport();

	            
			}else {
				JasperExportManager.exportReportToPdfStream(jasperPrint, stream);	
			}	
			return stream;
			
			
		} catch (Exception e) {
			System.out.print(""+e.getMessage());
		}
		return stream;
	}
	
}
