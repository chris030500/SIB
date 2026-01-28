package ahorrosbienestar.sib.std.configuracion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;


@Repository
public interface IConfiguracionRepository extends JpaRepository<Configuracion, Integer> {

	// Consulta para cambiar el valor de la longitud
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :longitud WHERE PK_ID_CONFIGURACION = 1", nativeQuery = true)
	public int cambiarLongitud(@Param("longitud") String longitud);

	// Consulta para cambiar el valor de caracteres
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :caracteres WHERE PK_ID_CONFIGURACION = 2", nativeQuery = true)
	public int cambiarCaracteres(@Param("caracteres") String caracteres);

	// Consulta para cambiar el valor de numeros
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :numeros WHERE PK_ID_CONFIGURACION = 3", nativeQuery = true)
	public int cambiarNumeros(@Param("numeros") String numeros);

	// Consulta para cambiar el valor de minusculas
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :minusculas WHERE PK_ID_CONFIGURACION = 4", nativeQuery = true)
	public int cambiarMinusculas(@Param("minusculas") String minusculas);

	// Consulta para cambiar el valor de minusculas
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :mayusculas WHERE PK_ID_CONFIGURACION = 5", nativeQuery = true)
	public int cambiarMayusculas(@Param("mayusculas") String mayusculas);

	// Consulta para cambiar el valor del periodo de bloqueo
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :pBloqueo WHERE PK_ID_CONFIGURACION = 7", nativeQuery = true)
	public int cambiarPeriodoBloqueo(@Param("pBloqueo") String pBloqueo);

	// Consulta para cambiar el valor del periodo de dias
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :pDias WHERE PK_ID_CONFIGURACION = 8", nativeQuery = true)
	public int cambiarPeriodoDias(@Param("pDias") String pDias);

	// Consulta para cambiar el valor de intentos
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :intentos WHERE PK_ID_CONFIGURACION = 9", nativeQuery = true)
	public int cambiarIntentos(@Param("intentos") String intentos);

	// Consulta para cambiar el valor de la contraseña por default
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CONFIGURACION set VC_VALOR_PROPIEDAD = :contrasena WHERE PK_ID_CONFIGURACION = 6", nativeQuery = true)
	public int cambiarContrasenaDefault(@Param("contrasena") String contrasena);
	
	//Consulta para obtener los parametros de la conexión a la base de datos del core bancario
	@Query(value = "SELECT * FROM TBL_CONFIGURACION "
			+ "where VC_NOM_PROPIEDAD = 'driverClassName' "
			+ "OR VC_NOM_PROPIEDAD = 'url' "
			+ "OR VC_NOM_PROPIEDAD = 'username' "
			+ "OR VC_NOM_PROPIEDAD = 'password';", nativeQuery = true)
	public List<Configuracion> obtenerInfoBDCore();
	
	@Query(value = "SELECT * FROM SIB.TBL_CONFIGURACION WHERE VC_NOM_PROPIEDAD = 'pdf_firma';", nativeQuery = true)
	public List<Configuracion> obtenerUrlPdfFirma();	

	//Consulta para obtener los parametros de la conexión a la base de datos del core bancario
	@Query(value = "SELECT * FROM TBL_CONFIGURACION "
			+ "where VC_NOM_PROPIEDAD like '%jaspersserver_url%' "
			+ "OR VC_NOM_PROPIEDAD like '%jaspersserver_username%' "
			+ "OR VC_NOM_PROPIEDAD like '%jaspersserver_password%'; ", nativeQuery = true)
	public List<Configuracion> obtenerConfReportes();
	
	@Query(value = "SELECT * FROM SIB.TBL_CONFIGURACION WHERE VC_NOM_PROPIEDAD = 'urlCSO';", nativeQuery = true)
	public List<Configuracion> obtenerUrlCSO();
	
	@Query(value = "SELECT VC_VALOR_PROPIEDAD FROM SIB.TBL_CONFIGURACION WHERE VC_NOM_PROPIEDAD =:nomPropiedad", nativeQuery = true)
	public String buscarPropiedad(@Param("nomPropiedad") String nomPropiedad);
	
	@Query(value = "SELECT * FROM SIB.TBL_CONFIGURACION"
			+ " WHERE VC_NOM_PROPIEDAD = :valor", nativeQuery = true)
	public Configuracion valorPropiedad(@Param("valor") String valor);
	
	@Query(value = "SELECT VC_VALOR_PROPIEDAD FROM SIB.TBL_CONFIGURACION WHERE VC_NOM_PROPIEDAD = 'ROLES_VOBO';", nativeQuery = true)
	public String ObtenerRolesVobo();
}
