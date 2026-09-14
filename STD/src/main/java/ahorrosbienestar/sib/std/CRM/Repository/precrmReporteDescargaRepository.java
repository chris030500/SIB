package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmReporteDescarga;


@Repository
public interface precrmReporteDescargaRepository
        extends JpaRepository<precrmReporteDescarga, Long> {


    // =========================================================
    // HISTORIAL COMPLETO
    // MAS RECIENTE PRIMERO
    // =========================================================

    List<precrmReporteDescarga>
        findAllByOrderByFechaDescargaDesc();


    // =========================================================
    // HISTORIAL POR USUARIO
    // =========================================================

    List<precrmReporteDescarga>
        findByUsuarioOrderByFechaDescargaDesc(
            String usuario
        );


    // =========================================================
    // HISTORIAL POR REPORTE
    // =========================================================

    List<precrmReporteDescarga>
        findByFkReporteOrderByFechaDescargaDesc(
            Long fkReporte
        );

}