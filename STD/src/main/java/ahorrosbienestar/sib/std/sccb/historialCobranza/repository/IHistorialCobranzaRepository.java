package ahorrosbienestar.sib.std.sccb.historialCobranza.repository;


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
			+ "WHERE I_EXPEDIENTE_ID = :idExpediente", nativeQuery = true)
	public Optional<HistorialCobranza> getDetalleExpediente(@Param("idExpediente") Integer idExpediente);
	
	@Query(value = "SELECT hist.VC_RANGO_BANDA, hist.FL_IMPORTE, hist.FL_SALDO, "
			+ "hist.DT_FECHA_CARTERA, hist.VC_EN_COBRANZA_JUDICIAL " 
			+ "FROM TBL_SCCB_HISTORIAL_COBRANZA as hist "
			+ "WHERE hist.I_EXPEDIENTE_ID = :idExpediente", nativeQuery = true)
	public Optional<HistorialCobranza> getDatosGeneralesHistorialCobranza(@Param("idExpediente") Integer idExpediente);
}
