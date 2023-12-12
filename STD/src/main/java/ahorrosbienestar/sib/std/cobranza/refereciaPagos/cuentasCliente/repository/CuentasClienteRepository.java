package ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;

@Repository
public interface CuentasClienteRepository extends JpaRepository<CuentasClienteModel, Integer> {

	// Query para obtener la lista de clientes por núm de cliente
	@Query(value = "SELECT * FROM SIB.TBL_CUENTAS_CLIENTE_RP" 
				+ " WHERE I_CLIENTE_ID = :cliente", nativeQuery = true)
	public List<CuentasClienteModel> clientePorNumCliente(@Param("cliente") BigInteger cliente);

	// Query para obtener la lista de clientes por nombre de cliente
	@Query(value = "SELECT * FROM SIB.TBL_CUENTAS_CLIENTE_RP"
				+ " WHERE VC_NOMBRE_CLIENTE = :nombre", nativeQuery = true)
	public List<CuentasClienteModel> clientePorNomCliente(@Param("nombre") String nombre);

	// Query para obtener la lista de clientes por núm de crédito
	@Query(value = "SELECT * FROM SIB.TBL_CUENTAS_CLIENTE_RP " 
				+ " WHERE I_CREDITO_ID = :credito", nativeQuery = true)
	public List<CuentasClienteModel> clientePorNumCredito(@Param("credito") BigInteger credito);
	
	// Query para obtener la lista de clientes por ID de cuenta cliente
	@Query(value = "SELECT * FROM SIB.TBL_CUENTAS_CLIENTE_RP " 
				+ " WHERE PK_ID_CUENTA_RP = :cuenta", nativeQuery = true)
	public List<CuentasClienteModel> clientePorIdCtaCliente(@Param("cuenta") int cuenta);

}
