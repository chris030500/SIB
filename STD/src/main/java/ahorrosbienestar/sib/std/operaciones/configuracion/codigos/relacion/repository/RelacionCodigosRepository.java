package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;

@Repository
public interface RelacionCodigosRepository extends JpaRepository<RelacionCodigosModel, Integer> {

}
