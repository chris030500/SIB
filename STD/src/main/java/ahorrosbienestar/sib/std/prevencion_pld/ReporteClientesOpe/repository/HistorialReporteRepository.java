package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;


@Repository
public interface HistorialReporteRepository 
        extends JpaRepository<HistorialReporte, Integer> {

    List<HistorialReporte> findByEstatus(Integer estatus);

    @Modifying
    @Transactional
    @Query(value ="UPDATE HistorialReporte h " +
           "SET h.estatus  = 0, " +
           "h.i_usuario_vobo  = null " +
           "WHERE h.tipoReporte  = :tipo " +
           "AND h.anioGeneracion  = :anio")
    int desactivarVobosPorTipoYAnio(
            @Param("tipo") String tipo,
            @Param("anio") Integer anio
    );

    HistorialReporte findTopByTipoReporteAndAnioGeneracionOrderByIdDesc(
            String tipoReporte,
            Integer anioGeneracion
    );
}



