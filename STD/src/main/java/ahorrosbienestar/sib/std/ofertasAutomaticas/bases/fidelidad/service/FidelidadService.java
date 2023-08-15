package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FidelidadService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public FidelidadService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insertarEnBD(int clienteId, String nombreCliente, String region, String nombreSucursal,
			Date fechaNacimiento, int edad, int creditoId, String nombreProducto, double montoOriginal, int maxDiasMora,
			String telCelular, String telRecados, String telEmpleado, String telReferencia1, String telReferencia2) {
		try {
			System.out.println("ejecutando insertarEnBD");
			String insertQuery = "INSERT INTO SIB.TBL_FIDELIDAD_OA(I_CLIENTE_ID, VC_NOMBRE_COMPLETO_CIENTE, VC_REGION, VC_NOMBRE_SUCURS, DT_FECHA_NACIMIENTO, I_EDAD, I_CREDITO_ID, VC_NOMBRE_PRODUCTO, DEC_MONTO_ORIGINAL, I_MAX_DIA_MORA, VC_TELEFONO_CELULAR, VC_TELEFONO_RECADOS, VC_TELEFONO_EMPLEADO, VC_TELEFONO_REFERENCIA_1, VC_TELEFONO_REFERENCIA_2) VALUES \r\n"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        jdbcTemplate.update(
	                insertQuery,
	                clienteId,
	                nombreCliente,
	                region,
	                nombreSucursal,
	                fechaNacimiento,
	                edad,
	                creditoId,
	                nombreProducto,
	                montoOriginal,
	                maxDiasMora,
	                telCelular,
	                telRecados,
	                telEmpleado,
	                telReferencia1,
	                telReferencia2
	        );
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al insertar en la base de datos");
			e.printStackTrace();
		}
		
	}

}
