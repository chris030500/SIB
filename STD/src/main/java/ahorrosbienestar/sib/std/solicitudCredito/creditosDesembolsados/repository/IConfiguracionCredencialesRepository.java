package ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.model.ConfiguracionCredenciales;

@Repository
public interface IConfiguracionCredencialesRepository extends JpaRepository<ConfiguracionCredenciales, Integer> {
	
	@Query(value = "SELECT COUNT(*) FROM SIB.TBL_CONFIGURACION_SOLICITUD_CREDITO "
			+ "WHERE VC_NOMBRE_CONFIG LIKE %:nombreConfig% "
			+ "AND VC_VALOR_CONFIG LIKE %:username%", nativeQuery = true)
	public int searchConfiguracion(String nombreConfig,String username);

}
