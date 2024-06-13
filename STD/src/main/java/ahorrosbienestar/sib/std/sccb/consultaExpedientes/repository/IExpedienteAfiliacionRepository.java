package ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteAfiliacion;

@Repository
public interface IExpedienteAfiliacionRepository extends JpaRepository<ExpedienteAfiliacion, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION "
			+ "WHERE VC_NOMBRE_CLIENTE = :nomCliente ", nativeQuery = true)
	public Optional<ExpedienteAfiliacion> getExpedienteAfiliacionByName(@Param("nomCliente") String nomCliente);
	
	@Query( value = "SELECT T1.VC_NOMBRE_CLIENTE AS nombre_cliente "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_AFILIACION T1 "
			+ "WHERE T1.VC_NOMBRE_CLIENTE LIKE %:nombre% "
			+ "LIMIT 100", nativeQuery = true )
	public List<Map<String, Object>> getByNameExpedienteAfiliacion(@Param("nombre") String nombre);
}
