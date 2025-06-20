package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteClientesOpeRepository extends JpaRepository<ReporteClientesOpeModel, Integer> {
    // ClienteID es la llave primaria
}

