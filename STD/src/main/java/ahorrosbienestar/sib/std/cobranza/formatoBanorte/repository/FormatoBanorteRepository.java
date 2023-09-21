package ahorrosbienestar.sib.std.cobranza.formatoBanorte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.formatoBanorte.model.FormatoBanorteModel;

@Repository
public interface FormatoBanorteRepository extends JpaRepository<FormatoBanorteModel, Integer> {

}
