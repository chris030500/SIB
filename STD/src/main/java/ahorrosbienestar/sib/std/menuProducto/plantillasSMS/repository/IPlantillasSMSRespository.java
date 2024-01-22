package ahorrosbienestar.sib.std.menuProducto.plantillasSMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.plantillasSMS.model.PlantillasSMS;

@Repository
public interface IPlantillasSMSRespository extends JpaRepository<PlantillasSMS, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_PLANTILLAS_SMS ORDER BY PK_ID_PLANTILLA ASC", nativeQuery = true)
	public List<PlantillasSMS> obtPlantillas();

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_PLANTILLAS_SMS WHERE FK_ID_BASE =:slcCampania and FK_ID_CLASIFICACION =:slcClasificacion ", nativeQuery = true)
	public int existeRegistro(@Param("slcCampania") Integer slcCampania, @Param("slcClasificacion") Integer slcClasificacion);

}
