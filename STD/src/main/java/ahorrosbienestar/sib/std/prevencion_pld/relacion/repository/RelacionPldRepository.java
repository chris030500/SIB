package ahorrosbienestar.sib.std.prevencion_pld.relacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.relacion.model.RelacionPldModel;

@Repository
public interface RelacionPldRepository extends JpaRepository<RelacionPldModel, Integer> {

}
