package ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.model.BitacoraModel;

@Repository
public interface BitacoraRepository extends JpaRepository<BitacoraModel, Integer> {

}
