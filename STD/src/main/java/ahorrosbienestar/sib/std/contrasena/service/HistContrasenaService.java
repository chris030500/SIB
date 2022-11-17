package ahorrosbienestar.sib.std.contrasena.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contrasena.dto.HistContrasenaDto;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.repository.IHistContrasenaRepository;
import ahorrosbienestar.sib.std.empleados.dto.EmpleadoDto;

@Service
public class HistContrasenaService {

	private IHistContrasenaRepository iHistContrasenaRepository;

//	private JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public HistContrasenaService(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}

	@Autowired
	public HistContrasenaService(IHistContrasenaRepository iHistContrasenaRepository) {
		this.iHistContrasenaRepository = iHistContrasenaRepository;
	}

	// Metodo para dar de alta Historico Contraseña
	public HistContrasena save(HistContrasena iHistContrasena) {
		return iHistContrasenaRepository.save(iHistContrasena);
	}

	// Metodo paraa buscar por id de Historico Contraseña
	public Optional<HistContrasena> findById(Integer id) {
		return iHistContrasenaRepository.findById(id);
	}

	// Metodo para buscar todos los Historico Contraseña
	public List<HistContrasena> findAll() {
		return iHistContrasenaRepository.findAll();
	}

	// Metodo para editar Historico Contraseña
	public HistContrasena edit(HistContrasena histContrasena) {
		return iHistContrasenaRepository.save(histContrasena);
	}
	
	//Obtenemos el empleado por nombre
//		public List<HistContrasenaDto> obtenerUsuarioAndFecha(String nombre) {
//		      String sql = "SELECT u.PK_ID_USUARIO, u.VC_NOMBRE_USUARIO, hc.VC_CONTRASENIA, CASE WHEN hc.DT_VIG_CONT_FIN = current_date() THEN 1 ELSE 0 END AS FECHA\r\n"
//		      		+ "FROM TBL_CAT_USUARIOS as u\r\n"
//		      		+ "inner join TBL_CAT_ROL as r\r\n"
//		      		+ "on (u.Rol_idRol = r.PK_ID_ROL)\r\n"
//		      		+ "inner join TBL_HIST_CONTRASENIA as hc\r\n"
//		      		+ "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
//		      		+ "where hc.VC_INDICADOR_ACTIVO = 1 and  u.VC_NOMBRE_USUARIO = ?;";
//		      List<HistContrasenaDto> ls = jdbcTemplate.query(sql, new Object[] {nombre},
//		              new RowMapper<HistContrasenaDto>() {
//		                  public HistContrasenaDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
//		                	  HistContrasenaDto e = new HistContrasenaDto();
//		                	  e.setPk_id_usuario(rs.getInt(1));
//								e.setVc_nombre_usuario(rs.getString(2));
//								e.setVc_contrasenia(rs.getString(3));
//								e.setValorFecha(rs.getInt(4));
//
//		                      return e;
//		                  }
//		              });
//		      
//		      return ls;
//		  }

}
