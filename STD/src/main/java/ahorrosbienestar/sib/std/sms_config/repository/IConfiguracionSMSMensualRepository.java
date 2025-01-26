package ahorrosbienestar.sib.std.sms_config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sms_config.model.ConfiguracionSMSMensual;

@Repository
public interface IConfiguracionSMSMensualRepository extends JpaRepository<ConfiguracionSMSMensual, Integer> {

}
