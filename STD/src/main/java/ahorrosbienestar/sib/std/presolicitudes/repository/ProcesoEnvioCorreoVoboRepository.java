package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.ProcesoEnvioCorreoVobo;

@Repository
public interface ProcesoEnvioCorreoVoboRepository extends JpaRepository<ProcesoEnvioCorreoVobo, Integer> {

    // Aquí puedes definir consultas personalizadas si es necesario
    // Ejemplo: Buscar por empresa
    ProcesoEnvioCorreoVobo findByVcEmpresa(String vcEmpresa);

    // Otros métodos personalizados pueden ser agregados aquí
}
