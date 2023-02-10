package ahorrosbienestar.sib.std.videoGrabacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.videoGrabacion.model.OpBusquedaVideo;

@Repository
public interface IVideoGrabacionRepository extends JpaRepository<OpBusquedaVideo, Integer>{

}
