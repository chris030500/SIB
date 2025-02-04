package ahorrosbienestar.sib.std.smsconfig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.smsconfig.model.CredencialesCalixta;

@Repository
public interface ICredencialesCalixtaRepository extends JpaRepository<CredencialesCalixta, Integer> {
	
	@Query(value = "SELECT COUNT(*) FROM SIB.TBL_CONFIGURACION_DESEMBOLSADOS_MARKETING "
			+ "WHERE VC_NOMBRE_CONFIG LIKE %:nombreConfig% "
			+ "AND VC_VALOR_CONFIG LIKE %:username%", nativeQuery = true)
	public int searchConfiguracion(String nombreConfig,String username);
	
	@Query(value = "SELECT * FROM SIB.TBL_CREDENCIALES_CALIXTA "
			+ "WHERE I_TIPO_CREDENCIAL = :tipoCredencial "
			+ "OR I_TIPO_CREDENCIAL = 3", nativeQuery = true)
	public List<CredencialesCalixta> getDatosCalixta(Integer tipoCredencial);

}
