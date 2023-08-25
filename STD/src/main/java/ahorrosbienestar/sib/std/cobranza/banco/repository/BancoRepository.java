package ahorrosbienestar.sib.std.cobranza.banco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.banco.model.BancoModel;

@Repository
public interface BancoRepository extends JpaRepository<BancoModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_BANCO_CNV"
			+ " WHERE I_ESTATUS = :estatus", nativeQuery = true)
	public List<BancoModel> listaBancosPorEstatus(@Param("estatus") int estatus);

}
