package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.DetalleFacturaMod;

@Repository
public interface IDetalleFactura extends JpaRepository<DetalleFacturaMod,Integer>{

	
	@Query(value = "SELECT t1.PK_ID as ID,t1.FK_Folio as folio, "
            + "t1.VC_Concepto as concepto, "
            + "t1.DC_Cantidad as cantidad, "
            + "t1.VC_Descripcion as descripcion, "
            + "t1.DC_Precio_Unit as preciounitario, "
            + "t1.DC_Total as total, "
            + "DATE_FORMAT(t2.DT_Fecha_Emision, '%Y-%m-%d') as fechaemision "
            + "FROM SIB.TBL_FACTURA t1 "
            + "INNER JOIN SIB.TBL_DETALLE_FACTURA t2 ON t1.FK_Folio = t2.PK_I_Folio "
            + "WHERE  t2.DT_Fecha_Emision >= DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01') "
            + "AND t2.DT_Fecha_Emision <= LAST_DAY(CURDATE() - INTERVAL 1 MONTH)", nativeQuery = true)
	public List<DetalleFacturaMod> obtDetalleFacturaMod();
		
}
