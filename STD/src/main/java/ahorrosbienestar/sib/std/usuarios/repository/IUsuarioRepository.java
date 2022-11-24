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
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO LIKE CONCAT('%',:nombreUsuario,'%')", nativeQuery = true)
	public List<Usuario> findByNombreUsuario(@Param ( "nombreUsuario" ) String nombreUsuario);
	
	//Consulta para obtener el usuario por nombre de usuario
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombreUsuario", nativeQuery = true)
	public List<Usuario> obtenerNombreUsuario(@Param ( "nombreUsuario" ) String nombreUsuario);
	
	//Consulta para activar la sesion de un usuario mediante ID
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_USUARIOS u SET I_ESTATUS_SESION = 0 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioSesion( @Param("id") Integer id);
	
	//Consulta para activar la cuenta de un usuario mediante ID
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_USUARIOS u SET I_ESTATUS_CUENTA = 1 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioCuenta( @Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_USUARIOS u SET I_ESTATUS_USUARIO = 1 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int activarUsuarioEstatus( @Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_USUARIOS u SET I_ESTATUS_USUARIO = 0 WHERE PK_ID_USUARIO = :id", nativeQuery=true)
	public int bajaUsuarioEstatus( @Param("id") Integer id);
	
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.PK_ID_USUARIO = :id", nativeQuery = true)
	public Optional<Usuario> findById(@Param ( "id" ) Integer id);
	
//	@Query(value = "SELECT u.PK_ID_USUARIO,u.VC_NOMBRE_USUARIO, hc.VC_CONTRASENIA \r\n"
//			+ "FROM TBL_CAT_USUARIOS as u \r\n"
//			+ "inner join TBL_CAT_ROL as r \r\n"
//			+ "on u.Rol_idRol = r.PK_ID_ROL \r\n"
//			+ "inner join TBL_HIST_CONTRASENIA as hc \r\n"
//			+ "on hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO \r\n"
//			+ "where hc.VC_INDICADOR_ACTIVO = 1 and  u.VC_NOMBRE_USUARIO = :nombre", nativeQuery = true)
//	public List<Usuario> encontrarUsuarioAndFecha(@Param ( "id" ) String nombre);
	
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = ?1", nativeQuery = true)
	public Usuario findByNombre(String nombre);
	
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombreUsuario", nativeQuery = true)
	public Usuario findByNombreUsuarioBoolean(@Param("nombreUsuario") String nombre);
	
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.I_ID_EMPLEADO = :idEmpleado", nativeQuery = true)
	public Usuario findByIdEmpleadoBoolean(@Param("idEmpleado") Integer idEmpleado);
	
	@Query(value = "SELECT * FROM TBL_CAT_USUARIOS AS u WHERE u.VC_NOMBRE_USUARIO = :nombre", nativeQuery = true)
	public  Optional<Usuario> findByUsuarioNombre(@Param("nombre") String nombre);
	
}
