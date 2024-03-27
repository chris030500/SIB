package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.model.ConsultarRelacionesModel;

@Repository
public interface ConsultarRelacionesRepository extends JpaRepository<ConsultarRelacionesModel, Integer> {

	
	@Query(value = "SELECT rc.PK_ID_RELACION_CODIGO, "
			+ " CONCAT(ac.VC_DESCRIPCION, ' (', ac.VC_CODIGO, ') - ', re.VC_DESCRIPCION, ' (', re.VC_CODIGO, ')') AS RELACION_CODIGOS, "
			+ " rc.I_ESTATUS, "
			+ " ob.V_VALOR "
			+ " FROM SIB.TBL_RELACION_CODIGOS_OA rc  "
			+ " JOIN TBL_CODIGOS_ACCION_OA ac ON rc.FK_ID_CODIGO_ACCION = ac.PK_ID_CODIGO_ACCION "
			+ " JOIN TBL_CODIGOS_RESULTADO_OA re ON rc.FK_ID_CODIGO_RESULTADO = re.PK_ID_CODIGO_RESULTADO "
			+ " JOIN TBL_OPC_BUSQUEDA ob ON rc.I_ACCION_REALIZAR = ob.PK_ID "
			+ " WHERE rc.I_ESTATUS = 1 "
			+ " AND CONCAT(ac.VC_DESCRIPCION, ' (', ac.VC_CODIGO, ') - ', re.VC_DESCRIPCION, ' (', re.VC_CODIGO, ')') LIKE CONCAT('', '%')", nativeQuery = true)
	public List<ConsultarRelacionesModel> relacionesActivas(@Param("valor") String valor);
	
	
	@Query(value = "SELECT rc.PK_ID_RELACION_CODIGO, "
			+ " CONCAT(ac.VC_DESCRIPCION, ' (', ac.VC_CODIGO, ') - ', re.VC_DESCRIPCION, ' (', re.VC_CODIGO, ')') AS RELACION_CODIGOS, "
			+ " rc.I_ESTATUS, "
			+ " ob.V_VALOR "
			+ " FROM SIB.TBL_RELACION_CODIGOS_OA rc  "
			+ " JOIN TBL_CODIGOS_ACCION_OA ac ON rc.FK_ID_CODIGO_ACCION = ac.PK_ID_CODIGO_ACCION "
			+ " JOIN TBL_CODIGOS_RESULTADO_OA re ON rc.FK_ID_CODIGO_RESULTADO = re.PK_ID_CODIGO_RESULTADO "
			+ " JOIN TBL_OPC_BUSQUEDA ob ON rc.I_ACCION_REALIZAR = ob.PK_ID "
			+ " WHERE rc.I_ESTATUS = 1 "
			+ " AND CONCAT(ac.VC_DESCRIPCION, ' (', ac.VC_CODIGO, ') - ', re.VC_DESCRIPCION, ' (', re.VC_CODIGO, ')') LIKE CONCAT('', '%')", nativeQuery = true)
	public ConsultarRelacionesModel nombreRelacion(@Param("valor") String valor);
}
