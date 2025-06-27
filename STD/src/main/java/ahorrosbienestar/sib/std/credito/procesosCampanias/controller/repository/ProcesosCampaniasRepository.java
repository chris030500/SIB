package ahorrosbienestar.sib.std.credito.procesosCampanias.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
@Repository

public interface ProcesosCampaniasRepository extends JpaRepository<TiposCampaniasModel, Integer> {

}
