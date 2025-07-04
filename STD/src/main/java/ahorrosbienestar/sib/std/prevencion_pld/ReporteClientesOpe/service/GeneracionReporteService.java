package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DescargaReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DescargaReporteRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesoReporteRepository;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
 
import javax.persistence.EntityManager;
//import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
//import javax.persistence.StoredProcedureQuery;
//import java.io.*;
import java.net.URI;
//import java.time.LocalDateTime;
//import java.time.Year;
//import java.util.List;
 
@Service
public class GeneracionReporteService {
 
    private final ProcesoReporteRepository procesoRepo;
 
    @PersistenceContext
    private EntityManager entityManager;
 
    @Autowired
    public GeneracionReporteService(ProcesoReporteRepository procesoRepo,
                                    DescargaReporteRepository descargaRepo) {
        this.procesoRepo = procesoRepo;
    }
 
    public ResponseEntity<?> guardarFechasProceso(ProcesoReporte proceso) {
        if (procesoRepo.countPepCargados() == 0) {
            return ResponseEntity.badRequest().body("Debe cargar primero archivo PEP");
        }
        procesoRepo.save(proceso);
        return ResponseEntity.ok("Fechas guardadas correctamente");
    }

 
    public ResponseEntity<?> confirmarReporte(boolean confirmado) {
        if (!confirmado) {
            URI uri = URI.create("/pantalla/cargar-archivos");
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }
        return ResponseEntity.ok("Reporte confirmado");
    }
 
   
}
 