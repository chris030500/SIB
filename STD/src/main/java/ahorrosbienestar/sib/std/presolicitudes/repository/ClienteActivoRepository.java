package ahorrosbienestar.sib.std.presolicitudes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;

@Repository
public interface ClienteActivoRepository extends JpaRepository<Empleado, Integer> {
    
    // Método para buscar clientes activos por empresaEmpleado
    List<Empleado> findByEmpresaEmpleado(Integer empresaEmpleado);
}
