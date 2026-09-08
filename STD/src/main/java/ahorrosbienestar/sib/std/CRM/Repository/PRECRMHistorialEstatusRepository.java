package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.PRECRMHistorialEstatus;


@Repository
public interface PRECRMHistorialEstatusRepository
        extends JpaRepository<PRECRMHistorialEstatus, Long> {


    List<PRECRMHistorialEstatus>
        findByFkSospechosoOrderByFechaCambioDesc(
            Long fkSospechoso
        );
}