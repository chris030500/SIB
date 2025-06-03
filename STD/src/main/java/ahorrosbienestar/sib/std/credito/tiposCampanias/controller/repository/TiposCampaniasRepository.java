package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;

 
@Repository

public interface TiposCampaniasRepository extends JpaRepository<TiposCampaniasModel, Integer> {
	 
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) AS VC_TIPO_CAMPANIA"
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) LIKE %:valor%", nativeQuery = true)
	List<Object[]> listaCampanias(@Param("valor") String valor);
	
	@Query(value = "SELECT * FROM SIB.TBL_TIPOS_CAMPANIA WHERE PK_ID_TIPO_CAMPANIA = :pk_id_tipo_campania", nativeQuery = true)
	TiposCampaniasModel mostrarCampania(@Param("pk_id_tipo_campania") Integer pk_id_tipo_campania);
	
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	//public List<TiposCampaniasModel> listaCampanias(@Param("valor") String valor);
 
}
