package ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.model.TelefonoModel;

@Repository
public interface TelefonoRepository extends JpaRepository<TelefonoModel, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_TELEFONOS_SUCURSAL_OA WHERE PK_ID_TELEFONO = :idTelefono", nativeQuery = true)
	TelefonoModel buscarTelefonoPorId(@Param("idTelefono") int idTelefono);

}
