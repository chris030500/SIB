package ahorrosbienestar.sib.std.sccb.historialCobranza.repository;


import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialCobranza;


@Repository
public interface IHistorialCobranzaRepository extends JpaRepository<HistorialCobranza, Integer> {
	
	@Query(value = "SELECT * " 
			+ "FROM TBL_SCCB_HISTORIAL_COBRANZA "
			+ "WHERE I_SOLICITUD_ID = :idSolicitud", nativeQuery = true)
	public Optional<HistorialCobranza> getDetalleExpediente(@Param("idSolicitud") Integer idSolicitud);
	
	@Query(value = "SELECT T1.VC_RANGO_BANDA AS RANGO_BANDA, "
			+ "T1.FL_IMPORTE AS IMPORTE, T1.FL_SALDO AS SALDO, "
			+ "T1.FL_ATRASO AS ATRASO, T1.DT_FECHA_CARTERA AS FECHA_CARTERA, "
			+ "T1.VC_INTENCIONALIDAD AS INTENCION, "
			+ "T1.VC_EN_COBRANZA_JUDICIAL AS CJ, "
			+ "T2.I_CREDITO_ID AS CREDITO_ID "
			+ "FROM TBL_SCCB_HISTORIAL_COBRANZA T1 "
			+ "JOIN TBL_SCCB_DATOS_SOLICITUD T2 ON T1.I_SOLICITUD_ID = T2.I_SOLICITUD_ID "
			+ "WHERE T2.I_CREDITO_ID = :idCredito", nativeQuery = true)
	public Optional<Map<String, Object>> getDatosGeneralesHistorialCobranza(@Param("idCredito") Integer idCredito);
}
