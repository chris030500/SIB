package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import java.util.List;

public interface ProcesoReporteRepository extends JpaRepository<ProcesoReporte, Long> {
    @Query("SELECT COUNT(r) FROM ReporteClientesOpeModel r")
    long countPepCargados();
}