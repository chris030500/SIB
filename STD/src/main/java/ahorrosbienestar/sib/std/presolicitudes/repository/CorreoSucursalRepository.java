package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.CorreoSucursal;

import java.util.Optional;

@Repository
public interface CorreoSucursalRepository extends JpaRepository<CorreoSucursal, Integer> {

	Optional<CorreoSucursal> findByiSuc(int iSuc); // Consultar por la sucursal
}
