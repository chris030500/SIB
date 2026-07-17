package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;

@Repository
public interface ClientesActivosRepository extends JpaRepository<ClientesActivosModel, Integer> {

 
	@Query(value = "SELECT * FROM SIB.TBL_CLIENTES_ACTIVOS "
	        + "WHERE VC_NombreCompleto LIKE CONCAT('%', :valor, '%')",
	        nativeQuery = true)
	public List<ClientesActivosModel> listaClientes(
	        @Param("valor") String valor);
 
}

