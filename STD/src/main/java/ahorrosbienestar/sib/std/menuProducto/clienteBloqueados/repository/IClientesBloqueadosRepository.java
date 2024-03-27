package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model.ClientesBloqueados;

@Repository
public interface IClientesBloqueadosRepository extends JpaRepository<ClientesBloqueados, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_CLIENTES_BLOQUEADOS_AO "
			+ "ORDER BY PK_ID_BLOQUEO DESC ", nativeQuery = true)
	public List<ClientesBloqueados> buscarClientesBloqueados();

}
