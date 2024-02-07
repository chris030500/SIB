package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.SucursalesNotificar;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface ISucursalesNotificarRepository extends JpaRepository<SucursalesNotificar, Integer>{

	
	@Query(value = "select t1.FK_ID_SUCURSAL as FK_ID_SUCURSAL from SIB.TBL_ASIGNACION_OFERTAS t1 "
			+ "inner join SIB.TBL_TELEFONOS_SUCURSAL_OA t2 on t1.FK_ID_SUCURSAL = t2.FK_ID_SUCURSAL "
			+ "WHERE date_format(t1.DT_FECHA_REASIGNACION_NO_SEGUIMIENTO,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') and t1.I_REASIGNAR=1 AND t1.I_ESTATUS=7 "
			+ "GROUP BY t1.FK_ID_SUCURSAL ", nativeQuery = true)
	public List<SucursalesNotificar> consultaSucursal();
	
}
