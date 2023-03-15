package ahorrosbienestar.sib.std.reportesJasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;


@Repository
public interface IReporteRepository extends JpaRepository<ReporteJasper, Integer>{
}
