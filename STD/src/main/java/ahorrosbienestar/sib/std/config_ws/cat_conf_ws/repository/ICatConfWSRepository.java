package ahorrosbienestar.sib.std.config_ws.cat_conf_ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
 
@Repository
public interface ICatConfWSRepository extends JpaRepository<CatConfWS, Integer> {
 
}
