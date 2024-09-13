package ahorrosbienestar.sib.std.contabilidad.configuracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ProductosCreditoConf;



@Repository
public interface IProductosCreditoConf extends JpaRepository<ProductosCreditoConf,Integer>{


}
