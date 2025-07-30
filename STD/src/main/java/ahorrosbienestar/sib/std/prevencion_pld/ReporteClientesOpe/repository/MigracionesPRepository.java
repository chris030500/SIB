package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MigracionesPRepository extends JpaRepository<MigracionesP, Integer> {

    // Para activar ejecución
    @Modifying
    @Transactional
    @Query("UPDATE MigracionesP m SET m.ejecuta = 1, m.estatus = 1, m.usuarioEjecuta = :usuario WHERE m.idMigracion = :id")
    void activarEjecucion(@Param("id") Integer id, @Param("usuario") String usuario);

    // Para finalizar la migración
    @Modifying
    @Transactional
    @Query("UPDATE MigracionesP m SET m.ejecuta = 0, m.estatus = 0, m.fechaEjecucion = CURRENT_TIMESTAMP, m.mensajeResultado = :mensaje, m.usuarioActualiza = :usuario WHERE m.idMigracion = :id")
    void finalizarMigracion(@Param("id") Integer id, @Param("mensaje") String mensaje, @Param("usuario") String usuario);
}

