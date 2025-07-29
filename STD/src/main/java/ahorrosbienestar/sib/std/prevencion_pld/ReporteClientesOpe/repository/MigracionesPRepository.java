package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigracionesPRepository extends JpaRepository<MigracionesP, Integer> {
}

