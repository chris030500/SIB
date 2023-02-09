package ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.model.WSRespuestaAutenticacion;
 
@Repository
public interface IWSRespuestaAutenticacionRepository extends JpaRepository<WSRespuestaAutenticacion, Integer> {
 
}
