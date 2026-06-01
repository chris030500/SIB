package ahorrosbienestar.sib.std.sccb.expedientes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.expedientes.model.ExpedienteAfiliacion;

@Repository
public interface IExpedienteAfiliacionRepository extends JpaRepository<ExpedienteAfiliacion, Integer> {
	
	@Query( value = "SELECT DISTINCT T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_EXPEDIENTE_ID AS expediente_id, "
			+ "T1.VC_TIPO_EXPEDIENTE AS tipo_expediente, T1.DT_FECHA_CREACION AS fecha_creacion, "
			+ "T1.VC_DESC_ESTATUS AS desc_estatus, T1.VC_DESC_SITUACION AS desc_situacion "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION T1 "
			+ "WHERE T1.VC_NOMBRE_CLIENTE = :nombre ", nativeQuery = true)
	public Optional<Map<String, Object>> getExpedienteAfiliacionByName(@Param("nombre")String nombre);
	
	@Query(value = "SELECT T1.VC_NOMBRE_CLIENTE AS nombre_cliente "
	        + "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION T1 "
	        + "WHERE T1.VC_NOMBRE_CLIENTE LIKE CONCAT('%', :nombre, '%') "
	        + "LIMIT 100",
	        nativeQuery = true)
	public List<Map<String, Object>> getByNameExpedienteAfiliacion(
	        @Param("nombre") String nombre);
}
