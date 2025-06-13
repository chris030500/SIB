package ahorrosbienestar.sib.std.credito.paramburocredito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCredito;

@Repository
public interface ParamBuroCreditoRepository extends JpaRepository<ParamBuroCredito, Integer>{

	
}
