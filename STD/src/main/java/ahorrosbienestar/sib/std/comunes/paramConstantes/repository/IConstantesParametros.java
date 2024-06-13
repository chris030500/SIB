package ahorrosbienestar.sib.std.comunes.paramConstantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.comunes.paramConstantes.model.ConstantesParametros;


@Repository
public interface IConstantesParametros extends JpaRepository<ConstantesParametros, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_PARAMS_CONSTANTES WHERE VC_CLAVE=:clave", nativeQuery = true)
	ConstantesParametros findByClave(@Param("clave") String clave);

}
