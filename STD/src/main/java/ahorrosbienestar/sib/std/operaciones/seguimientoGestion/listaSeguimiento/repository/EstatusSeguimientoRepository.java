package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.EstatusSeguimientoModel;

@Repository
public interface EstatusSeguimientoRepository extends JpaRepository<EstatusSeguimientoModel, Integer> {
	
	@Query(value = "SELECT COUNT(*) AS TOTAL_REGISTROS,PK_ID,V_VALOR,CASE WHEN FK_ID_SUCURSAL=100 THEN 'CALL CENTER' ELSE VC_NOMBRESUCURSAL END VC_NOMBRESUCURSAL, FK_ID_SUCURSAL AS I_ID_SUCURSAL "
			+ "FROM SIB.VW_ASIGNACION_OFERTAS "
			+ "where FK_ID_SUCURSAL = :sucursal "
			+ "GROUP BY PK_ID,V_VALOR,VC_NOMBRESUCURSAL,FK_ID_SUCURSAL", nativeQuery = true)
	public List<EstatusSeguimientoModel> listaDeEstatus(@Param("sucursal") int sucursal);

}
