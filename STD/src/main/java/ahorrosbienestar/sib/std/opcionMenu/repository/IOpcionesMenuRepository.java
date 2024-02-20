package ahorrosbienestar.sib.std.opcionMenu.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import ahorrosbienestar.sib.std.opcionMenu.model.OpcionesMenu;
@Repository
public interface IOpcionesMenuRepository extends JpaRepository<OpcionesMenu, Integer> {


	@Query(value = "SELECT PK_ID_OPCION FROM SIB.TBL_OPCIONES_ROL WHERE VC_NOMBRE_PANTALLA=:nombrepantalla and I_ESTATUS = 1", nativeQuery = true)
	public int buscarRelacion(String nombrepantalla);
	
	@Query(value = "SELECT PK_ID_OPCION FROM SIB.TBL_OPCIONES_ROL WHERE VC_CONTROLLER LIKE CONCAT('%',:nombreController , '%') and I_ESTATUS = 1", nativeQuery = true)
	public int buscarRelacionController(String nombreController);


}

