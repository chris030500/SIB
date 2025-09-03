package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.TipoEmpresa;

@Repository
public interface ITipoEmpresaRepository extends JpaRepository<TipoEmpresa, Integer> {

}
