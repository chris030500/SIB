package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.NumClienteModel;

@Repository
public interface NumClienteRepository extends JpaRepository<NumClienteModel, Integer> {

	@Query(value = "SELECT I_ClienteID, CONCAT(I_ClienteID, ' - ', VC_NombreCompleto) AS ClienteInfo"
			+ " FROM SIB.TBL_CLIENTES_ACTIVOS"
			+ " WHERE CONCAT(I_ClienteID, ' - ', VC_NombreCompleto) LIKE %:valor%", nativeQuery = true)
	public List<NumClienteModel> listaClientes(@Param("valor") String valor);

}
