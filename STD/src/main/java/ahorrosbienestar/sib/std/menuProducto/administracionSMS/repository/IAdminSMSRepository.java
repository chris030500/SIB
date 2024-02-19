package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.adminSMS;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface IAdminSMSRepository extends JpaRepository<adminSMS, Integer> {
	@Query(value = "SELECT I_CLIENTEID, VC_NOMBRECOMPLETO, VC_NOMBRE_BASE, VC_ABREVIACION_CLASIFICACION, VC_TELEFONO_CELULAR, VC_DESC_MENSAJE, i_estatus FROM VW_ASIGNACION_OFERTAS LIMIT 100", nativeQuery = true)
	List<adminSMS> obtenerMensajess();
	
	@Query(value = "SELECT PK_ID_MENSAJE, I_CLIENTEID, VC_NOMBRECOMPLETO, VC_NOMBRE_BASE, VC_ABREVIACION_CLASIFICACION, "
			+ "VC_TELEFONO_CELULAR, VC_DESC_MENSAJE, I_ESTATUS_SMS "
			+ "FROM VW_ASIGNACION_OFERTAS "
			+ "WHERE VC_NOMBRECOMPLETO LIKE %:cliente% "
			+ "AND PK_ID_PROCESO LIKE %:proce% "
			+ "AND VC_NOMBRESUCURSAL LIKE %:sucur%", nativeQuery = true)
	public List<adminSMS> obtenerMensajes(
			@Param("cliente") String cliente, 
			@Param("sucur") String sucur, 
			@Param("proce") String proce
	);
}
