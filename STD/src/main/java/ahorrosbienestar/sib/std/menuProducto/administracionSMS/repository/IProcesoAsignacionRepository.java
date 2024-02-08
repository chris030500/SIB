package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.ProcesoAsignacion;

@Repository
public interface IProcesoAsignacionRepository extends JpaRepository<ProcesoAsignacion, Integer>{

	@Query(value = "SELECT PK_ID_PROCESO FROM SIB.TBL_REG_PROCESOS_GESTION_OA WHERE VC_ESTATUS_PROCESO = 'A'", nativeQuery = true)
	public List<Integer> obtProcesoAsignacion();

}
