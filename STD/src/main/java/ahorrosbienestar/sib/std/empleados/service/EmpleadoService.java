package ahorrosbienestar.sib.std.empleados.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.empleados.dto.EmpleadoDto;

@Service
public class EmpleadoService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public EmpleadoService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Obtenemos el empleado por nombre
	public List<EmpleadoDto> obtenerEmpleado(String nombre) {
		String sql = "SELECT me.empleadoID, me.nombreCompleto, me.fechaAlta, mp.descripcion, me.estatus FROM  CE.TBL_MICROFIN_EMPLEADOS as me inner join CE.TBL_MICROFIN_PUESTOS as mp on me.ClavePuestoID = mp.ClavePuestoID WHERE me.Estatus = 'A' and me.NombreCompleto LIKE CONCAT(? , '%');";
		List<EmpleadoDto> ls = jdbcTemplate.query(sql, new Object[] { nombre }, new RowMapper<EmpleadoDto>() {
			public EmpleadoDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
				EmpleadoDto e = new EmpleadoDto();
				e.setEmpleadoID(rs.getInt(1));
				e.setNombreCompleto(rs.getString(2));
				e.setFechaAlta(rs.getString(3));
				e.setDescripcion(rs.getString(4));
				e.setEstatus(rs.getString(5));

				return e;
			}
		});

		return ls;
	}

	// Buscar empleado por EmpleadoID
	public List<EmpleadoDto> buscarEmpleadoPorID(Integer id) {
		String sql = "SELECT EmpleadoID, NombreCompleto FROM CE.TBL_MICROFIN_EMPLEADOS WHERE EmpleadoID = ?;";
		List<EmpleadoDto> ls = jdbcTemplate.query(sql, new Object[] { id }, new RowMapper<EmpleadoDto>() {
			public EmpleadoDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
				EmpleadoDto e = new EmpleadoDto();
				e.setEmpleadoID(rs.getInt(1));
				e.setNombreCompleto(rs.getString(2));
				return e;
			}
		});

		return ls;
	}

}
