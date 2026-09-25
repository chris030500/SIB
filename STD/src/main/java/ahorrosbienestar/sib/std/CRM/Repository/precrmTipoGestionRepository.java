package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmTipoGestion;


@Repository
public interface precrmTipoGestionRepository
        extends JpaRepository<precrmTipoGestion, Integer> {


    List<precrmTipoGestion>
        findByActivoOrderByOrdenAsc(
            Integer activo
        );

}