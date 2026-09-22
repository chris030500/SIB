package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmUsuarioRol;


@Repository
public interface precrmUsuarioRolRepository
        extends JpaRepository<precrmUsuarioRol, Long> {


    // =========================================================
    // OBTENER ROL PRE CRM POR USUARIO
    // =========================================================

    @Query(
        value =
            "SELECT R.VC_NOMBRE_ROL " +
            "FROM SIB.TBL_PRECRM_USUARIO_ROL UR " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS U " +
            "   ON U.PK_ID_USUARIO = UR.FK_USUARIO " +
            "INNER JOIN SIB.TBL_CAT_ROL R " +
            "   ON R.PK_ID_ROL = UR.FK_ROL " +
            "WHERE U.VC_NOMBRE_USUARIO = :usuario " +
            "AND UR.ACTIVO = 1 " +
            "AND U.I_ESTATUS_USUARIO = 1 " +
            "AND R.VC_ESTATUS = 'ACTIVO' " +
            "LIMIT 1",
        nativeQuery = true
    )
    Optional<String> obtenerRolPrecrmPorUsuario(
        @Param("usuario")
        String usuario
    );
}