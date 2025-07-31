package ahorrosbienestar.sib.std.credito.tiposCampanias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclisionesClientesModel;

public interface ExclusionClienteRepository extends JpaRepository<ExclisionesClientesModel, Integer> {
}
