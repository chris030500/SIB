package ahorrosbienestar.sib.std.cat_catalogos.opc_busqueda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.cat_catalogos.opc_busqueda.model.OpcBusquedaModel;

@Repository
public interface OpcBusquedaRepository extends JpaRepository<OpcBusquedaModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_OPC_BUSQUEDA WHERE I_ID_GRUPO = :id", nativeQuery = true)
	public List<OpcBusquedaModel> busquedaPorGrupo(@Param("id") int id);
	
	@Query(value = "SELECT * FROM SIB.TBL_OPC_BUSQUEDA WHERE PK_ID = :id", nativeQuery = true)
	public OpcBusquedaModel buscarPorId(@Param("id") int id);

}
