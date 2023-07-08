package ahorrosbienestar.sib.std.prevencion_pld.estatus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;

@Repository
public interface EstatusPldRepository extends JpaRepository<EstatusPldModel, Integer> {
	
	@Query(value = "SELECT e.* FROM TBL_CAT_ESTATUS_QEQ e"
			+ " JOIN TBL_RELACION_LISTA_ESTATUS_QEQ r ON r.FK_ESTATUS = e.PK_ID_ESTATUS"
			+ " JOIN TBL_CAT_LISTAS_QEQ l ON l.PK_ID_LISTA = r.FK_LISTA"
			+ " WHERE l.PK_ID_LISTA = :idLista"
			+ " AND e.I_ESTATUS = 1", nativeQuery = true)
	public List<EstatusPldModel> relacionEstatus(@Param("idLista") Integer idLista);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_ESTATUS_QEQ"
			+ " WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<EstatusPldModel> estatusActivos();

}
