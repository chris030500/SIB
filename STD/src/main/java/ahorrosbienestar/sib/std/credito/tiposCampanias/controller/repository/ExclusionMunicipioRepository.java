package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.ExclusionMunicipioModel;

@Repository
public interface ExclusionMunicipioRepository extends JpaRepository<ExclusionMunicipioModel, Integer> {
    // Puedes agregar consultas personalizadas aquí si lo necesitas
}

