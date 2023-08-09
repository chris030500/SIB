package ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;

@Repository
public interface IClasificacionBasesRepository extends JpaRepository<ClasificacionBases, Integer>{
	
	//Consulta que ayuda a validar si existe el nombre 
	@Query(value = "SELECT * FROM SIB.TBL_CLASIFICACION_BASES_OA WHERE FK_ID_BASE = :base \r\n"
			+ "AND (VC_NOMBRE_CLASIFICACION = :nomClasificacion OR VC_CODIGO_CLASIFICACION = :codClasificacion)", nativeQuery = true)
	public Optional<ClasificacionBases> validarExistClasiBase(@Param("base") Integer base, @Param("nomClasificacion") String nomClasificacion, @Param("codClasificacion") String codClasificacion);
}
