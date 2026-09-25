package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmHistorialEstatus;


@Repository
public interface precrmHistorialEstatusRepository
        extends JpaRepository<precrmHistorialEstatus, Long> {


    List<precrmHistorialEstatus>
        findByFkSospechosoOrderByFechaCambioDesc(
            Long fkSospechoso
        );

}