package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;

@Repository
public interface SeguimientoGestionOaRepository extends JpaRepository<SeguimientoGestionOaModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_SEGUIMIENTO_GESTION_OA"
			+ " WHERE FK_ID_ASIGNACION_OFERTAS = :asignacion AND I_ESTATUS > 0", nativeQuery = true)
	public List<SeguimientoGestionOaModel> listaGestionPorOferta(@Param("asignacion") int asignacion);

}
