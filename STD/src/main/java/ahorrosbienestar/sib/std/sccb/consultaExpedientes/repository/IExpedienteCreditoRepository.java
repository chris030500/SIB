package ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteCredito;

@Repository
public interface IExpedienteCreditoRepository extends JpaRepository<ExpedienteCredito, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM TBL_SCCB_EXPEDIENTE_CREDITO "
			+ "WHERE I_CREDITO_ID = :idCredito ", nativeQuery = true)
	public Optional<ExpedienteCredito> getExpedienteCreditoByIdCredito(@Param("idCredito") Integer idCredito);
}
