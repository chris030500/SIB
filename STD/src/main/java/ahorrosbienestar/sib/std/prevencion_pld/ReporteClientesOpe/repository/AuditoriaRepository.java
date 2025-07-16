/*package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    // Método para ejecutar el procedimiento almacenado
    @Transactional
    @Procedure(name = "SIB.SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES")
    void actualizarPeps();
}*/