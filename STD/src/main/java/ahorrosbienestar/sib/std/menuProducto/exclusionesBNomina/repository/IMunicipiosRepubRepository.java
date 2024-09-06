package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.MunicipiosRepub;


@Repository
public interface IMunicipiosRepubRepository extends JpaRepository<MunicipiosRepub, Integer> {
	
	@Query( value = "SELECT T1.PK_I_MUNICIPIO_ID AS id_municipio, T1.VC_NOMBRE AS nombre_municipio "
			+ "FROM SIB.TBL_MUNICIPIOS_REPUB T1 "
			+ "WHERE T1.PK_I_ESTADO_ID = :idEstado "
			+ "AND T1.I_INDICADOR_DEPURAR = 0", nativeQuery = true)
	List<Map<String, Object>> getMunicipiosRepub(@Param("idEstado") Integer idEstado);
	
	@Query( value = "SELECT T2.VC_NOMBRE AS nombre_estado, "
			+ "T1.VC_NOMBRE AS nombre_municipio, T1.I_INDICADOR_DEPURAR AS indicador_depurar, "
			+ "T1.I_USUARIO_MODIFICO AS usuario_modifico, T1.DT_FECHA_MODIFICO AS fecha_modifico, "
			+ "T1.I_INDICADOR_DEPURAR_ANTERIOR AS indicador_anterior "
			+ "FROM SIB.TBL_MUNICIPIOS_REPUB T1 "
			+ "INNER JOIN SIB.TBL_ESTADOS_REPUB T2 ON T1.PK_I_ESTADO_ID = T2.PK_I_ESTADO_ID "
			+ "WHERE T1.I_INDICADOR_DEPURAR = 1 "
			+ "ORDER BY T2.VC_NOMBRE, T1.VC_NOMBRE ", nativeQuery = true)
	List<Map<String, Object>> getMunicipiosConCobertura();
	
    @Query(value = "SELECT * FROM SIB.TBL_MUNICIPIOS_REPUB WHERE PK_I_ESTADO_ID = :idEstado AND PK_I_MUNICIPIO_ID = :idMunicipio", nativeQuery = true)
    Optional<MunicipiosRepub> findByIdEstadoAndIdMunicipio(
            @Param("idEstado") Integer idEstado,
            @Param("idMunicipio") Integer idMunicipio);
}
