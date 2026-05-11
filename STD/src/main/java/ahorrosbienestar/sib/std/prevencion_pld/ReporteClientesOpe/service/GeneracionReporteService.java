//GeneracionReporteService
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DescargaReporteRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
import java.net.URI;

import javax.persistence.PersistenceContext;
 
 
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
 
    public ResponseEntity<String> guardarFechasProceso(ProcesoReporte proceso) {
        if (procesoRepo.countPepCargados() == 0) {
            return ResponseEntity.badRequest().body("Debe cargar primero archivo PEP");
        }
        procesoRepo.save(proceso);
        return ResponseEntity.ok("Fechas guardadas correctamente");
    }
 
 
    public ResponseEntity<String> confirmarReporte(boolean confirmado) {
        if (!confirmado) {
            URI uri = URI.create("/pantalla/cargar-archivos");
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }
        return ResponseEntity.ok("Reporte confirmado");
    }
 
   
}