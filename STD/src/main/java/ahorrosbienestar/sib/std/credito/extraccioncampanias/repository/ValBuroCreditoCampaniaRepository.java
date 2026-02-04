package ahorrosbienestar.sib.std.credito.extraccioncampanias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.extraccioncampanias.model.ValBuroCreditoCampaniaModel;

@Repository

public interface ValBuroCreditoCampaniaRepository extends JpaRepository<ValBuroCreditoCampaniaModel, Integer> {

}
