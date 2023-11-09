package ahorrosbienestar.sib.std.proceso_validacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.proceso_validacion.model.ProcesoValidacion;


@Repository
public interface IProcesoValidacionRepository extends JpaRepository<ProcesoValidacion, Integer> {

}
