package ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.model.AccionRealizarModel;

@Repository
public interface AccionRealizarRepository extends JpaRepository<AccionRealizarModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_ACCION_REALIZAR_OA WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<AccionRealizarModel> listaCatAccionRealizar();

	@Query(value = "SELECT * FROM SIB.TBL_CAT_ACCION_REALIZAR_OA WHERE PK_ID_ACCION_REALIZAR = :id", nativeQuery = true)
	public AccionRealizarModel buscarAccionRealizarId(@Param("id") int id);
}
