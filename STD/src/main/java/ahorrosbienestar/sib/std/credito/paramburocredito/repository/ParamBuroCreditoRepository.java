package ahorrosbienestar.sib.std.credito.paramburocredito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCreditoModel;

@Repository
public interface ParamBuroCreditoRepository extends JpaRepository<ParamBuroCreditoModel, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_PARAM_BURO_CREDITO WHERE FK_CAMPANIA_ID = :valorCampaniaID", nativeQuery = true)
	ParamBuroCreditoModel obtParamBuro(@Param("valorCampaniaID") Integer valorCampaniaID);



	
}
