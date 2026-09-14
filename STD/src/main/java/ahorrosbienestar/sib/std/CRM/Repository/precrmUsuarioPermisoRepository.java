package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmUsuarioPermiso;


@Repository
public interface precrmUsuarioPermisoRepository
        extends JpaRepository<precrmUsuarioPermiso, Long> {


    // =========================================================
    // OBTENER TODOS LOS PERMISOS ACTIVOS
    // =========================================================

    List<precrmUsuarioPermiso>
        findByActivo(
            Integer activo
        );


    // =========================================================
    // OBTENER PERMISOS DE UN USUARIO
    // =========================================================

    List<precrmUsuarioPermiso>
        findByUsuarioAndActivo(
            String usuario,
            Integer activo
        );


    // =========================================================
    // OBTENER USUARIOS DE UNA FUNCIONALIDAD
    // =========================================================

    List<precrmUsuarioPermiso>
        findByFkFuncionalidadAndActivo(
            Long fkFuncionalidad,
            Integer activo
        );


    // =========================================================
    // OBTENER USUARIOS POR FUNCIONALIDAD Y PERMISO
    // =========================================================

    List<precrmUsuarioPermiso>
        findByFkFuncionalidadAndFkPermisoAndActivo(
            Long fkFuncionalidad,
            Long fkPermiso,
            Integer activo
        );


    // =========================================================
    // BUSCAR RELACION EXACTA
    // USUARIO + FUNCIONALIDAD + PERMISO
    // =========================================================

    Optional<precrmUsuarioPermiso>
        findByUsuarioAndFkFuncionalidadAndFkPermiso(
            String usuario,
            Long fkFuncionalidad,
            Long fkPermiso
        );


    // =========================================================
    // VALIDAR SI EL USUARIO TIENE UN PERMISO ACTIVO
    // =========================================================

    boolean
        existsByUsuarioAndFkFuncionalidadAndFkPermisoAndActivo(
            String usuario,
            Long fkFuncionalidad,
            Long fkPermiso,
            Integer activo
        );


    // =========================================================
    // OBTENER PERMISOS ACTIVOS DE UN USUARIO
    // EN UNA FUNCIONALIDAD
    // =========================================================

    List<precrmUsuarioPermiso>
        findByUsuarioAndFkFuncionalidadAndActivo(
            String usuario,
            Long fkFuncionalidad,
            Integer activo
        );


    // =========================================================
    // OBTENER TODAS LAS RELACIONES DE UNA FUNCIONALIDAD
    // INCLUYENDO ACTIVAS E INACTIVAS
    // =========================================================

    List<precrmUsuarioPermiso>
        findByFkFuncionalidad(
            Long fkFuncionalidad
        );

}