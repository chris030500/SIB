package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmEstatus;

@Repository
public interface precrmEstatusRepository
        extends JpaRepository<precrmEstatus, Integer> {

    List<precrmEstatus>
        findByActivoOrderByOrdenAsc(
            Integer activo
        );
}