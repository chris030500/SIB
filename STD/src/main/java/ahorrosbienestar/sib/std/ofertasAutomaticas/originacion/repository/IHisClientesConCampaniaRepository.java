package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.model.OriginacionEstatusOfertaWs;

@Repository
public interface IHisClientesConCampaniaRepository extends JpaRepository<OriginacionEstatusOfertaWs, Integer> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_HIS_CLIENTES_CON_CAMPANIA "
			+ "SET I_ESTATUS_MC = 0 "
			+ "WHERE I_ID_CLIENTES_CON_CAMPANIA = :idClientesConCampania", nativeQuery = true)
	int actualizarEstatusMcPorIdClientesConCampania(@Param("idClientesConCampania") Integer idClientesConCampania);
}
