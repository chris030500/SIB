package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.PRECRMCarga;

@Repository
public interface PRECRMCargaRepository
        extends JpaRepository<PRECRMCarga, Long> {

    boolean existsByNombreArchivo(String nombreArchivo);

    Optional<PRECRMCarga>
        findTopByOrderByFechaProcesamientoDesc();
}