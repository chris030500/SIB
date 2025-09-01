package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;

@Repository
public interface IProcesoBasesEbrRepository extends JpaRepository<ProcesoBasesEbr, Integer> {
	@Query(value = "SELECT MAX(PK_PROCESO_EBR_ID) FROM SIB.TBL_PLD_PROCESO_BASES_EBR", nativeQuery = true)
	public Integer findMaxProceso();
}
