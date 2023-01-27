package ahorrosbienestar.sib.std.config_ws.bitacora_consumo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.modelo.BitacoraConsumo;

@Repository
public interface IBitacoraConsumoRepository extends JpaRepository<BitacoraConsumo, Integer>{

}
