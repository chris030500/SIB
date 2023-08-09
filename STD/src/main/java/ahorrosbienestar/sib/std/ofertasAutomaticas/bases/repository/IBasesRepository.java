package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;

@Repository
public interface IBasesRepository extends JpaRepository<Bases, Integer>{

}
