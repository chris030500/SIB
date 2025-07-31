package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.ExclisionesClientesModel;

public interface ExclusionClienteRepository extends JpaRepository<ExclisionesClientesModel, Integer> {
}
