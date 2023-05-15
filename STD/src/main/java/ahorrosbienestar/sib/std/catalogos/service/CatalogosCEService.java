package ahorrosbienestar.sib.std.catalogos.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.dto.EmpleadosCE;
import ahorrosbienestar.sib.std.catalogos.dto.ListaAreas;
import ahorrosbienestar.sib.std.catalogos.dto.ListaFolioXEmpleado;
import ahorrosbienestar.sib.std.catalogos.dto.ListaPuestos;
import ahorrosbienestar.sib.std.catalogos.dto.SeguimientoActividadPA;

@Service
public class CatalogosCEService {

	private JdbcTemplate jdbcTemplate;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public CatalogosCEService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	// Obtenemos los empleados activos
		public List<EmpleadosCE> obtenerEmpleado(){
			logger.info("Ejecutando listaEmpleados: ");
			try {
				String sql = "SELECT EmpleadoID, CONCAT_WS(' ',PrimerNombre,SegundoNombre,ApellidoPat, ApellidoMat) as nombreEmpleado"
						+ " FROM CE.TBL_MICROFIN_EMPLEADOS WHERE Estatus = 'A' and EsJefe = 'S';";
				List<EmpleadosCE> ls = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<EmpleadosCE>() {
					public EmpleadosCE mapRow(ResultSet rs, final int rowNum) throws SQLException {
						EmpleadosCE e = new EmpleadosCE();
						e.setEmpleadoID(rs.getInt(1));
						e.setNombreCompleto(rs.getString(2));
					
						return e;
					}
				});
				logger.info(ls.get(0).getNombreCompleto());
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
			}
			return null;
		}
		
		// Obtenemos las areas de Bienestar
		public List<ListaAreas> obtenerAreas(){
			logger.info("Ejecutando lista Areas: ");
			try {
				String sql = "select PK_ID_AREA, VC_DESCRIPCION from CE.TBL_AREA;";
				List<ListaAreas> ls = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<ListaAreas>() {
					public ListaAreas mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ListaAreas e = new ListaAreas();
						e.setAreaID(rs.getInt(1));
						e.setDescripcionArea(rs.getString(2));
						return e;
					}
				});
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
			}
			return null;
		}

		// Obtenemos los puestos
		public List<ListaPuestos> obtenerPuesto(Integer empleadoID){
			logger.info("Ejecutando lista puestos: ");
			try {
				String sql = "select p.PK_ID_PUESTO AS puestoID, p.VC_DESCRIPCION as nombrePuesto  from CE.TBL_PUESTO p"
						+ " inner join CE.TBL_MICROFIN_EMPLEADOS emp on emp.ClavePuestoID = p.ClavePuestoID"
						+ " where emp.EmpleadoID = ?;";
				@SuppressWarnings("deprecation")
				List<ListaPuestos> ls = jdbcTemplate.query(sql, new Object[] {empleadoID}, new RowMapper<ListaPuestos>() {
					public ListaPuestos mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ListaPuestos e = new ListaPuestos();
						e.setPuestoID(rs.getInt(1));
						e.setDescripcionPuesto(rs.getString(2));
						return e;
					}
				});
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
			}
			return null;
		}
		/**
		 * 
		 * @param areaID
		 * @return
		 */

		public List<EmpleadosCE> busquedaxArea(Integer areaID) {
			logger.info("Ejecutando lista empleados por area: ");
			try {
				String sql = "select emp.EmpleadoID, CONCAT_WS(' ',emp.PrimerNombre,emp.SegundoNombre,emp.ApellidoPat, emp.ApellidoMat) as nombreEmpleado "
						+ " from CE.TBL_AREA a "
						+ " inner join CE.TBL_PUESTO p on p.areaID = a.pk_id_area and p.i_id_estatus = 1"
						+ " inner join CE.TBL_MICROFIN_EMPLEADOS emp on emp.ClavePuestoID = p.ClavePuestoID and emp.Estatus = 'A'"
						+ " where a.pk_id_area = ? and a.i_id_estatus = 1"
						+ " order by emp.JerarquiaID desc;";
				System.out.print("sql"+sql);
				List<EmpleadosCE> ls = jdbcTemplate.query(sql, new Object[] {areaID}, new RowMapper<EmpleadosCE>() {
					public EmpleadosCE mapRow(ResultSet rs, final int rowNum) throws SQLException {
						EmpleadosCE e = new EmpleadosCE();
						e.setEmpleadoID(rs.getInt(1));
						e.setNombreCompleto(rs.getString(2));
					
						return e;
					}
				});
				logger.info(ls.get(0).getNombreCompleto());
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
			}
			return null;
		}
		/**
		 * 
		 * @param areaID
		 * @return
		 */
		public List<ListaFolioXEmpleado> listaFolioXEmpleado(Integer idEmpleado) {
			logger.info("Ejecutando lista Folio por Empleado: ");
			try {
				String sql = "SELECT distinct t2.I_FOLIO, t2.PK_ID_FOLIO FROM SIB.TBL_OBSERVACIONESXFOLIO t1 "
						+ " inner join SIB.TBL_FOLIOS t2 on t2.PK_ID_FOLIO = t1.I_FOLIO "
						+ " where t1.I_RESPONSABLE = ? and t1.I_CONFIRMA IN (1,0);";
				List<ListaFolioXEmpleado> ls = jdbcTemplate.query(sql, new Object[] {idEmpleado}, new RowMapper<ListaFolioXEmpleado>() {
					public ListaFolioXEmpleado mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ListaFolioXEmpleado e = new ListaFolioXEmpleado();
						e.setFolio(rs.getInt(1));
						e.setPkFolio(rs.getInt(2));

						return e;
					}
				});
				logger.info(ls.get(0).getFolio());
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
			}
			return null;
		}
		/**
		 * 
		 * @param areaID
		 * @return
		 */
		public List<EmpleadosCE> busquedaxAreaEmpleado(Integer empleadoID) {
			logger.info("Ejecutando lista empleados por area: ");
			try {
				String sql = "select emp.EmpleadoID, CONCAT_WS(' ',emp.PrimerNombre,emp.SegundoNombre,emp.ApellidoPat, emp.ApellidoMat) as nombreEmpleado" 
						+" from CE.TBL_AREA a "
						+" inner join CE.TBL_PUESTO p on p.areaID = a.pk_id_area and p.i_id_estatus = 1 "
						+" inner join CE.TBL_MICROFIN_EMPLEADOS emp on emp.ClavePuestoID = p.ClavePuestoID and emp.Estatus = 'A' "
						+" inner join (select t2.AreaID from CE.TBL_MICROFIN_EMPLEADOS t1 "
						+" inner join CE.TBL_PUESTO t2 on t1.ClavePuestoID = t2.ClavePuestoID "
						+" where t1.EmpleadoID = ?) t3 on t3.areaID =  a.pk_id_area "
						+" where a.i_id_estatus = 1 "
						+" order by emp.JerarquiaID desc;";
				System.out.print("sql"+sql);
				List<EmpleadosCE> ls = jdbcTemplate.query(sql, new Object[] {empleadoID}, new RowMapper<EmpleadosCE>() {
					public EmpleadosCE mapRow(ResultSet rs, final int rowNum) throws SQLException {
						EmpleadosCE e = new EmpleadosCE();
						e.setEmpleadoID(rs.getInt(1));
						e.setNombreCompleto(rs.getString(2));
					
						return e;
					}
				});
				logger.info(ls.get(0).getNombreCompleto());
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
				return null;
			}
			
		}
		
		/**
		 * 
		 * @param areaID
		 * @return
		 */
		public List<ActividadesPA> buscarActividadesxEmpleado(Integer i_folio, Integer empleadoID) {
			logger.info("Ejecutando lista empleados por area: ");
			try {
				String sql = "select t1.PK_ID_PLANACCION "
						+ ",t1.VC_DESC_ACTIVIDAD "
						+ ",CONCAT_WS(' ',t3.PrimerNombre,t3.SegundoNombre,t3.ApellidoPat, t3.ApellidoMat) as nombreEmpleado "
						+ ",t1.DT_FECHA_INICIO "
						+ ",t1.DT_FECHA_VENCIMIENTO "
						+ ",t4.V_VALOR AS ESTATUS "
						+ "from SIB.TBL_PLAN_ACCION t1 "
						+ "inner join SIB.TBL_OBSERVACIONESXFOLIO t2 on t1.I_OBSERVACION = t2.PK_ID_OBSERVACION and t2.I_CONFIRMA = 1 "
						+ "inner join CE.TBL_MICROFIN_EMPLEADOS t3 on t1.I_RESPONSABLE = t3.EmpleadoID "
						+ "inner join SIB.TBL_OPC_BUSQUEDA t4 on t1.I_ESTATUS = t4.I_OPCION and t4.I_ID_GRUPO = 6 "
						+ "where I_FOLIO = ? and t1.I_RESPONSABLE = ? "
						+ "order by t1.DT_FECHA_INICIO asc; ";
				System.out.print("sql"+sql);
				List<ActividadesPA> ls = jdbcTemplate.query(sql, new Object[] {i_folio,empleadoID}, new RowMapper<ActividadesPA>() {
					public ActividadesPA mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ActividadesPA e = new ActividadesPA();
						
						e.setId_planaccion(rs.getInt(1));
						e.setNombreActividad(rs.getString(2));
						e.setNombreResponsable(rs.getString(3));
						e.setDtFechaInicio(rs.getString(4));
						e.setDtFechaVencimiento(rs.getString(5));
						e.setEstatus(rs.getString(6));
						
						return e;
					}
				});
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
				return null;
			}
		
		}
		
		/**
		 * 
		 * @param areaID
		 * @return
		 */
		public List<SeguimientoActividadPA> obtSeguimientoxActividad(Integer i_actividad) {
			logger.info("Ejecutando Busqueda de seguimiento: ");
			try {
				String sql = "SELECT t2.PK_ID_SEGUIMIENTO "
						+ ",t1.VC_DESC_ACTIVIDAD "
						+ ",t2.VC_DESC_SEGUIMIENTO "
						+ ",t2.DT_FECHA_REGISTRO "
						+ ",case t2.I_CONFIRMACION_RESPONSABLE "
						+ "when 1 then 'SI' "
						+ "when 2 then 'NO' END ESTATUS "
						+ "FROM SIB.TBL_PLAN_ACCION t1 "
						+ "inner join SIB.TBL_SEGUIMIENTO t2 on t1.PK_ID_PLANACCION = t2.I_PLANACCION "
						+ "WHERE t1.PK_ID_PLANACCION = ?;";
				System.out.print("sql"+sql);
				List<SeguimientoActividadPA> ls = jdbcTemplate.query(sql, new Object[] {i_actividad}, new RowMapper<SeguimientoActividadPA>() {
					public SeguimientoActividadPA mapRow(ResultSet rs, final int rowNum) throws SQLException {
						SeguimientoActividadPA e = new SeguimientoActividadPA();
						
						e.setId_seguimiento(rs.getInt(1));
						e.setNombreActividad(rs.getString(2));
						e.setDesc_actividad(rs.getString(3));
						e.setDtFechaRegistro(rs.getString(4));
						e.setEstatus(rs.getString(5));
	
						return e;
					}
				});
				return ls;
			} catch (Exception e) {
				System.out.println("e"+e.getMessage());
				return null;
			}
		
		}
}
