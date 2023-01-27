package ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;

@Repository
public interface IWSAutenticacionUserRepository extends JpaRepository<WSAutenticacionUser, Integer>{
	
}
