package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoColocacion;

public interface IProductoColocacionRepository extends JpaRepository<ProductoColocacion, Integer> {

}
