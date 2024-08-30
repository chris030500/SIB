package ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;

@Repository
public interface IClasificacionBasesRepository extends JpaRepository<ClasificacionBases, Integer>{
	
	//Consulta que ayuda a validar si existe el nombre 
	@Query(value = "SELECT * FROM SIB.TBL_CLASIFICACION_BASES_OA WHERE FK_ID_BASE = :base \r\n"
			+ "AND (VC_NOMBRE_CLASIFICACION = :nomClasificacion OR VC_CODIGO_CLASIFICACION = :codClasificacion)", nativeQuery = true)
	public Optional<ClasificacionBases> validarExistClasiBase(@Param("base") Integer base, @Param("nomClasificacion") String nomClasificacion, @Param("codClasificacion") String codClasificacion);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CLASIFICACION_BASES_OA SET I_ESTATUS_CLASIFICACION = :estatus WHERE PK_ID_CLASIFICACION = :idClasificacion", nativeQuery = true)
	public int cambiarEstatusClasificacion(@Param("idClasificacion") Integer idClasificacion, @Param("estatus") Integer estatus);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_CLASIFICACION_BASES_OA "
			+ "WHERE FK_ID_BASE = :idBase and "
			+ "VC_NOMBRE_CLASIFICACION= :nomClasificacion and "
			+ "VC_ABREVIACION_CLASIFICACION= :abrevacion", nativeQuery = true)
	public int existeClasificacion(@Param("idBase") Integer idBase,@Param("nomClasificacion") String nomClasificacion,@Param("abrevacion") String abrevacion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CLASIFICACION_BASES_OA SET FK_ID_BASE= :idBase, "
			+ "VC_NOMBRE_CLASIFICACION= :nomClasificacion, "
			+ "VC_ABREVIACION_CLASIFICACION= :abrevacion, "
			+ "VC_CODIGO_CLASIFICACION= :codigoClasificacion,"
			+ "I_VIGENCIA_CLASIFICACION= :vigenciaClasificacion, "
			+ "VC_MENSAJE_CLASIFICACION= :mensajeClasificacion "
			+ "WHERE PK_ID_CLASIFICACION = :idClass", nativeQuery = true)
	public int updateClasificacion(@Param("idClass") Integer idClass, @Param("idBase") Integer idBase,@Param("nomClasificacion")  String nomClasificacion, @Param("abrevacion") String abrevacion,
			@Param("codigoClasificacion") String codigoClasificacion,@Param("vigenciaClasificacion") Integer vigenciaClasificacion, @Param("mensajeClasificacion") String mensajeClasificacion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CLASIFICACION_BASES_OA SET "
			+ "I_ESTATUS_CLASIFICACION = 1 "
			+ "WHERE PK_ID_CLASIFICACION = :idClass", nativeQuery = true)
	public int activarClasificacion(@Param("idClass") Integer idClass);

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CLASIFICACION_BASES_OA SET "
			+ "I_ESTATUS_CLASIFICACION = 0 "
			+ "WHERE PK_ID_CLASIFICACION = :idClass", nativeQuery = true)
	public int bajaClasificacion(Integer idClass);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_CLASIFICACION_BASES_OA "
			+ "WHERE VC_CODIGO_CLASIFICACION= :codigoClasificacion", nativeQuery = true)
	public int existeCodigo(@Param("codigoClasificacion") String codigoClasificacion);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_CLASIFICACION_BASES_OA "
			+ "WHERE VC_CODIGO_CLASIFICACION= :codigoClasificacion and PK_ID_CLASIFICACION <> :idClass", nativeQuery = true)
	public int existeCodigoMod(String codigoClasificacion, Integer idClass);

	@Query(value = "SELECT * FROM SIB.TBL_CLASIFICACION_BASES_OA WHERE FK_ID_BASE = :idBase", nativeQuery = true)
	public List<ClasificacionBases> clasificacionXBase(@Param("idBase") Integer idBase);

	
}
