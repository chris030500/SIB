package ahorrosbienestar.sib.std.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.producto_contrato.model.ProductoContrato;

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
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_PRODUCTO WHERE I_NO_PRODUCTO = :numero", nativeQuery = true)
    public Producto productoPorNumero(@Param("numero") Integer numero);
	
	@Query(value ="SELECT * FROM SIB.TBL_CAT_PRODUCTO WHERE I_NO_PRODUCTO = :producto", nativeQuery = true)
	public Producto findByNoProducto(Integer producto);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_PRODUCTO SET VC_IDENTIFICADOR_KYC = :kyc , VC_DESCRIPCION_PRODUCTO = :descripcion WHERE PK_ID_PRODUCTO = :idProducto", nativeQuery = true)
	public int cambiarKycYDescripcion(@Param ("kyc") String kyc, @Param("descripcion") String descripcion, @Param("idProducto") Integer idProducto);
	
	@Query(value = "SELECT * FROM TBL_CAT_PRODUCTO as p WHERE p.I_NO_PRODUCTO = :producto", nativeQuery = true)
	public Producto findByProducto(Integer producto);
	
	@Query(value = "SELECT PRODUCTO.PK_ID_PRODUCTO, PRODUCTO.DT_FECHA_CANCELACION, PRODUCTO.DT_FECHA_OPERACION, PRODUCTO.I_CLASIFICACION, "
			+ " PRODUCTO.I_ESTATUS, PRODUCTO.I_NO_PRODUCTO, PRODUCTO.I_TIPO_PRODUCTO, PRODUCTO.I_USUARIOID_REGISTRA, "
			+ " PRODUCTO.VC_NOMBRE_PRODUCTO, CONTRATO.VC_CONTRATO, PRODUCTO.VC_IDENTIFICADOR_KYC, PRODUCTO.VC_DESCRIPCION_PRODUCTO "
			+ " FROM SIB.TBL_CAT_PRODUCTO PRODUCTO "
			+ " LEFT JOIN SIB.TBL_CAT_CONTRATO CONTRATO ON PRODUCTO.FK_ID_CONTRATO = CONTRATO.PK_ID_CONTRATO "
			+ " GROUP BY PRODUCTO.PK_ID_PRODUCTO "
			+ " ORDER BY PRODUCTO.PK_ID_PRODUCTO DESC", nativeQuery = true)
	public List<Producto> listaDeProductosConContrato2();
}
