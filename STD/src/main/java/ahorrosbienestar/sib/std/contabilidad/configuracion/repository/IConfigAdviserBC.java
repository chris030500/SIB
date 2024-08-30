package ahorrosbienestar.sib.std.contabilidad.configuracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ConfigAdviserBC;

@Repository
public interface IConfigAdviserBC extends JpaRepository<ConfigAdviserBC, Integer>{

	

}
