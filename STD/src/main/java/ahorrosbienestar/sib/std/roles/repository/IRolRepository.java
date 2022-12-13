package ahorrosbienestar.sib.std.roles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.roles.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
	
	//Consulta para trae el id_rol y nombre del rol
	@Query(value = "SELECT r.PK_ID_ROL, r.VC_NOMBRE_ROL FROM TBL_CAT_ROL as r WHERE r.PK_ID_ROL = ?1", nativeQuery = true)
	public Rol findByIdRol(Integer rol);

}
