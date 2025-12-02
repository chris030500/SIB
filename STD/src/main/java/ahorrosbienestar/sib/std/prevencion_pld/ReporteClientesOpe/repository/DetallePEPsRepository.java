package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
@Repository
public interface DetallePEPsRepository extends JpaRepository<DetallePEPsModel, Integer>{
 
}
