package ahorrosbienestar.sib.std.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.productos.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
	

	@Query(value = "SELECT r.PK_ID_PRODUCTO, r.VC_NOMBRE_PRODUCTO FROM TBL_CAT_PRODUCTO as r WHERE r.PK_ID_PRODUCTO = ?", nativeQuery = true)
	public Producto findByIdProducto(Integer producto);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_PRODUCTO SET I_ESTATUS = 2, DT_FECHA_CANCELACION = now() WHERE I_NO_PRODUCTO = :id_producto", nativeQuery = true)
	public int bajaProducto( @Param("id_producto") Integer id_producto);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_PRODUCTO SET I_ESTATUS = 1, DT_FECHA_CANCELACION = null WHERE I_NO_PRODUCTO = :id_producto", nativeQuery = true)
	public int reactivar(@Param("id_producto") Integer id_producto);
	
	@Query(value = "SELECT COUNT(*) AS NUMERO FROM TBL_CAT_PRODUCTO WHERE I_NO_PRODUCTO = :id_producto", nativeQuery = true)
	public int existeProducto(@Param("id_producto")int id_producto);

	@Query(value = "SELECT * FROM TBL_CAT_PRODUCTO order by PK_ID_PRODUCTO desc ", nativeQuery = true)
	public List<Producto> todosProductos();

	
	@Query(value ="SELECT * FROM SIB.TBL_CAT_PRODUCTO WHERE I_NO_PRODUCTO = :producto", nativeQuery = true)
	public Producto findByNoProducto(Integer producto);
}
