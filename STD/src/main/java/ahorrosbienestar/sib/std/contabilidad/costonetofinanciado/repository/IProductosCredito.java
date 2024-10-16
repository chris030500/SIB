package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ProductosCredito;




@Repository
public interface IProductosCredito extends JpaRepository<ProductosCredito, Integer> {

	@Query(value = "SELECT PK_ID_CREDITO as productocreditoid,VC_DESCRIPCION as descripcionproducto,C_ESTATUS as estatus "
			+ "FROM SIB.TBL_PRODUCTOS_CREDITOS ", nativeQuery = true) 
	public List<ProductosCredito> obtProductos();
	
	@Query(value = "SELECT VC_DESCRIPCION as descripcionproducto "
			+ "FROM SIB.TBL_PRODUCTOS_CREDITOS where PK_ID_CREDITO=:productoID", nativeQuery = true) 
	public String descProducto(@Param("productoID") String productoID);
}
