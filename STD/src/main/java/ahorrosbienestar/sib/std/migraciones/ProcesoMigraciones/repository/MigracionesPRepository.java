package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.repository;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MigracionesPRepository extends JpaRepository<MigracionesP, Integer> {

    // Botón Switch: Cambia solo el estatus y usuario que lo modificó
    @Modifying
    @Transactional
    @Query("UPDATE MigracionesP m SET m.estatus = :estatus, m.usuarioActualiza = :usuario WHERE m.idMigracion = :id")
    void actualizarEstatusSolo(
        @Param("id") Integer id,
        @Param("estatus") Integer estatus,
        @Param("usuario") String usuario
    );

    // Botón Ejecutar: Programa ejecución (I_EJECUTA, CRON, FECHA_EJECUCION, VC_USUARIO_EJECUTA)
    @Modifying
    @Transactional
    @Query("UPDATE MigracionesP m SET m.ejecuta = :ejecuta, m.cron = :cron, m.usuarioEjecuta = :usuario, m.fechaEjecucion = :fecha WHERE m.idMigracion = :id")
    void programarEjecucion(
        @Param("id") Integer id,
        @Param("ejecuta") Integer ejecuta,
        @Param("cron") String cron,
        @Param("usuario") String usuario,
        @Param("fecha") LocalDateTime fecha
    );

    // Al finalizar la migración: se actualiza estatus, mensaje, usuario y fecha
    @Modifying
    @Transactional
    @Query("UPDATE MigracionesP m SET m.ejecuta = 0, m.estatus = 0, m.fechaEjecucion = CURRENT_TIMESTAMP, m.mensajeResultado = :mensaje, m.usuarioActualiza = :usuario WHERE m.idMigracion = :id")
    void finalizarMigracion(
        @Param("id") Integer id,
        @Param("mensaje") String mensaje,
        @Param("usuario") String usuario
    );
    

}
