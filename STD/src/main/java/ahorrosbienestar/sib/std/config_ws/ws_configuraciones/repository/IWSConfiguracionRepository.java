package ahorrosbienestar.sib.std.config_ws.ws_configuraciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.config_ws.ws_configuraciones.model.WSConfiguracion;

@Repository
public interface IWSConfiguracionRepository extends JpaRepository<WSConfiguracion, Integer> {

	@Query(value = "SELECT * FROM TBL_WSCAMPANIAS_CONFIGURACIONES WHERE PK_ID = :pkId AND I_ESTATUS = :iEstatus", nativeQuery = true)
	Optional<WSConfiguracion> findConfiguracionActivaPorId(@Param("pkId") Integer pkId,
			@Param("iEstatus") Integer iEstatus);

	@Query(value = "SELECT * FROM TBL_WSCAMPANIAS_CONFIGURACIONES WHERE VC_DESCRIPCION = :vcDescripcion AND I_ESTATUS = :iEstatus", nativeQuery = true)
	Optional<WSConfiguracion> findConfiguracionActivaPorDescripcion(@Param("vcDescripcion") String vcDescripcion,
			@Param("iEstatus") Integer iEstatus);
}
