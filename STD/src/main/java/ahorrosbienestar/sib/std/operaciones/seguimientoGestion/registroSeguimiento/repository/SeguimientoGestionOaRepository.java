package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;

@Repository
public interface SeguimientoGestionOaRepository extends JpaRepository<SeguimientoGestionOaModel, Integer> {

}
