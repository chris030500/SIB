package ahorrosbienestar.sib.std.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Repository
public interface ISolicitudEnvio extends JpaRepository<SolicitudEnvio, Integer>{

}
