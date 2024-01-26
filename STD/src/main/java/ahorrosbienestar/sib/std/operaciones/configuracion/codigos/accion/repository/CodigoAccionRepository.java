package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.model.CodigoAccionModel;

@Repository
public interface CodigoAccionRepository extends JpaRepository<CodigoAccionModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CODIGOS_ACCION_OA WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<CodigoAccionModel> listaCodigosAccion();
	
	@Query(value = "SELECT * FROM SIB.TBL_CODIGOS_ACCION_OA WHERE PK_ID_CODIGO_ACCION = :idCodigo", nativeQuery = true)
	public CodigoAccionModel buscarCodigoAccion(@Param("idCodigo") int idCodigo);

}
