package ahorrosbienestar.sib.std.smsconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;

@Repository
public interface IConfiguracionSMSMensualRepository extends JpaRepository<ConfiguracionSMSMensual, Long> {

}
