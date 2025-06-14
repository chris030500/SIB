package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;

@Repository
public interface ReporteClientesOpeRepository extends JpaRepository<ReporteClientesOpeModel, Long> {

    /**
     * Busca un registro por clienteId (suponiendo que clienteId es un campo único en la entidad).
     * Ajusta el nombre del método y el tipo según tu entidad.
     */
	Optional<ReporteClientesOpeModel> findByClienteId(Integer clienteId);

}


