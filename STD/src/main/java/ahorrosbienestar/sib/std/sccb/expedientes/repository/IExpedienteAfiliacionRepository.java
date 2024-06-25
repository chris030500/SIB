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
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION "
			+ "WHERE PK_I_ROW_GUID = :rowGuid ", nativeQuery = true)
	public Optional<ExpedienteAfiliacion> getExpedienteAfiliacionByName(@Param("rowGuid") Integer rowGuid);
	
	@Query( value = "SELECT T1.VC_NOMBRE_CLIENTE AS nombre_cliente, "
			+ "T1.PK_I_ROW_GUID AS row_guid "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION T1 "
			+ "WHERE T1.VC_NOMBRE_CLIENTE LIKE %:nombre% "
			+ "LIMIT 100", nativeQuery = true )
	public List<Map<String, Object>> getByNameExpedienteAfiliacion(@Param("nombre") String nombre);
}
