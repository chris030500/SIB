package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmPermiso;


@Repository
public interface precrmPermisoRepository
        extends JpaRepository<precrmPermiso, Long> {


    // =========================================================
    // OBTENER PERMISOS ACTIVOS
    // =========================================================

    List<precrmPermiso>
        findByActivo(
            Integer activo
        );


    // =========================================================
    // BUSCAR PERMISO POR CLAVE
    // =========================================================

    precrmPermiso
        findByClavePermiso(
            String clavePermiso
        );


    // =========================================================
    // VALIDAR SI EXISTE PERMISO
    // =========================================================

    boolean
        existsByClavePermiso(
            String clavePermiso
        );

}