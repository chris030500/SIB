package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ConfiguracionCorreoRepository extends JpaRepository<ConfiguracionCorreo, Long> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CONFIGURACION_CORREO_VOBO WHERE PK_ID_CONF = 1", nativeQuery = true)
	ConfiguracionCorreo findFirstByOrderByPkIdConfAsc();
	
}
