package ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.model.HistorialModel;

@Repository
public interface HistorialRepository extends JpaRepository<HistorialModel, Integer> {

	@Query(value = "SELECT cn.PK_ID_CONSULTA_RP,"
			+ "	cu.PK_ID_USUARIO,"
			+ " cn.DT_FECHA_REGISTRO,"
			+ " cn.VC_PARAMETRO_BUSQUEDA,"
			+ " cn.VC_VALOR_PARAMETRO,"
			+ " cn.I_NUMERO_CLIENTE,"
			+ " cn.VC_NOMBRE_CLIENTE,"
			+ " cn.I_NUMERO_CUENTA_PRINCIPAL_1,"
			+ " cn.I_NUMERO_CUENTA_PRINCIPAL_2,"
			+ " bt.I_NUMERO_CREDITO,"
			+ " bt.VC_PRODUCTO_CREDITO,"
			+ " bt.I_REFERENCIA_BANCO_CREDITO,"
			+ " bt.VC_NOMBRE_BANCO_CREDITO"
			+ " FROM TBL_CONSULTA_RP cn"
			+ " INNER JOIN TBL_BITACORA_RP bt ON bt.FK_ID_CONSULTA_RP = cn.PK_ID_CONSULTA_RP"
			+ " INNER JOIN TBL_CAT_USUARIOS cu ON cu.PK_ID_USUARIO = cn.FK_USUARIO_REGISTRO"
			+ " order by cn.PK_ID_CONSULTA_RP DESC", nativeQuery = true)
	public List<HistorialModel> historialDeBusqueda();
	
	
}
