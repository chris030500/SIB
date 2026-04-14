package ahorrosbienestar.sib.std.presolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;

public interface EmpresaSIBRepository extends JpaRepository<EmpresaSIB, Integer> {

	// Buscar empresa por nombre
    EmpresaSIB findByNombre(String nombre);

    // Obtener solo empresas activas (estatus = 1)
    List<EmpresaSIB> findByEstatus(int estatus);

}
