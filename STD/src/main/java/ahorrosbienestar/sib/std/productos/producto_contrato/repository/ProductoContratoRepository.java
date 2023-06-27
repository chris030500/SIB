package ahorrosbienestar.sib.std.productos.producto_contrato.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.productos.producto_contrato.model.ProductoContrato;

@Repository
public interface ProductoContratoRepository extends JpaRepository<ProductoContrato, Integer> {
	
	@Query(value = "SELECT PRODUCTO.PK_ID_PRODUCTO, PRODUCTO.DT_FECHA_CANCELACION, PRODUCTO.DT_FECHA_OPERACION, PRODUCTO.I_CLASIFICACION, "
			+ " PRODUCTO.I_ESTATUS, PRODUCTO.I_NO_PRODUCTO, PRODUCTO.I_TIPO_PRODUCTO, PRODUCTO.I_USUARIOID_REGISTRA, "
			+ " PRODUCTO.VC_NOMBRE_PRODUCTO, CONTRATO.VC_CONTRATO, PRODUCTO.VC_IDENTIFICADOR_KYC, PRODUCTO.VC_DESCRIPCION_PRODUCTO "
			+ " FROM SIB.TBL_CAT_PRODUCTO PRODUCTO "
			+ " LEFT JOIN SIB.TBL_CAT_CONTRATO CONTRATO ON PRODUCTO.FK_ID_CONTRATO = CONTRATO.PK_ID_CONTRATO "
			+ " GROUP BY PRODUCTO.PK_ID_PRODUCTO "
			+ " ORDER BY PRODUCTO.PK_ID_PRODUCTO DESC", nativeQuery = true)
	public List<ProductoContrato> listaDeProductosConContrato();
	

}
