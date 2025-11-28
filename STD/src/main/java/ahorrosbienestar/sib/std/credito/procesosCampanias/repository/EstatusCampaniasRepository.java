package ahorrosbienestar.sib.std.credito.procesosCampanias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;

@Repository

public interface EstatusCampaniasRepository extends JpaRepository<EstatusCampaniasModel, Integer> {

	@Query(value =
	        "select t1.I_ID_ESTATUS_CAMPANIA "
	        + "        ,t3.VC_ESTATUS "
	        + "FROM SIB.TBL_PROCESO_CAMPANIA t1 "
	        + "inner join SIB.TBL_ESTATUS_CAMPANIA t3 "
	        + "on t1.I_ID_ESTATUS_CAMPANIA = t3.PK_ID_ESTATUS_CAMPANIA "
	        + "WHERE t1.PK_ID_PROCESO_CAMPANIA = ( SELECT MAX(PK_ID_PROCESO_CAMPANIA) "
	        + "                                   FROM SIB.TBL_PROCESO_CAMPANIA "
	        + "                                   WHERE I_ID_TIPO_CAMPANIA = :tipoCampania "
	        + "                                    ) "
	        + "and t1.I_ESTATUS_REG = 1 "
	        + "and  t1.I_ID_ESTATUS_CAMPANIA not in(5,6)", 
	       nativeQuery = true)
	List<Object[]> listaEstatus(@Param("tipoCampania") Integer tipoCampania);

}
