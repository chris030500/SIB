package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component.ComparadorEncabezadosComponent;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.model.Fidelidad;

@Service
public class FidelidadService {
	@Autowired
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private static final Logger logger = Logger.getLogger(FidelidadService.class);
	

	@Autowired
	public FidelidadService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void registrarFidelidad(List<Fidelidad> listaFidelidad) {
		try {
			logger.info("ejecutando insertarEnBD");
			 String insertQuery = "INSERT INTO SIB.TBL_FIDELIDAD_OA(I_CLIENTE_ID, VC_NOMBRE_COMPLETO_CLIENTE, VC_REGION, VC_NOMBRE_SUCURS, DT_FECHA_NACIMIENTO"
			 		+ ", I_EDAD, I_CREDITO_ID, VC_NOMBRE_PRODUCTO, D_MONTO_ORIGINAL, I_MAX_DIA_MORA, VC_TELEFONO_CELULAR, VC_TELEFONO_RECADOS"
			 		+ ", VC_TELEFONO_EMPLEADO, VC_TELEFONO_REFERENCIA_1, VC_TELEFONO_REFERENCIA_2,DT_FECHA_REGISTRO,VC_PLAZO_OFERTA) VALUES \r\n"
						+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
			 
			 
			 jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
		            public void setValues(java.sql.PreparedStatement preparedStatement, int i) throws SQLException {
		            	Fidelidad regFidelidad = listaFidelidad.get(i);
		            	preparedStatement.setInt(1, regFidelidad.getI_cliente_id());
		            	preparedStatement.setString(2, regFidelidad.getVc_nombre_completo_cliente());
		            	preparedStatement.setString(3, regFidelidad.getVc_region());
		            	preparedStatement.setString(4, regFidelidad.getVc_nombre_sucurs());
		                java.sql.Date fechaNacimiento = new java.sql.Date(regFidelidad.getDt_fecha_nacimiento().getTime());
		                preparedStatement.setDate(5, fechaNacimiento);
		                preparedStatement.setInt(6, regFidelidad.getI_edad());
		                preparedStatement.setLong(7, regFidelidad.getI_credito_id());
		                preparedStatement.setString(8, regFidelidad.getVc_nombre_producto());
		                preparedStatement.setDouble(9, regFidelidad.getD_monto_original());
		                preparedStatement.setInt(10, regFidelidad.getI_max_dia_mora());
		                preparedStatement.setString(11, regFidelidad.getVc_telefono_celular());
		                preparedStatement.setString(12, regFidelidad.getVc_telefono_recados());
		                preparedStatement.setString(13, regFidelidad.getVc_telefono_empleado());
		                preparedStatement.setString(14, regFidelidad.getVc_telefono_referencia_1());
		                preparedStatement.setString(15, regFidelidad.getVc_telefono_referencia_2());
		                java.sql.Date fechaRegistro = new java.sql.Date(regFidelidad.getDt_fecha_registro().getTime());
		                preparedStatement.setDate(16,fechaRegistro);
		                preparedStatement.setString(17,regFidelidad.getVc_plazo_oferta());
		            }

		            @Override
		            public int getBatchSize() {
		                return listaFidelidad.size();
		            }
		        });
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());

		}
	}

	public void insertarEnBD(int clienteId, String nombreCliente, String region, String nombreSucursal,
			Date fechaNacimiento, int edad, int creditoId, String nombreProducto, double montoOriginal, int maxDiasMora,
			String telCelular, String telRecados, String telEmpleado, String telReferencia1, String telReferencia2) {
		try {
			
			String insertQuery = "INSERT INTO SIB.TBL_FIDELIDAD_OA(I_CLIENTE_ID, VC_NOMBRE_COMPLETO_CIENTE, VC_REGION, VC_NOMBRE_SUCURS, DT_FECHA_NACIMIENTO, I_EDAD, I_CREDITO_ID, VC_NOMBRE_PRODUCTO, DEC_MONTO_ORIGINAL, I_MAX_DIA_MORA, VC_TELEFONO_CELULAR, VC_TELEFONO_RECADOS, VC_TELEFONO_EMPLEADO, VC_TELEFONO_REFERENCIA_1, VC_TELEFONO_REFERENCIA_2) VALUES \r\n"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	      
		} catch (Exception e) {
			logger.error("Error al insertar en la base de datos"+e.getMessage());
			e.printStackTrace();
		}
		
	}
}

