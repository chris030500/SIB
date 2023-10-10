package ahorrosbienestar.sib.std.cobranza.detalleCarga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.detalleCarga.model.DetalleCargaModel;

@Repository
public interface DetalleCargaRepository extends JpaRepository<DetalleCargaModel, Integer> {

}
