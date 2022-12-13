package ahorrosbienestar.sib.std.docto_envio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Repository
public interface IDoctEnvioRepository extends JpaRepository<DoctEnvio, Integer>{

}
