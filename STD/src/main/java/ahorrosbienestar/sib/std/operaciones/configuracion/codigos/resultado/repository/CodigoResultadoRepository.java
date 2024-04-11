package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.model.CodigoResultadoModel;

@Repository
public interface CodigoResultadoRepository extends JpaRepository<CodigoResultadoModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CODIGOS_RESULTADO_OA WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<CodigoResultadoModel> listaCodigosResultado();
	
	@Query(value = "SELECT * FROM SIB.TBL_CODIGOS_RESULTADO_OA WHERE PK_ID_CODIGO_RESULTADO = :idCodigo", nativeQuery = true)
	public CodigoResultadoModel buscarCodigoResultado(@Param("idCodigo") int idCodigo);

}
