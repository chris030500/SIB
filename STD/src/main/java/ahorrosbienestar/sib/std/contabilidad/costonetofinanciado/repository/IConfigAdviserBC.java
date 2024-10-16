package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ConfigAdviserBC;



@Repository
public interface IConfigAdviserBC extends JpaRepository<ConfigAdviserBC, Integer>{

	@Query(value = "select COUNT(*) as existe from SIB.TBL_CAT_CONF_CB_ADVISER WHERE I_PRODUCTOS =:idproducto AND I_CONCEPTO=:concepto "
			+ "AND (D_MONTO_INICIAL =:montoInicial "
			+ "AND D_MONTO_FINAL=:montoFinal) AND I_ESTATUS =1 ", nativeQuery = true)
	public int existeConfiguracion(double montoInicial, double montoFinal, String idproducto, Integer concepto);

	

}
