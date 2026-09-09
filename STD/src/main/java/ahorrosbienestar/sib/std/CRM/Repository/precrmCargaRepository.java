package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmCarga;

@Repository
public interface precrmCargaRepository
        extends JpaRepository<precrmCarga, Long> {


    boolean existsByNombreArchivo(
        String nombreArchivo
    );


    Optional<precrmCarga>
        findTopByOrderByFechaProcesamientoDesc();
}