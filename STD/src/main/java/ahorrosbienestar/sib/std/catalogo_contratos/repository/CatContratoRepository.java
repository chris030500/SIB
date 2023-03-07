package ahorrosbienestar.sib.std.catalogo_contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatContrato;

@Repository
@Transactional
public interface CatContratoRepository extends JpaRepository<CatContrato, Integer> {

}
