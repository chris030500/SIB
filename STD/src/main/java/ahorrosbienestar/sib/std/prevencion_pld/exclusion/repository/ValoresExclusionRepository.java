package ahorrosbienestar.sib.std.prevencion_pld.exclusion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ValoresExclusionModel;

@Repository
public interface ValoresExclusionRepository extends JpaRepository<ValoresExclusionModel, Integer> {

	@Query(value = "SELECT ex.PK_ID_EXCLUSION, l.VC_LISTA, e.VC_ESTATUS FROM TBL_EXCLUSION_LISTA_ESTATUS_QEQ ex"
			+ " JOIN TBL_CAT_LISTAS_QEQ l ON l.PK_ID_LISTA = ex.FK_LISTA"
			+ " JOIN TBL_CAT_ESTATUS_QEQ e ON e.PK_ID_ESTATUS = ex.FK_ESTATUS"
			+ " ORDER BY ex.PK_ID_EXCLUSION DESC", nativeQuery = true) 
	List<ValoresExclusionModel> listadoExclusion();
	
}
