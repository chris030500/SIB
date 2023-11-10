package ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.model.ProcesoBase;

@Repository
public interface IProcesoBaseRepository extends JpaRepository<ProcesoBase, Integer>{
	
}
