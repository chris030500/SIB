package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;

@Repository
public interface IReporteClientesOpeRepository extends JpaRepository<ReporteClientesOpeModel, Long> {
}
