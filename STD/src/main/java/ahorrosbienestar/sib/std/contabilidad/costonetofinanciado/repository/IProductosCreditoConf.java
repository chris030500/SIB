package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.model.ProductosCreditoConf;

@Repository
public interface IProductosCreditoConf extends JpaRepository<ProductosCreditoConf,Integer>{


}
