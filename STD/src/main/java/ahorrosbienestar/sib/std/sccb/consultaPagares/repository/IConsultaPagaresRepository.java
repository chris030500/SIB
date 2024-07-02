package ahorrosbienestar.sib.std.sccb.consultaPagares.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaPagares.model.ConsultaPagares;

@Repository
public interface IConsultaPagaresRepository extends JpaRepository<ConsultaPagares, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_PAGARES "
			+ "WHERE I_CREDITO_ID = :idCredito ", nativeQuery = true)
	List<ConsultaPagares> getByIdCreditoConsultaPagares(@Param("idCredito") Integer idCredito);
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_PAGARES "
			+ "WHERE I_NO_CLIENTE = :noCliente ", nativeQuery = true)
	List<ConsultaPagares> getBynoClienteConsultaPagares(@Param("noCliente") Integer noCliente);
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_PAGARES "
			+ "WHERE I_NO_CLIENTE = :noCliente "
			+ "AND I_CREDITO_ID = :idCredito", nativeQuery = true)
	List<ConsultaPagares> getBynoClienteAndIdCreditoConsultaPagares(
			@Param("noCliente") Integer noCliente,
			@Param("idCredito") Integer idCredito);
	
	@Query( value = "SELECT DISTINCT T1.I_NO_CLIENTE AS no_cliente, T2.VC_NOMBRECLIENTE AS nombre_cliente, "
			+ "T2.VC_ROLPARTICIPANTE AS rol, T1.I_CREDITO_ID AS credito_id "
			+ "FROM SIB.TBL_SCCB_PAGARES T1 "
			+ "JOIN SIB.TBL_SCCB_PARTICIPANTES T2 ON T1.I_NO_CLIENTE = T2.I_NOCLIENTE "
			+ "WHERE T2.VC_NOMBRECLIENTE LIKE %:nombre% "
			+ "LIMIT 100 ", nativeQuery = true)
	List<Map<String, Object>> getCustumerNamesPagares(@Param("nombre") String nombre);
}
