package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmFuncionalidad;


@Repository
public interface precrmFuncionalidadRepository
        extends JpaRepository<precrmFuncionalidad, Long> {


    // =========================================================
    // OBTENER FUNCIONALIDADES ACTIVAS
    // ORDENADAS COMO SE MOSTRARAN EN PANTALLA
    // =========================================================

    List<precrmFuncionalidad>
        findByActivoOrderByOrdenAsc(
            Integer activo
        );


    // =========================================================
    // BUSCAR FUNCIONALIDAD POR CLAVE
    // =========================================================

    precrmFuncionalidad
        findByClaveFuncionalidad(
            String claveFuncionalidad
        );


    // =========================================================
    // VALIDAR SI EXISTE UNA CLAVE
    // =========================================================

    boolean
        existsByClaveFuncionalidad(
            String claveFuncionalidad
        );

}