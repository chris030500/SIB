package ahorrosbienestar.sib.std.productodocumento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.productodocumento.model.RelacionDocumentos;

@Repository
public interface IProductoDocumentoRepository extends JpaRepository<RelacionDocumentos, Integer> {
	
	@Query(value = "SELECT COUNT(*) AS NUMERO FROM TBL_CAT_PROD_CAT_DOCT WHERE FK_PRODUCTO =:id_producto and FK_DOCUMENTO=:id_documento", nativeQuery = true)
	public int existeDocumento(@Param("id_producto")int id_producto,@Param("id_documento")int id_documento);

	@Query(value = "SELECT * FROM TBL_CAT_PROD_CAT_DOCT WHERE FK_PRODUCTO =:id_producto and I_ESTATUS = 1", nativeQuery = true)
	public List<RelacionDocumentos> buscarDocumentos(@Param("id_producto")int id_producto);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_PROD_CAT_DOCT SET I_ESTATUS = 2, DT_FECHA_BAJA = now() WHERE FK_PRODUCTO =:id_producto and FK_DOCUMENTO=:id_documento", nativeQuery = true)
	public int inactivarRelacion(@Param("id_producto")int id_producto,@Param("id_documento")int id_documento);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_PROD_CAT_DOCT SET I_ESTATUS = 1, DT_FECHA_BAJA = null WHERE FK_PRODUCTO =:id_producto and FK_DOCUMENTO=:id_documento", nativeQuery = true)
	public int activarDocumento(@Param("id_producto") int idproducto,@Param("id_documento") int idDocumento);

	/*
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_PROD_CAT_DOCT SET I_ESTATUS = 2, DT_FECHA_BAJA = now() WHERE FK_PRODUCTO = :idproducto and FK_DOCUMENTO = idDocumento", nativeQuery = true)
	public int bajaDocumento(@Param("idproducto") int idproducto,@Param("idDocumento") int idDocumento);
	*/
	
}
