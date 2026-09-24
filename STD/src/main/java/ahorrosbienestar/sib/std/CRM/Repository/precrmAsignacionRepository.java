package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmAsignacion;


@Repository
public interface precrmAsignacionRepository
        extends JpaRepository<precrmAsignacion, Long> {


    // =========================================================
    // OBTENER USUARIO SIB A PARTIR DEL NOMBRE DEL COLABORADOR
    // =========================================================

	@Query(
		    value =
		        "SELECT TRIM(U.VC_USUARIO) " +
		        "FROM SIB.TBL_USUARIOS U " +
		        "WHERE U.CH_ACTIVO = 1 " +
		        "AND UPPER(" +
		        "    REGEXP_REPLACE(TRIM(U.VC_NOMBRE), '[[:space:]]+', ' ')" +
		        ") = UPPER(" +
		        "    REGEXP_REPLACE(TRIM(:nombre), '[[:space:]]+', ' ')" +
		        ") " +
		        "LIMIT 1",
		    nativeQuery = true
		)
		Optional<String> obtenerUsuarioActivoPorNombre(
		    @Param("nombre")
		    String nombre
		);


    // =========================================================
    // OBTENER ASIGNACION ACTIVA DE UN SOSPECHOSO
    // =========================================================

    Optional<precrmAsignacion>
        findTopByFkSospechosoAndActivoOrderByFechaAsignacionDesc(
            Long fkSospechoso,
            Integer activo
        );


    // =========================================================
    // VALIDAR SI YA EXISTE LA MISMA ASIGNACION ACTIVA
    // =========================================================

    boolean existsByFkSospechosoAndUsuarioAsignadoAndActivo(
        Long fkSospechoso,
        String usuarioAsignado,
        Integer activo
    );

}