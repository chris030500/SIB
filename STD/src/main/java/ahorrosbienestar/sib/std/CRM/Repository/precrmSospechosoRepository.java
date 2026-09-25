package ahorrosbienestar.sib.std.CRM.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmSospechoso;


@Repository
public interface precrmSospechosoRepository
        extends JpaRepository<precrmSospechoso, Long> {


    // =====================================================
    // VALIDAR SI YA EXISTE UN REGISTRO DE LA CARGA
    // =====================================================

    boolean existsByFkCargaDetalle(
        Long fkCargaDetalle
    );


    // =====================================================
    // VALIDAR SI EL SOSPECHOSO YA ESTABA REGISTRADO
    // =====================================================

    boolean existsByNombreIgnoreCaseAndTelefonoPrincipalAndActivo(
        String nombre,
        String telefonoPrincipal,
        Integer activo
    );


    // =====================================================
    // OBTENER SOSPECHOSOS ACTIVOS
    // =====================================================

    List<precrmSospechoso>
        findByActivoOrderByFechaRegistroDesc(
            Integer activo
        );


    // =====================================================
    // CONTAR POR ESTATUS
    // GLOBAL
    // =====================================================

    long countByEstatusAndActivo(
        String estatus,
        Integer activo
    );


    // =====================================================
    // BUSQUEDA GLOBAL CON FILTROS
    // JPQL - USA PROPIEDADES JAVA
    // =====================================================

    @Query("""
        SELECT s
        FROM precrmSospechoso s
        WHERE s.activo = 1

        AND (
            :folio IS NULL
            OR UPPER(s.folio)
                LIKE UPPER(CONCAT('%', :folio, '%'))
        )

        AND (
            :nombre IS NULL
            OR UPPER(s.nombre)
                LIKE UPPER(CONCAT('%', :nombre, '%'))
        )

        AND (
            :telefono IS NULL
            OR s.telefonoPrincipal
                LIKE CONCAT('%', :telefono, '%')
            OR s.telefonoAlterno
                LIKE CONCAT('%', :telefono, '%')
        )

        AND (
            :clasificacion IS NULL
            OR UPPER(s.clasificacion)
                = UPPER(:clasificacion)
        )

        AND (
            :estatus IS NULL
            OR UPPER(s.estatus)
                = UPPER(:estatus)
        )

        AND (
            :fechaInicio IS NULL
            OR s.fechaRegistro >= :fechaInicio
        )

        AND (
            :fechaFin IS NULL
            OR s.fechaRegistro < :fechaFin
        )

        ORDER BY s.fechaRegistro DESC
    """)
    List<precrmSospechoso> buscarSospechosos(

        @Param("folio")
        String folio,

        @Param("nombre")
        String nombre,

        @Param("telefono")
        String telefono,

        @Param("clasificacion")
        String clasificacion,

        @Param("estatus")
        String estatus,

        @Param("fechaInicio")
        LocalDateTime fechaInicio,

        @Param("fechaFin")
        LocalDateTime fechaFin

    );


    // =====================================================
    // BUSCAR SOSPECHOSOS ASIGNADOS AL USUARIO
    // SQL NATIVO
    // =====================================================

    @Query(
        value =
            "SELECT DISTINCT S.* " +

            "FROM SIB.TBL_PRECRM_SOSPECHOSO S " +

            "INNER JOIN SIB.TBL_PRECRM_ASIGNACION A " +
            "ON A.FK_SOSPECHOSO = S.PK_SOSPECHOSO " +

            "WHERE S.I_ACTIVO = 1 " +

            "AND A.I_ACTIVO = 1 " +

            "AND LOWER(TRIM(A.VC_USUARIO_ASIGNADO)) " +
            "    = LOWER(TRIM(:usuario)) " +

            "AND (:folio IS NULL " +
            "     OR S.VC_FOLIO LIKE " +
            "        CONCAT('%', :folio, '%')) " +

            "AND (:nombre IS NULL " +
            "     OR LOWER(S.VC_NOMBRE) LIKE " +
            "        LOWER(CONCAT('%', :nombre, '%'))) " +

            "AND (:telefono IS NULL " +
            "     OR S.VC_TELEFONO_PRINCIPAL LIKE " +
            "        CONCAT('%', :telefono, '%') " +
            "     OR S.VC_TELEFONO_ALTERNO LIKE " +
            "        CONCAT('%', :telefono, '%')) " +

            "AND (:clasificacion IS NULL " +
            "     OR UPPER(S.VC_CLASIFICACION) " +
            "        = UPPER(:clasificacion)) " +

            "AND (:estatus IS NULL " +
            "     OR UPPER(S.VC_ESTATUS) " +
            "        = UPPER(:estatus)) " +

            "AND (:fechaInicio IS NULL " +
            "     OR S.DT_FECHA_REGISTRO >= :fechaInicio) " +

            "AND (:fechaFin IS NULL " +
            "     OR S.DT_FECHA_REGISTRO < :fechaFin) " +

            "ORDER BY S.DT_FECHA_REGISTRO DESC",

        nativeQuery = true
    )
    List<precrmSospechoso>
    buscarSospechososPorUsuario(

        @Param("usuario")
        String usuario,

        @Param("folio")
        String folio,

        @Param("nombre")
        String nombre,

        @Param("telefono")
        String telefono,

        @Param("clasificacion")
        String clasificacion,

        @Param("estatus")
        String estatus,

        @Param("fechaInicio")
        LocalDateTime fechaInicio,

        @Param("fechaFin")
        LocalDateTime fechaFin

    );


    // =====================================================
    // CONTAR SOSPECHOSOS POR ESTATUS Y USUARIO ASIGNADO
    // =====================================================

    @Query(
        value =
            "SELECT COUNT(DISTINCT S.PK_SOSPECHOSO) " +

            "FROM SIB.TBL_PRECRM_SOSPECHOSO S " +

            "INNER JOIN SIB.TBL_PRECRM_ASIGNACION A " +
            "ON A.FK_SOSPECHOSO = S.PK_SOSPECHOSO " +

            "WHERE S.I_ACTIVO = 1 " +

            "AND A.I_ACTIVO = 1 " +

            "AND UPPER(S.VC_ESTATUS) " +
            "    = UPPER(:estatus) " +

            "AND LOWER(TRIM(A.VC_USUARIO_ASIGNADO)) " +
            "    = LOWER(TRIM(:usuario))",

        nativeQuery = true
    )
    long contarSospechososPorEstatusYUsuario(

        @Param("estatus")
        String estatus,

        @Param("usuario")
        String usuario

    );


    // =====================================================
    // VALIDAR QUE EL SOSPECHOSO PERTENEZCA AL USUARIO
    // =====================================================

    @Query(
        value =
            "SELECT COUNT(DISTINCT S.PK_SOSPECHOSO) " +

            "FROM SIB.TBL_PRECRM_SOSPECHOSO S " +

            "INNER JOIN SIB.TBL_PRECRM_ASIGNACION A " +
            "ON A.FK_SOSPECHOSO = S.PK_SOSPECHOSO " +

            "WHERE S.PK_SOSPECHOSO = :pkSospechoso " +

            "AND S.I_ACTIVO = 1 " +

            "AND A.I_ACTIVO = 1 " +

            "AND LOWER(TRIM(A.VC_USUARIO_ASIGNADO)) " +
            "    = LOWER(TRIM(:usuario))",

        nativeQuery = true
    )
    long contarSospechosoPorUsuario(

        @Param("pkSospechoso")
        Long pkSospechoso,

        @Param("usuario")
        String usuario

    );

}