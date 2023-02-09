package ahorrosbienestar.sib.std.solicitudes.hallazgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudes.hallazgo.model.HallazgoSolicitud;

@Repository
public interface IHallazgoSolicitud extends JpaRepository<HallazgoSolicitud, Integer>{

}
