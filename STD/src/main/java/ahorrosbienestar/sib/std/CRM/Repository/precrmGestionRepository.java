package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmGestion;


@Repository
public interface precrmGestionRepository
        extends JpaRepository<precrmGestion, Long> {


    List<precrmGestion>
        findByFkSospechosoOrderByFechaGestionDesc(
            Long fkSospechoso
        );
}