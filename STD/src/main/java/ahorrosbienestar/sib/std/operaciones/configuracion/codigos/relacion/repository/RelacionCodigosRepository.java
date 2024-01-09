package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;

@Repository
public interface RelacionCodigosRepository extends JpaRepository<RelacionCodigosModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_RELACION_CODIGOS_OA WHERE PK_ID_RELACION_CODIGO = :id", nativeQuery = true)
	public RelacionCodigosModel buscarRelacionPorId(@Param("id") int id);

}
