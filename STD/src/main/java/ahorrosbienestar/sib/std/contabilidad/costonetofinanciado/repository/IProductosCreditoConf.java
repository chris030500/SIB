package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ProductosCreditoConf;

@Repository
public interface IProductosCreditoConf extends JpaRepository<ProductosCreditoConf,Integer>{


}
