package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoCaptacion;

public interface IProductoCaptacionRepository extends JpaRepository<ProductoCaptacion, Integer> {

}
