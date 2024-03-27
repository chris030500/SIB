package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.ProcesoAsignacion;

@Repository
public interface IProcesoAsignacionRepository extends JpaRepository<ProcesoAsignacion, Integer>{

	@Query(value = "SELECT\r\n"
			+ "  reg.PK_ID_PROCESO as 'Proceso',\r\n"
			+ "  base.VC_NOMBRE_BASE as 'Base'\r\n"
			+ "FROM SIB.TBL_REG_PROCESOS_GESTION_OA reg\r\n"
			+ "JOIN SIB.TBL_BASES_OA base\r\n"
			+ "ON reg.I_TIPO_BASE = base.PK_ID_BASE\r\n"
			+ "WHERE reg.VC_ESTATUS_PROCESO = 'A'", nativeQuery = true)
	public List<ProcesoAsignacion> obtProcesoAsignacion();

}
