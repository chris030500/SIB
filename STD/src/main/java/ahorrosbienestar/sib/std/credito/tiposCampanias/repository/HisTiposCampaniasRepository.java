package ahorrosbienestar.sib.std.credito.tiposCampanias.repository;

//import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.HisTiposCampaniasModel;
import jakarta.transaction.Transactional;

@Repository

public interface HisTiposCampaniasRepository extends JpaRepository<HisTiposCampaniasModel, Integer> {
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO SIB.TBL_HIS_TIPOS_CAMPANIA ("
	        + "VC_JSON_ENTRADA, VC_TABLA, I_USER_REG"
	        + ") VALUES ("
	        + ":vc_json_entrada, :vc_tabla, :i_user_reg"
	        + ")", nativeQuery = true)
	int insertarHisCampania(
			@Param("vc_json_entrada") String vc_json_entrada,
			@Param("vc_tabla") String vc_tabla,
			@Param("i_user_reg") Integer i_user_reg
	);

}
