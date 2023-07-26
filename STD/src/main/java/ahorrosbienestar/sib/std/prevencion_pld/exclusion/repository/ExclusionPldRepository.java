package ahorrosbienestar.sib.std.prevencion_pld.exclusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ExclusionPldModel;

@Repository
public interface ExclusionPldRepository extends JpaRepository<ExclusionPldModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_EXCLUSION_LISTA_ESTATUS_QEQ"
			+ " WHERE FK_LISTA = :idLista"
			+ " AND FK_ESTATUS = :idEstatus", nativeQuery = true)
	public ExclusionPldModel exclusionRegistrada(@Param("idLista") Integer idLista,
												 @Param("idEstatus") Integer idEstatus);

}
