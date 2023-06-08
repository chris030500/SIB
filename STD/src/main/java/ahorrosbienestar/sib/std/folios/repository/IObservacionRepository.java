package ahorrosbienestar.sib.std.folios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.folios.model.Observaciones;

@Repository
public interface IObservacionRepository  extends JpaRepository<Observaciones, Integer>{


	@Query(value = "SELECT * FROM TBL_OBSERVACIONESXFOLIO WHERE I_FOLIO=:idFolio order by PK_ID_OBSERVACION desc ", nativeQuery = true)
	List<Observaciones> findAllFolio(Integer idFolio);

	@Query(value = "SELECT * FROM TBL_OBSERVACIONESXFOLIO WHERE I_RESPONSABLE=:i_id_empleado and I_FOLIO=:i_folio and i_confirma in (0,1) order by 1 ", nativeQuery = true)
	List<Observaciones> findAllxEmpleado(Integer i_id_empleado,Integer i_folio);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_OBSERVACIONESXFOLIO SET I_CONFIRMA = 1, DT_FECHA_CONFIRMACION =now(), I_USUARIO_MOD=:usuario WHERE PK_ID_OBSERVACION =:idObservacion", nativeQuery = true)
	public int aceptarObservacion(Integer idObservacion,Integer usuario);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_OBSERVACIONESXFOLIO SET I_CONFIRMA = 2, VC_DESCRIPCION_RECHAZO =:vc_motivo, DT_FECHA_CONFIRMACION =now(), I_USUARIO_MOD=:usuario WHERE PK_ID_OBSERVACION =:idObservacion", nativeQuery = true)
	public int rechazarObservacion(Integer idObservacion, String vc_motivo,Integer usuario);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_OBSERVACIONESXFOLIO SET I_AREA=:idAreaAtencion,I_RESPONSABLE=:idResponsableAtencion,DT_FECHA_VENCIMIENTO=:idFechaVencimiento,VC_DESCRIPCION=:desObservacion,I_USUARIO_MOD=:usuarioMod,I_USUARIO_ELIMINO=:usuarioElimino,I_OBSERVACION_OFICIO=:obsOficio  WHERE PK_ID_OBSERVACION =:idObservacion", nativeQuery = true)
	public int modObservacion(Integer idObservacion, Integer idAreaAtencion, Integer idResponsableAtencion,
			String idFechaVencimiento, String desObservacion, Integer usuarioMod, Integer usuarioElimino,Integer obsOficio);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_OBSERVACIONESXFOLIO SET I_USUARIO_ELIMINO=:usuarioElimina WHERE PK_ID_OBSERVACION =:idObservacion", nativeQuery = true)
	public int inactivarRegistro(Integer idObservacion, Integer usuarioElimina);
	
}
