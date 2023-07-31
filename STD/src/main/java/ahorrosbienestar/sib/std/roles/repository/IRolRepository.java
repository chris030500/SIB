package ahorrosbienestar.sib.std.roles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.roles.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
	
	//Consulta para trae el id_rol y nombre del rol
	@Query(value = "SELECT r.PK_ID_ROL, r.VC_NOMBRE_ROL FROM TBL_CAT_ROL as r WHERE r.PK_ID_ROL = ?", nativeQuery = true)
	public Rol findByIdRol(Integer rol);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM TBL_CAT_ROL  WHERE TRIM(VC_NOMBRE_ROL) = :nombreRol ", nativeQuery = true)
	public int existeRol(@Param("nombreRol") String nombreRol);

	@Modifying
	@Transactional
	@Query(value = "UPDATE  TBL_CAT_ROL SET VC_ESTATUS = :estatus WHERE PK_ID_ROL = :idRol", nativeQuery = true)
	public int updateEstatus(@Param("idRol") Integer idRol,@Param("estatus") String estatus);

	@Modifying
	@Transactional
	@Query(value = "UPDATE  TBL_CAT_ROL SET VC_NOMBRE_ROL= :nombreRol, VC_DESCRIPCION_ROL= :descripcionRol WHERE PK_ID_ROL= :idRol", nativeQuery = true)
	public int modificarRoles(@Param("idRol") Integer idRol,@Param("nombreRol") String nombreRol, @Param("descripcionRol") String descripcionRol);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_ROL WHERE VC_ESTATUS = 'ACTIVO'", nativeQuery = true)
	public List<Rol> findByRolActivo();

}
