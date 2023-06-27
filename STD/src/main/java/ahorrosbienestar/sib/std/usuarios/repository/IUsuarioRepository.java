package ahorrosbienestar.sib.std.usuarios.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	//@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombreUsuario", nativeQuery = true)
	//Consulta para obtener usuarios que lo conforme por el parametro
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO LIKE CONCAT('%',:nombreUsuario,'%')", nativeQuery = true)
	public List<Usuario> findByNombreUsuario(@Param ( "nombreUsuario" ) String nombreUsuario);
	
	//Consulta para obtener el usuario por nombre de usuario
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombreUsuario", nativeQuery = true)
	public List<Usuario> obtenerNombreUsuario(@Param ( "nombreUsuario" ) String nombreUsuario);
	
	//Consulta para activar la sesion de un usuario mediante ID
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS u SET I_ESTATUS_SESION = 0 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioSesion( @Param("id") Integer id);
	
	//Consulta para activar la cuenta de un usuario mediante ID
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS u SET I_ESTATUS_CUENTA = 1 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioCuenta( @Param("id") Integer id);
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS u SET I_ESTATUS_USUARIO = 1 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioEstatus( @Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS u SET I_ESTATUS_USUARIO = 0 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int bajaUsuarioEstatus( @Param("id") Integer id);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u \r\n"
			+ "INNER JOIN SIB.TBL_HIST_CONTRASENIA AS contra ON u.PK_ID_USUARIO = contra.Usr_historico_contrasena_id\r\n"
			+ "WHERE u.PK_ID_USUARIO = :id and contra.VC_INDICADOR_ACTIVO = 1", nativeQuery = true)
	public Optional<Usuario> findById(@Param ( "id" ) Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS set VC_NOMBRE_USUARIO = :usuario WHERE PK_ID_USUARIO = :idUsuario", nativeQuery=true)
	public int cambiarNombreUsuario( @Param("usuario") String usuario, @Param("idUsuario") Integer idUsuario);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = ?1", nativeQuery = true)
	public Usuario findByNombre(String nombre);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombreUsuario", nativeQuery = true)
	public Usuario findByNombreUsuarioBoolean(@Param("nombreUsuario") String nombre);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.I_ID_EMPLEADO = :idEmpleado", nativeQuery = true)
	public Usuario findByIdEmpleadoBoolean(@Param("idEmpleado") Integer idEmpleado);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombre", nativeQuery = true)
	public  Optional<Usuario> findByUsuarioNombre(@Param("nombre") String nombre);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_CAT_USUARIOS SET I_ESTATUS_SESION = 0 WHERE PK_ID_USUARIO  = :usuario", nativeQuery=true)
	public int destruirSesion(@Param("usuario") int usuario);
	
	@Modifying
	@Transactional
    @Query(value = "UPDATE SIB.TBL_CAT_USUARIOS SET I_ESTATUS_SESION = 0 WHERE PK_ID_USUARIO > 0 AND I_ESTATUS_SESION = 1", nativeQuery = true)
    public int destruirSesionUsuario();
	
	
	
}
