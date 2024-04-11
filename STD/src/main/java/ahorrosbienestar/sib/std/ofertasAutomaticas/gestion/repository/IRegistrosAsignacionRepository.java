package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.RegistrosAsignacion;

@Repository
public interface IRegistrosAsignacionRepository extends JpaRepository<RegistrosAsignacion, Integer>{

	@Query(value = "SELECT count(*) as VC_TOTAL,FK_ID_SUCURSAL,VC_NOMBRESUCURSAL,FECHAINICIO AS DT_FECHAINICIO, FECHAFIN AS VC_FECHAFIN  FROM SIB.VW_ASIGNACION_OFERTAS "
			+ "GROUP BY FK_ID_SUCURSAL,VC_NOMBRESUCURSAL,FECHAINICIO, FECHAFIN order by FK_ID_SUCURSAL asc ", nativeQuery = true) 
	public List<RegistrosAsignacion> obtRegistroParametros();
}
