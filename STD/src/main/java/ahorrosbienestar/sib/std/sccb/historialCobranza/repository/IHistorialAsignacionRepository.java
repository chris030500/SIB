package ahorrosbienestar.sib.std.sccb.historialCobranza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialAsignacion;


@Repository
public interface IHistorialAsignacionRepository extends JpaRepository<HistorialAsignacion, Integer> {
	
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_HISTORIAL_ASIGNACION "
			+ "WHERE I_CREDITO_ID = :idCredito ", nativeQuery = true)
	public List<HistorialAsignacion> getByIdCreditoHistorialAsignacion(@Param("idCredito") Integer idCredito);
}
