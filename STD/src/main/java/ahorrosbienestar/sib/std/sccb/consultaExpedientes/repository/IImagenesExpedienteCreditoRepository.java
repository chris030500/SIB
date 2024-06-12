package ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ImagenesExpedienteCredito;


@Repository
public interface IImagenesExpedienteCreditoRepository extends JpaRepository<ImagenesExpedienteCredito, Integer> {
	
	@Query( value = "SELECT T1.I_EXPEDIENTE_ID AS expediente_id, T1.VC_TIPO_REQUISITO  AS tipo_requisito, "
			+ "T1.VC_TIPO_COMPROBANTE AS tipo_comprobante, T1.DT_FECHA_DIGITALIZACION AS fecha_digitalizacion, "
			+ "T1.VC_IMAGEN AS imagen, T1.VC_ESTATUS_ENTREGADO AS estatus_entregado, "
			+ "T1.VC_ESTATUS_DIGITALIZACION AS estatus_digitalizacion, T1.DT_VALIDO_DESDE AS valido_desde, "
			+ "T1.DT_VALIDO_HASTA AS valido_hasta, "
			+ "T3.VC_NOMBRECLIENTE AS nombre_cliente, T3.VC_ROLPARTICIPANTE AS rol_participante "
			+ "FROM TBL_SCCB_IMAGENES_EXP_CREDITO T1 "
			+ "JOIN TBL_SCCB_EXPEDIENTE_CREDITO T2 ON T1.I_EXPEDIENTE_ID = T2.I_EXPEDIENTE_ID "
			+ "JOIN TBL_SCCB_PARTICIPANTES T3 ON T2.I_CREDITO_ID = T3.I_CREDITOID "
			+ "WHERE T1.I_EXPEDIENTE_ID = :idExpediente ", nativeQuery = true)
	public List<Map<String, Object>> getDetalleExpedienteCredito(@Param("idExpediente") Integer idExpediente);
}
