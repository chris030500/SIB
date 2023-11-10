package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CONSULTA_RP"
			+ " WHERE FK_ID_CUENTA_RP = :cuenta", nativeQuery = true)
	public ConsultaModel consultaPorCuentaCliente(@Param("cuenta") int cuenta);

}
