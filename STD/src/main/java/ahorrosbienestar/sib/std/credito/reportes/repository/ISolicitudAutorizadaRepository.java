package ahorrosbienestar.sib.std.credito.reportes.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.reportes.model.SolicitudAutorizada;

@Repository
public interface ISolicitudAutorizadaRepository extends JpaRepository<SolicitudAutorizada, Integer> {
	
	@Query(value = "SELECT T1.ClienteID AS idCliente, "
			+ "CASE WHEN T2.I_ClienteID IS NULL THEN CONCAT(T1.ClienteID, ' - ', T1.SoloNombres) "
			+ "ELSE CONCAT(T1.ClienteID, ' - ', T2.VC_NombreCompleto) "
			+ "END AS clienteInfo "
			+ "FROM SIB.TBL_AUTORIZADAS_PRODUCCION T1 "
			+ "LEFT JOIN SIB.TBL_CLIENTES_ACTIVOS T2 ON T1.ClienteID = T2.I_ClienteID "
			+ "WHERE (CONCAT(T1.ClienteID, ' - ', T2.VC_NombreCompleto) LIKE CONCAT('%', :valor, '%') "
			+ "OR CONCAT(T1.ClienteID, ' - ', T1.SoloNombres) LIKE CONCAT('%', :valor, '%')) "
			+ "AND T1.Estatus = 'A' ", nativeQuery = true)
	List<Map<String, Object>> getClientes(@Param("valor") String valor);
	
	@Query(value = "SELECT TelefonoCelular AS telCelular "
	        + "FROM SIB.TBL_AUTORIZADAS_PRODUCCION "
	        + "WHERE TelefonoCelular LIKE CONCAT('%', :valor, '%') "
	        + "AND Estatus = 'A' ",
	        nativeQuery = true)
	List<Map<String, Object>> getCustomerCellphones(@Param("valor") String valor);
}
