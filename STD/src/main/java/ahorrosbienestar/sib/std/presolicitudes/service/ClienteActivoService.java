package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;
import ahorrosbienestar.sib.std.presolicitudes.repository.ClienteActivoRepository;

@Service
public class ClienteActivoService {
	
	@Autowired
	private ClienteActivoRepository clienteActivoRepository;
	
	// Método para obtener clientes activos por empresaEmpleado
    public List<Empleado> obtenerClientesPorEmpresaEmpleado(Integer empresaEmpleado) {
        return clienteActivoRepository.findByEmpresaEmpleado(empresaEmpleado);
    }
}
