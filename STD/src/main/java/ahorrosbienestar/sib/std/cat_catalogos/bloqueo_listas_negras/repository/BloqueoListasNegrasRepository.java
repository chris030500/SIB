package ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.model.BloqueoListasNegrasModel;

@Repository
public interface BloqueoListasNegrasRepository extends JpaRepository<BloqueoListasNegrasModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_BLOQUEO_LISTAS_NEGRAS_OA WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<BloqueoListasNegrasModel> listaCatBloqueo();
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_BLOQUEO_LISTAS_NEGRAS_OA WHERE PK_ID_BLOQUEO_LISTAS_NEGRAS = :id", nativeQuery = true)
	public BloqueoListasNegrasModel buscarBloqueoPorId(@Param("id") int id);

}
