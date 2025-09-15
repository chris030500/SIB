package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.PeriodoEbr;

@Repository
public interface IPeriodoEbrRepository extends JpaRepository<PeriodoEbr, Integer> {
	
	@Query(value = "SELECT * " +
	        "FROM SIB.TBL_PLD_CAT_PERIODOS " +
	        "WHERE CAST(STR_TO_DATE(CONCAT(:anio, '-', LPAD(I_MES_INICIO, 2, '0'), '-', LPAD(I_DIA_INICIO, 2, '0')), '%Y-%m-%d') AS DATE) = :fechaInicio " +
	        "AND CAST(STR_TO_DATE(CONCAT(:anio, '-', LPAD(I_MES_FIN, 2, '0'), '-', LPAD(I_DIA_FIN, 2, '0')), '%Y-%m-%d') AS DATE) = :fechaFin",
	       nativeQuery = true)
	public Optional<PeriodoEbr> obtenerPeriodoEbr(@Param("anio") int anio,
			@Param("fechaInicio") LocalDate fechaInicio,
			@Param("fechaFin") LocalDate fechaFin);
}
