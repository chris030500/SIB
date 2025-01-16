package ahorrosbienestar.sib.std.marketing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.marketing.model.ConfiguracionSMS;

@Repository
public interface IConfiguracionSMSRepository extends JpaRepository<ConfiguracionSMS, Integer> {
	
	@Query( value = "SELECT * FROM SIB.TBL_CONFIG_SMS "
			+ "WHERE FK_VC_TP_CREDITO = :tipoCredito "
			+ "AND I_IND_ACTIVO = 1 "
			+ "AND FK_VC_ESTATUS_SOLCRE = 'D' "
			+ "AND I_IND_SOLCRE_CAMPANIA = :isCampania ", nativeQuery = true)
	public Optional<ConfiguracionSMS> getCurrentSmsConfig(String tipoCredito, Integer isCampania);

}
