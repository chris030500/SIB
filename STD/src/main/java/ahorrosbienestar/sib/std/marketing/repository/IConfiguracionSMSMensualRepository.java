package ahorrosbienestar.sib.std.marketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.marketing.model.ConfiguracionSMSMensual;

@Repository
public interface IConfiguracionSMSMensualRepository extends JpaRepository<ConfiguracionSMSMensual, Integer> {

}
