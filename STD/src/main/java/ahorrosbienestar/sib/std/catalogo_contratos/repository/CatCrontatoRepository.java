package ahorrosbienestar.sib.std.catalogo_contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatCrontato;

@Repository
public interface CatCrontatoRepository extends JpaRepository<CatCrontato, Integer> {

}
