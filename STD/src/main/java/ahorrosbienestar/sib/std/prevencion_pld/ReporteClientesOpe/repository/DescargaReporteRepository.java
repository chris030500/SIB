package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DescargaReporteRepository extends JpaRepository<DescargaReporte, Long> {
    @Query("SELECT d FROM DescargaReporte d WHERE YEAR(d.fechaGeneracion) = :anio")
    List<DescargaReporte> findByAnio(@Param("anio") int anio);
}