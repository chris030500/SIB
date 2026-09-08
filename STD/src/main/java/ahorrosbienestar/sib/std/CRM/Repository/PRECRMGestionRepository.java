package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.PRECRMGestion;


@Repository
public interface PRECRMGestionRepository
        extends JpaRepository<PRECRMGestion, Long> {


    List<PRECRMGestion>
        findByFkSospechosoOrderByFechaGestionDesc(
            Long fkSospechoso
        );
}