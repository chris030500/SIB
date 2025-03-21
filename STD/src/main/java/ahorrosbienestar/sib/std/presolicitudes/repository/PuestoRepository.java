package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Integer> {
}
