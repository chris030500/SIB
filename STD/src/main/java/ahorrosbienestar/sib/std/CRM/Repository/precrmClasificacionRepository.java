package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmClasificacion;

@Repository
public interface precrmClasificacionRepository
        extends JpaRepository<precrmClasificacion, Integer> {

    List<precrmClasificacion>
        findByActivoOrderByOrdenAsc(
            Integer activo
        );
}