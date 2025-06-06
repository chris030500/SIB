package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class ReporteClientesOpeService {

    @Autowired
    private ReporteClientesOpeRepository repository;

    public byte[] generarReporteClientesOperacionesAuditor() throws Exception {
        List<ReporteClientesOpeModel> datos = repository.findAll();

        // Ruta del archivo .jasper (compilado) en src/main/resources/reports/
        InputStream reporteStream = new ClassPathResource("reports/rpt_Clientes_operaciones_auditor.jasper").getInputStream();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
        HashMap<String, Object> parametros = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reporteStream, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}