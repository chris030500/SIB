package ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.model.ConfiguracionSistema;


@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Integer> {

}
