package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.MensajeSMS;

import org.springframework.data.repository.query.Param;

@Repository
public interface IMensajeSMSRepository extends JpaRepository<MensajeSMS, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_MENSAJES_SMS_AO SET I_ESTATUS= 2, FK_ID_PROCES_SMS = :idProce WHERE PK_ID_MENSAJE IN :idsMensajes", nativeQuery = true)
	int actualizarEstadoMensajes(@Param("idProce") int idProce, @Param("idsMensajes") List<Integer> idsMensajes);

}
