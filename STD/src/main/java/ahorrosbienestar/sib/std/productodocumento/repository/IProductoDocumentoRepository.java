package ahorrosbienestar.sib.std.productodocumento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.productodocumento.model.RelacionDocumentos;

@Repository
public interface IProductoDocumentoRepository extends JpaRepository<RelacionDocumentos, Integer> {
	
	@Query(value = "SELECT COUNT(*) AS NUMERO FROM TBL_CAT_PROD_CAT_DOCT WHERE FK_PRODUCTO =:id_producto and FK_DOCUMENTO=:id_documento", nativeQuery = true)
	public int existeDocumento(@Param("id_producto")int id_producto,@Param("id_documento")int id_documento);

	@Query(value = "SELECT * FROM TBL_CAT_PROD_CAT_DOCT WHERE FK_PRODUCTO =:id_producto and I_ESTATUS = 1", nativeQuery = true)
	public List<RelacionDocumentos> buscarDocumentos(@Param("id_producto")int id_producto);
	
	
}
