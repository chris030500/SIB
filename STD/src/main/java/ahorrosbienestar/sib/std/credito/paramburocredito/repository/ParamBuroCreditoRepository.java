package ahorrosbienestar.sib.std.credito.paramburocredito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCreditoModel;

@Repository
public interface ParamBuroCreditoRepository extends JpaRepository<ParamBuroCreditoModel, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_PARAM_BURO_CREDITO WHERE FK_CAMPANIA_ID = :valorCampaniaID and I_ACTIVO = 1", nativeQuery = true)
	ParamBuroCreditoModel obtParamBuro(@Param("valorCampaniaID") Integer valorCampaniaID);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE SIB.TBL_PARAM_BURO_CREDITO SET I_ACTIVO = 0 WHERE FK_CAMPANIA_ID = :idCamapania", nativeQuery = true)
	void inactivarBC(@Param("idCamapania") Integer idCamapania );
	
}
