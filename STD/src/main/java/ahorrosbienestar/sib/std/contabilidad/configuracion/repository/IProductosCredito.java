package ahorrosbienestar.sib.std.contabilidad.configuracion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ProductosCredito;


@Repository
public interface IProductosCredito extends JpaRepository<ProductosCredito, Integer> {

	@Query(value = "SELECT I_PRODUCTO_CREDITOID,VC_DESCRIPCION_PRODUCTO,C_ESTATUS "
			+ "FROM SIB.TBL_PRODUCTOS_CREDITO ", nativeQuery = true) 
	public List<ProductosCredito> obtProductos();
}
