package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglas;


@Repository
public interface IParametrosReglaRepository extends JpaRepository<ParametrosReglas, Integer> {

	
	@Query(value = "SELECT ra.PK_PARMS_REGLA,ra.FK_ID_REGLA,p.v_valor as tipoCartera,tc.v_valor as tipoCliente,ag.v_valor as asignadoA "
			+ ",ra.I_PRIORIDAD,ur.VC_NOMBRE_USUARIO,ra.dt_fecha_registro,ur2.VC_NOMBRE_USUARIO,ra.DT_FECHA_MOD,e.v_valor as estatus "
			+ "FROM SIB.TBL_PARAMS_REGLAS_ASIGNACION ra "
			+ "LEFT JOIN SIB.TBL_CAT_USUARIOS ur on ur.PK_ID_USUARIO = ra.I_USUARIO_REGISTRO "
			+ "LEFT JOIN SIB.TBL_CAT_USUARIOS ur2 on ur2.PK_ID_USUARIO = ra.I_USUARIO_MOD "
			+ "LEFT JOIN SIB.TBL_OPC_BUSQUEDA p on p.I_OPCION = ra.I_TIPO_CARTERA and p.I_ID_GRUPO = 19 "
			+ "LEFT JOIN SIB.TBL_OPC_BUSQUEDA tc on tc.I_OPCION = ra.I_TIPO_CLIENTE and tc.I_ID_GRUPO = 18 "
			+ "LEFT JOIN SIB.TBL_OPC_BUSQUEDA ag on ag.I_OPCION = ra.I_TIPO_ASIGNADO and ag.I_ID_GRUPO = 21 "
			+ "LEFT JOIN SIB.TBL_OPC_BUSQUEDA e on e.I_OPCION = ra.I_ESTATUS and e.I_ID_GRUPO = 16 "
			+ "WHERE ra.FK_ID_REGLA=:idRegla ", nativeQuery = true) 
	public List<ParametrosReglas> obtParametros(@Param("idRegla") Integer idRegla);
}
