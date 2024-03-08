package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;

@Repository
public interface RelacionCodigosRepository extends JpaRepository<RelacionCodigosModel, Integer> {

}
