package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import javax.transaction.Transactional;
 
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ProcesosPEPsRepository extends JpaRepository<ProcesosPEPsModel, Integer> {
 
    @Modifying
    @Transactional
    @Query("UPDATE ProcesosPEPsModel p SET p.i_estatus = 0 WHERE p.i_estatus = 1")
    void desactivarProcesosActivos();
}

