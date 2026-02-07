package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;


@Repository
public interface EstadoProcesoReporteRepository
        extends JpaRepository<EstadoProcesoReporte, Integer> {

    Optional<EstadoProcesoReporte> 
        findByVcUsuarioAndEstatus(String vcUsuario, Integer estatus);
}

