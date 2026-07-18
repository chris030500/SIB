package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.model.OriginacionEstatusOfertaWs;

@Repository
public interface IOriginacionEstatusOfertaWsRepository extends JpaRepository<OriginacionEstatusOfertaWs, Integer> {
}
