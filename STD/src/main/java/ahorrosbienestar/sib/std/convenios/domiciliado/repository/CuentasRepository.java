package ahorrosbienestar.sib.std.convenios.domiciliado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.convenios.domiciliado.model.CuentasModel;

@Repository
public interface CuentasRepository extends JpaRepository<CuentasModel, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_CUENTASTRANS_MF"
			+ " WHERE Clabe = :clabe",  nativeQuery = true)
	public CuentasModel datosCuentaClabe(@Param("clabe") String clabe);
	
}
