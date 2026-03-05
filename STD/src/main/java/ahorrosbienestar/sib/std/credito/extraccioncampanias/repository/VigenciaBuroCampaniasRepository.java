package ahorrosbienestar.sib.std.credito.extraccioncampanias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.extraccioncampanias.model.VigenciaBuroCampaniasModel;

@Repository

public interface VigenciaBuroCampaniasRepository extends JpaRepository<VigenciaBuroCampaniasModel, Integer> {

}