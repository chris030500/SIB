package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoColocacion;

public interface IProductoColocacionRepository extends JpaRepository<ProductoColocacion, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_PRODUCTOS_CREDITO WHERE C_ESTATUS = :valor", nativeQuery = true)
	List<ProductoColocacion> listaProductos(@Param("valor") String valor);	
	
}
