package ahorrosbienestar.sib.std.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.roles.model.Rol;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
	

	@Query(value = "SELECT r.PK_ID_PRODUCTO, r.VC_NOMBRE_PRODUCTO FROM TBL_CAT_PRODUCTO as r WHERE r.PK_ID_PRODUCTO = ?1", nativeQuery = true)
	public Producto findByIdProducto(Integer producto);
	
	//@Query(value = "SELECT ")
	public Producto findAll(int producto);

}
