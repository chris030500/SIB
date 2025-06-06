package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteClientesOpeRepository extends JpaRepository<ReporteClientesOpeModel, Integer> {
   
}