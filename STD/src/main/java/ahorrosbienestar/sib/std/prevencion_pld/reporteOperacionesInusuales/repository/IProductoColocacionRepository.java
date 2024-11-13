package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.model.ProductoColocacion;

public interface IProductoColocacionRepository extends JpaRepository<ProductoColocacion, Integer> {

}
