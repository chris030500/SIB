package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.MunicipiosRepub;


@Repository
public interface IMunicipiosRepubRepository extends JpaRepository<MunicipiosRepub, Integer> {
	
	@Query( value = "SELECT T1.PK_I_MUNICIPIO_ID AS id_municipio, T1.VC_NOMBRE AS nombre_municipio "
			+ "FROM SIB.TBL_MUNICIPIOS_REPUB T1 "
			+ "WHERE T1.PK_I_ESTADO_ID = :idEstado", nativeQuery = true)
	List<Map<String, Object>> getMunicipiosRepub(@Param("idEstado") Integer idEstado);
}
