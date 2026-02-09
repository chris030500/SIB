package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;


    @Repository
    public interface HistorialReporteRepository extends JpaRepository<HistorialReporte, Integer> {
     
     
        @Query(value = "SELECT * FROM SIB.TBL_HISTORIAL_REPORTES_AUDITORIAS WHERE I_ESTATUS =1", nativeQuery = true)
        public List<HistorialReporte> procesoVobo();
    } 