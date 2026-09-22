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
	// BUSCAR ROL PRE CRM DE UN USUARIO
	// =========================================================

	Optional<precrmUsuarioRol>
	    findByFkUsuario(
	        Integer fkUsuario
	    );


	// =========================================================
	// VALIDAR QUE SEA UN ROL PERMITIDO PARA PRE CRM
	// =========================================================

	@Query(
	    value =
	        "SELECT COUNT(*) " +
	        "FROM SIB.TBL_CAT_ROL R " +
	        "WHERE R.PK_ID_ROL = :fkRol " +
	        "AND R.VC_ESTATUS = 'ACTIVO' " +
	        "AND R.VC_NOMBRE_ROL IN ( " +
	        "   'ROLE_PRECRM_COLABORADOR', " +
	        "   'ROLE_PRECRM_GERENTE_SUCURSAL', " +
	        "   'ROLE_PRECRM_REGIONAL', " +
	        "   'ROLE_PRECRM_GERENTE_COMERCIAL', " +
	        "   'ROLE_RESPONSABLE' " +
	        ")",
	    nativeQuery = true
	)
	long contarRolPrecrmActivo(
	    @Param("fkRol")
	    Integer fkRol
	);
	
	// =========================================================
	// OBTENER ROL PRE CRM POR NOMBRE DE USUARIO
	// =========================================================

	@Query(
	    value =
	        "SELECT R.VC_NOMBRE_ROL " +
	        "FROM SIB.TBL_PRECRM_USUARIO_ROL UR " +
	        "INNER JOIN SIB.TBL_CAT_USUARIOS U " +
	        "    ON U.PK_ID_USUARIO = UR.FK_USUARIO " +
	        "INNER JOIN SIB.TBL_CAT_ROL R " +
	        "    ON R.PK_ID_ROL = UR.FK_ROL " +
	        "WHERE U.VC_NOMBRE_USUARIO = :usuario " +
	        "AND UR.ACTIVO = 1 " +
	        "LIMIT 1",
	    nativeQuery = true
	)
	Optional<String> obtenerRolPrecrmPorUsuario(
	    @Param("usuario")
	    String usuario
	);
}