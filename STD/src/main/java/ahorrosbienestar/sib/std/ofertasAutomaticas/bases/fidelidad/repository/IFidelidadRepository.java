package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.model.Fidelidad;


@Repository
public interface IFidelidadRepository extends JpaRepository<Fidelidad, Integer>{

}
