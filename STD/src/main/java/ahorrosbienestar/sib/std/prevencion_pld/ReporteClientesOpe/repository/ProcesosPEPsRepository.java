package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;

@Repository
public interface ProcesosPEPsRepository extends JpaRepository<ProcesosPEPsModel, Integer>  {

}
