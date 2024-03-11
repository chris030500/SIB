package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.ListaSeguimientoModel;

@Repository
public interface ListaSeguimientoRepository extends JpaRepository<ListaSeguimientoModel, Integer> {

	@Query(value = "select I_CLIENTEID "
			+ ",PK_ID_ASIGNACION_OA "
			+ ",VC_NOMBRECOMPLETO as nombreCompleto "
			+ ",C_Estatus "
			+ ",VC_CODIGO_OFERTA "
			+ ",VC_COD_REFERENCIA "
			+ ",FECHAINICIO "
			+ ",FECHAFIN "
			+ ",i_estatus "
			+ ",V_VALOR "
			+ ",FK_ID_SUCURSAL "
			+ ",VC_NOMBRESUCURSAL "
			+ "FROM SIB.VW_ASIGNACION_OFERTAS ", nativeQuery = true)
	public List<ListaSeguimientoModel> listaSeguimiento();
	
	
	@Query(value = "SELECT I_CLIENTEID"
			+ " ,PK_ID_ASIGNACION_OA"
			+ " ,VC_NOMBRECOMPLETO as nombreCompleto"
			+ " ,VC_CODIGO_OFERTA"
			+ " ,VC_COD_REFERENCIA"
			+ " ,FECHAINICIO"
			+ " ,FECHAFIN"
			+ " ,i_estatus"
			+ " ,V_VALOR"
			+ " ,FK_ID_SUCURSAL"
			+ " ,VC_NOMBRESUCURSAL"
			+ " ,I_BLOQUEO"
			+ " ,VC_NOMBRE_USUARIO"
			+ " FROM SIB.VW_ASIGNACION_OFERTAS"
			+ " WHERE VC_NOMBRECOMPLETO = :nombre"
			+ " OR VC_CODIGO_OFERTA = :oferta"
			+ " OR VC_COD_REFERENCIA = :referencia"
			+ " OR I_CLIENTEID = :cliente", nativeQuery = true)
	public List<ListaSeguimientoModel> busquedaPorSeguimiento(@Param("nombre") String nombre, 
			@Param("oferta") String oferta, 
			@Param("referencia") String referencia, 
			@Param("cliente") String cliente);
	
}
