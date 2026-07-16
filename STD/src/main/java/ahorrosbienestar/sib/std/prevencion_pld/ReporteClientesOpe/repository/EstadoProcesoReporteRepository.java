package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface EstadoProcesoReporteRepository
        extends JpaRepository<EstadoProcesoReporte, Integer> {

    Optional<EstadoProcesoReporte>
    findByVcUsuarioAndEstatus(String vcUsuario, Integer estatus);

    Optional<EstadoProcesoReporte>
    findFirstByVcUsuarioAndEstatusOrderByFechaCreacionDesc(
        String vcUsuario,
        Integer estatus
    );
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE EstadoProcesoReporte e " +
                   "SET e.estatus = 0 " +
                   "WHERE e.vcUsuario = :usuario " +
                   "AND e.estatus = 1")
    int desactivarProcesosActivosPorUsuario(
            @Param("usuario") String usuario
    );
}


