package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;

public interface MigracionesPRepository extends JpaRepository<MigracionesP, Integer> {
}
