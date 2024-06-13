package ahorrosbienestar.sib.std.sccb.consultaPagares.repository;

import java.util.List;

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
	
}
