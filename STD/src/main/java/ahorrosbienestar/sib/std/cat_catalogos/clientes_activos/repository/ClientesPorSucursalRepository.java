package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesPorSucursal;

@Repository
public interface ClientesPorSucursalRepository extends JpaRepository<ClientesPorSucursal, Integer>{

	@Query(value = "select I_CLIENTEID,VC_NOMBRECOMPLETO,C_Estatus "
			+ "FROM SIB.VW_ASIGNACION_OFERTAS "
			+ "WHERE FK_ID_SUCURSAL =:idSucursal ", nativeQuery = true)
	public List<ClientesPorSucursal> listaClientesFiltro(@Param("idSucursal") int idSucursal);

}
