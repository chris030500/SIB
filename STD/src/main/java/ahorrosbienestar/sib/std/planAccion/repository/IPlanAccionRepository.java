package ahorrosbienestar.sib.std.planAccion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.planAccion.model.PlanAccion;

@Repository
public interface IPlanAccionRepository extends JpaRepository<PlanAccion, Integer> {

	@Query(value = "SELECT * FROM TBL_PLAN_ACCION WHERE I_OBSERVACION=:idObservacion order by DT_FECHA_INICIO desc ", nativeQuery = true)
	List<PlanAccion> findAllActividades(Integer idObservacion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_PLAN_ACCION SET VC_DESC_ACTIVIDAD =:nombreActividad, I_RESPONSABLE=:idEncargado, DT_FECHA_INICIO =:dtFechaInicio,DT_FECHA_VENCIMIENTO=:dtFechaVencimiento,I_USUARIO_MOD=:pk_id_usuario WHERE PK_ID_PLANACCION =:idPA", nativeQuery = true)
	public int modificarPA(@Param("idPA") Integer idPA , @Param("nombreActividad") String nombreActividad,@Param("idEncargado") Integer idEncargado, @Param("dtFechaInicio") String dtFechaInicio,
			@Param("dtFechaVencimiento") String dtFechaVencimiento, @Param("pk_id_usuario") int pk_id_usuario);

}
