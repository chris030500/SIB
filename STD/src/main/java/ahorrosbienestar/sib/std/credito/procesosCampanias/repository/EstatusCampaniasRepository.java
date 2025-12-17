package ahorrosbienestar.sib.std.credito.procesosCampanias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;

@Repository

public interface EstatusCampaniasRepository extends JpaRepository<EstatusCampaniasModel, Integer> {


}
