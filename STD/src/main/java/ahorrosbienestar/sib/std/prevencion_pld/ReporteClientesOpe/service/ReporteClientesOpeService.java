package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp; 
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;

@Service
public class ReporteClientesOpeService {

    @Autowired
    private ReporteClientesOpeRepository reporteClientesOpeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public List<ReporteClientesOpeModel> getReporteClientesOpe() {
        return reporteClientesOpeRepository.findAll();
    }

    public void guardarRegistro(String nombreArchivo, LocalDateTime fecha, String usuario) {
        jdbcTemplate.update(
            "INSERT INTO SIB.HIST_archivos_pep (nombre_archivo, fecha_subida, usuario) VALUES (?, ?, ?)",
            nombreArchivo, Timestamp.valueOf(fecha), usuario
        );
    }
    public List<Map<String, Object>> obtenerHistorialArchivos() {
        String sql = "SELECT nombre_archivo, fecha_subida, usuario FROM SIB.HIST_archivos_pep ORDER BY fecha_subida DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("nombre", rs.getString("nombre_archivo"));
            item.put("fechaSubida", rs.getTimestamp("fecha_subida").toString());
            item.put("usuario", rs.getString("usuario"));
            item.put("urlDescarga", "/descargarArchivoPEP?nombre=" + rs.getString("nombre_archivo"));
            return item;
        });
    }
}


