package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmCatalogoReporte;


@Repository
public interface precrmCatalogoReporteRepository
        extends JpaRepository<precrmCatalogoReporte, Long> {


    // =========================================================
    // OBTENER REPORTES ACTIVOS
    // ORDENADOS SEGUN CONFIGURACION DEL CATALOGO
    // =========================================================

    List<precrmCatalogoReporte>
        findByActivoOrderByOrdenAsc(
            Integer activo
        );


    // =========================================================
    // BUSCAR POR CLAVE
    // =========================================================

    precrmCatalogoReporte
        findByClaveReporte(
            String claveReporte
        );

}