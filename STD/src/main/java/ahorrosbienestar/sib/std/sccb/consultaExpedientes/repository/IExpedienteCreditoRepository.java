package ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteCredito;

@Repository
public interface IExpedienteCreditoRepository extends JpaRepository<ExpedienteCredito, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_CREDITO "
			+ "WHERE I_CREDITO_ID = :idCredito ", nativeQuery = true)
	public Optional<ExpedienteCredito> getExpedienteCreditoByIdCredito(@Param("idCredito") Integer idCredito);
	
	@Query( value = "SELECT T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_CREDITO_ID AS credito_id "
			+ "FROM SIB.TBL_SCCB_EXPEDIENTE_CREDITO T1 "
			+ "WHERE T1.VC_NOMBRE_CLIENTE LIKE %:nombre% "
			+ "LIMIT 100", nativeQuery = true )
	public List<Map<String, Object>> getNamesExpedienteCredito(@Param("nombre") String nombre);
}
