package ahorrosbienestar.sib.std.convenios.procesoCarga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.convenios.procesoCarga.model.ProcesoCargaModel;

@Repository
public interface ProcesoCargaRepository extends JpaRepository<ProcesoCargaModel, Integer>{

}
