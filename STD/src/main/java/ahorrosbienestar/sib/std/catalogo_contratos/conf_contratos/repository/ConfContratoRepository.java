package ahorrosbienestar.sib.std.catalogo_contratos.conf_contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.catalogo_contratos.conf_contratos.model.ConfContrato;

@Repository
public interface ConfContratoRepository extends JpaRepository<ConfContrato, Integer> {

}
