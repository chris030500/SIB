package ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.model.ConfiguracionSistema;


@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Integer> {

	@Query(value = "SELECT COUNT(*) FROM SIB.TBL_CONFIGURACION_PARAMETROS_PRODUCTOS WHERE VC_NOMBRE_CAMPO LIKE %:campoParametro% and VC_CONFIGURACION LIKE %:usuario%", nativeQuery = true)
	public int buscarConfiguracion(String campoParametro,String usuario);

}
