package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.adminSMS;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface IAdminSMSRepository extends JpaRepository<adminSMS, Integer> {
	@Query(value = " SELECT I_CLIENTEID, VC_NOMBRECOMPLETO, VC_NOMBRE_BASE, VC_ABREVIACION_CLASIFICACION, VC_TELEFONO_CELULAR, VC_DESC_MENSAJE, i_estatus FROM VW_ASIGNACION_OFERTAS LIMIT 100", nativeQuery = true)
	List<adminSMS> obtenerMensajes();
}
