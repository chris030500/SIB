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
    // OBTENER SOSPECHOSOS ACTIVOS
    // =====================================================

    List<precrmSospechoso>
        findByActivoOrderByFechaRegistroDesc(
            Integer activo
        );


    // =====================================================
    // CONTAR POR ESTATUS
    // =====================================================

    long countByEstatusAndActivo(
        String estatus,
        Integer activo
    );


    // =====================================================
    // BUSQUEDA CON FILTROS
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

}