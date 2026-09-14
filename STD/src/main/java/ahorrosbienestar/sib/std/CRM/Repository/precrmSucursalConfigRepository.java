package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmSucursalConfig;


// =========================================================
// REPOSITORY CONFIGURACION DE SUCURSALES PRE CRM
// =========================================================

@Repository
public interface precrmSucursalConfigRepository
        extends JpaRepository<
            precrmSucursalConfig,
            Integer
        > {


    // =====================================================
    // BUSCAR CONFIGURACION POR SUCURSAL
    // =====================================================

    Optional<precrmSucursalConfig>
        findByFkSucursal(
            Integer fkSucursal
        );


    // =====================================================
    // OBTENER CONFIGURACIONES POR ESTATUS
    //
    // 1 = ACTIVA PRE CRM
    // 0 = INACTIVA PRE CRM
    // =====================================================

    List<precrmSucursalConfig>
        findByActivoPrecrm(
            Integer activoPrecrm
        );


    // =====================================================
    // SUCURSALES DISPONIBLES
    //
    // Obtiene todas las sucursales que NO estan activas
    // dentro de PRE CRM.
    //
    // Si nunca han sido configuradas:
    //     ACTIVO_PRECRM = 0
    //
    // Si fueron desactivadas:
    //     ACTIVO_PRECRM = 0
    // =====================================================

    @Query(
        value =
            "SELECT "
          + "    S.PK_ID_SUCURSAL AS pkIdSucursal, "
          + "    S.VC_NOMBRESUCURSAL AS nombreSucursal, "
          + "    S.VC_TIPOSUCURSAL AS tipoSucursal, "
          + "    S.I_REGIONID AS regionId, "
          + "    S.VC_ESTATUS AS estatusSucursal, "
          + "    COALESCE(C.ACTIVO_PRECRM, 0) AS activoPrecrm "
          + "FROM SIB.TBL_SUCURSALES S "
          + "LEFT JOIN SIB.TBL_PRECRM_SUCURSAL_CONFIG C "
          + "    ON C.FK_SUCURSAL = S.PK_ID_SUCURSAL "
          + "WHERE COALESCE(C.ACTIVO_PRECRM, 0) = 0 "
          + "ORDER BY S.VC_NOMBRESUCURSAL ASC",
        nativeQuery = true
    )
    List<Map<String, Object>>
        obtenerSucursalesDisponibles();


    // =====================================================
    // SUCURSALES HABILITADAS EN PRE CRM
    //
    // Solo muestra las sucursales que tengan:
    // ACTIVO_PRECRM = 1
    // =====================================================

    @Query(
        value =
            "SELECT "
          + "    S.PK_ID_SUCURSAL AS pkIdSucursal, "
          + "    S.VC_NOMBRESUCURSAL AS nombreSucursal, "
          + "    S.VC_TIPOSUCURSAL AS tipoSucursal, "
          + "    S.I_REGIONID AS regionId, "
          + "    S.VC_ESTATUS AS estatusSucursal, "
          + "    C.PK_SUCURSAL_CONFIG AS pkSucursalConfig, "
          + "    C.ACTIVO_PRECRM AS activoPrecrm, "
          + "    C.FECHA_ALTA AS fechaAlta, "
          + "    C.USUARIO_ALTA AS usuarioAlta, "
          + "    C.FECHA_MODIFICACION AS fechaModificacion, "
          + "    C.USUARIO_MODIFICACION AS usuarioModificacion "
          + "FROM SIB.TBL_SUCURSALES S "
          + "INNER JOIN SIB.TBL_PRECRM_SUCURSAL_CONFIG C "
          + "    ON C.FK_SUCURSAL = S.PK_ID_SUCURSAL "
          + "WHERE C.ACTIVO_PRECRM = 1 "
          + "ORDER BY S.VC_NOMBRESUCURSAL ASC",
        nativeQuery = true
    )
    List<Map<String, Object>>
        obtenerSucursalesPrecrm();


    // =====================================================
    // VALIDAR SI SUCURSAL YA ESTA ACTIVA EN PRE CRM
    // =====================================================

    boolean
        existsByFkSucursalAndActivoPrecrm(
            Integer fkSucursal,
            Integer activoPrecrm
        );

}