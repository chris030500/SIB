package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;

@Repository
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Integer> {

	@Query(value = "SELECT SC.PK_ID_SOLICITUD, SC.VC_EMPLEADO, SC.DT_FECHA_SOLICITUD, SC.VC_EMPRESA, " +
	        "SC.DC_MONTO_SOLICITADO, SC.DC_MONTO_DESCONTAR, SCUR.VC_NOMBRESUCURSAL, SC.I_ESTATUS " +
	        "FROM SIB.TBL_SOLICITUDES_CREDITO SC " +
	        "INNER JOIN SIB.TBL_SUCURSALES SCUR ON SCUR.PK_ID_SUCURSAL = SC.I_SUCURSAL " +
	        "WHERE SC.I_EJECUTIVO = :ejecutivo",
	    countQuery = "SELECT COUNT(*) FROM SIB.TBL_SOLICITUDES_CREDITO WHERE I_EJECUTIVO = :ejecutivo",
	    nativeQuery = true)
	Page<Object[]> buscarSolicitudesRaw(@Param("ejecutivo") Integer ejecutivo, Pageable pageable);


	@Query(value = "SELECT SC.PK_ID_SOLICITUD, SC.VC_EMPLEADO, SC.DT_FECHA_SOLICITUD, SC.VC_EMPRESA, " +
	        "SC.DC_MONTO_SOLICITADO, SC.DC_MONTO_DESCONTAR, SCUR.VC_NOMBRESUCURSAL, SC.I_ESTATUS " +
	        "FROM SIB.TBL_SOLICITUDES_CREDITO SC " +
	        "INNER JOIN SIB.TBL_SUCURSALES SCUR ON SC.I_SUCURSAL = SCUR.PK_ID_SUCURSAL " +
	        "WHERE SC.I_SUCURSAL IN (:sucursales)",
	    countQuery = "SELECT COUNT(*) FROM SIB.TBL_SOLICITUDES_CREDITO WHERE I_SUCURSAL IN (:sucursales)",
	    nativeQuery = true)
	Page<Object[]> buscarSolicitudesPorSucursalesRaw(@Param("sucursales") List<Integer> sucursales, Pageable pageable);


	@Query(value = "SELECT SC.PK_ID_SOLICITUD, SC.VC_EMPLEADO, SC.DT_FECHA_SOLICITUD, SC.VC_EMPRESA, " +
	        "SC.DC_MONTO_SOLICITADO, SC.DC_MONTO_DESCONTAR, SCUR.VC_NOMBRESUCURSAL, SC.I_ESTATUS " +
	        "FROM SIB.TBL_SOLICITUDES_CREDITO SC " +
	        "INNER JOIN SIB.TBL_SUCURSALES SCUR ON SC.I_SUCURSAL = SCUR.PK_ID_SUCURSAL",
	    countQuery = "SELECT COUNT(*) FROM SIB.TBL_SOLICITUDES_CREDITO",
	    nativeQuery = true)
	Page<Object[]> buscarTodasSolicitudesRaw(Pageable pageable);


	@Query("SELECT s FROM SolicitudCredito s WHERE s.id IN :ids")
	List<SolicitudCredito> findByIds(@Param("ids") List<Integer> ids);

	@Query("SELECT COUNT(s) FROM SolicitudCredito s WHERE s.empresa = :empresa AND s.estatus = 5")
	int contarSolicitudesPorEmpresa(@Param("empresa") String empresa);

	@Transactional
	@Modifying
	@Query(value = "UPDATE TBL_SOLICITUDES_CREDITO SET FK_ID_PROCESO = :idProceso, I_ESTATUS = 2 WHERE VC_EMPRESA = :empresa AND I_ESTATUS = 5", nativeQuery = true)
	int actualizarSolicitudesPorEmpresa(@Param("empresa") String empresa, @Param("idProceso") Integer idProceso);
}