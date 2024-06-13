package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.AsignacionOfertas;

@Repository
public interface AsignacionOfertasRepository extends JpaRepository<AsignacionOfertas, Integer> {

}
