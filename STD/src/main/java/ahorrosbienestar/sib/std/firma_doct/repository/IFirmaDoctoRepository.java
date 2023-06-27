package ahorrosbienestar.sib.std.firma_doct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.firma_doct.model.FirmaDocto;

@Repository
public interface IFirmaDoctoRepository extends JpaRepository<FirmaDocto, Integer>{
	
	
}
