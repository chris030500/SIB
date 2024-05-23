package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;

@Repository
public interface ISolicitudesCreditoRepository extends JpaRepository<SolicitudesCredito, Integer> {
	
	@Query(value = "SELECT S.I_SOLICITUD_ID, S.I_CREDITO_ID, S.VC_NOMBRE_CLIENTE, S.I_NO_CLIENTE, S.VC_ROL, S.VC_NOMBRE_CENTRO, "
            + "S.VC_ETAPA, S.VC_ESTATUS, S.FL_MONTO, S.DT_FECHA_RECEPCION, S.DT_FECHA_INICIO_TRAMITE, S.DT_FECHA_AUTORIZACION, "
            + "S.DT_FECHA_DESEMBOLSO "
            + "FROM TBL_SCCB_SOLICITUDES AS S "
            + "WHERE S.I_CREDITO_ID = :idCredito", nativeQuery = true)
	public List<SolicitudesCredito> findByIdCredito(@Param("idCredito") Integer idCredito);
	
}
	
