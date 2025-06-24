package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class DetallePEPsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Se ejecuta cada vez que se sube un archivo.
     */
    public void procesarCargaConUsuario(MultipartFile file, int idUsuario) throws Exception {
        //  Desactivar procesos anteriores (estatus 0)
        desactivarProcesosAnteriores();

        //  Insertar nuevo proceso activo
        int idProceso = registrarNuevoProceso(idUsuario);

        //  Parsear archivo Excel
        List<DetallePEPsModel> registros = ExcelPEPReader.parse(file);
        if (registros.isEmpty()) return;

        //  Insertar todos los registros (no importa si ya existían)
        String sql = """
            INSERT INTO SIB.TBL_DETALLE_PEPS (FK_PROCESO, I_CLIENTEID, VC_NOMBRE_CLIENTE)
            VALUES (?, ?, ?)
        """;

        for (DetallePEPsModel r : registros) {
            jdbcTemplate.update(sql, idProceso, r.getI_clienteid(), r.getVc_nombre_cliente());
        }

        System.out.println(" Proceso " + idProceso + " (usuario " + idUsuario + "): insertados " + registros.size() + " registros.");
    }

    private void desactivarProcesosAnteriores() {
        jdbcTemplate.update("UPDATE SIB.TBL_PROCESOS_PEPS SET I_ESTATUS = 0 WHERE I_ESTATUS = 1");
    }

    private int registrarNuevoProceso(int idUsuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO SIB.TBL_PROCESOS_PEPS (I_USER_REG, I_ESTATUS) VALUES (?, 1)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUsuario);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
