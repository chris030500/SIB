package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("gestion_vobo_nomina")
public class VoboNominaDashboardController {

    private static final Logger logger = Logger.getLogger(VoboNominaDashboardController.class);
    private static final String rutaController = "/gestion_vobo_nomina/dashboard";
    private static final String rutaError = "errores/error_403";

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private IUsuarioRepository iUsuarioRepository;
    @Autowired private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    @Autowired private OpcionesMenuService opcionesMenuService;
    private static final int SLA_DIAS = 3;

    @GetMapping("/dashboard")
    public String dashboard() {
        UserDetails userDetails = currentUserDetails();
        if (userDetails == null || validarAcceso(userDetails.getUsername()) != 1) {
            return rutaError;
        }
        return "voboNomina/dashboard";
    }

    @GetMapping("/dashboard/metrics")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> metrics(
        @RequestParam(name = "fechaInicio", required = false) String fechaInicio,
        @RequestParam(name = "fechaFin", required = false) String fechaFin
    ) {
        Map<String, Object> payload = new HashMap<>();
        LocalDate inicio = parseFecha(fechaInicio, LocalDate.now().minusMonths(12));
        LocalDate fin = parseFecha(fechaFin, LocalDate.now());
        if (inicio.isAfter(fin)) {
            LocalDate tmp = inicio;
            inicio = fin;
            fin = tmp;
        }
        LocalDate finExclusive = fin.plusDays(1);
        Map<String, Object> range = new HashMap<>();
        range.put("inicio", inicio.toString());
        range.put("fin", fin.toString());
        payload.put("range", range);
        UserDetails userDetails = currentUserDetails();
        if (userDetails == null || validarAcceso(userDetails.getUsername()) != 1) {
            payload.put("error", "Sin permisos.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(payload);
        }
        try {
            String kpiSql =
                "SELECT " +
                " COUNT(*) AS total, " +
                " SUM(CASE WHEN I_ESTATUS IN (3,5) THEN 1 ELSE 0 END) AS aprobadas, " +
                " SUM(CASE WHEN I_ESTATUS = 4 THEN 1 ELSE 0 END) AS rechazadas, " +
                " SUM(CASE WHEN I_ESTATUS IN (1,2) THEN 1 ELSE 0 END) AS enProceso, " +
                " SUM(CASE WHEN I_ESTATUS IN (3,5) AND DT_FECHA_RESPUESTA_EMP IS NOT NULL " +
                "     THEN IFNULL(DC_MONTO_SOLICITADO,0) ELSE 0 END) AS montoDesembolsado, " +
                " AVG(CASE WHEN DT_FECHA_RESPUESTA_EMP IS NOT NULL THEN " +
                "     DATEDIFF(DT_FECHA_RESPUESTA_EMP, DT_FECHA_SOLICITUD) " +
                " END) AS avgDiasRespuesta, " +
                " AVG(IFNULL(DC_MONTO_SOLICITADO,0)) AS avgMontoSolicitado, " +
                " AVG(CASE WHEN I_ESTATUS IN (3,5) THEN IFNULL(DC_MONTO_SOLICITADO,0) END) AS avgMontoAprobado, " +
                " SUM(CASE WHEN DT_FECHA_RESPUESTA_EMP IS NOT NULL THEN 1 ELSE 0 END) AS respondidas, " +
                " SUM(CASE WHEN I_ESTATUS IN (1,2) THEN 1 ELSE 0 END) AS pendientes, " +
                " AVG(CASE WHEN I_ESTATUS IN (1,2) THEN DATEDIFF(CURDATE(), DT_FECHA_SOLICITUD) END) AS backlogDiasPromedio, " +
                " SUM(CASE WHEN DT_FECHA_RESPUESTA_EMP IS NOT NULL AND " +
                "     DATEDIFF(DT_FECHA_RESPUESTA_EMP, DT_FECHA_SOLICITUD) <= " + SLA_DIAS + " " +
                " THEN 1 ELSE 0 END) AS slaCumplidas, " +
                " SUM(CASE WHEN DT_FECHA_RESPUESTA_EMP IS NOT NULL THEN 1 ELSE 0 END) AS slaTotal " +
                "FROM TBL_SOLICITUDES_CREDITO WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ?";

            Map<String, Object> kpis = jdbcTemplate.queryForMap(kpiSql, inicio, finExclusive);

            String monthlySql =
                "SELECT DATE_FORMAT(DT_FECHA_SOLICITUD, '%Y-%m') AS mes, " +
                " COUNT(*) AS total, " +
                " SUM(CASE WHEN I_ESTATUS IN (3,5) THEN 1 ELSE 0 END) AS aprobadas, " +
                " SUM(CASE WHEN I_ESTATUS = 4 THEN 1 ELSE 0 END) AS rechazadas " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY mes " +
                "ORDER BY mes";

            List<Map<String, Object>> monthly = jdbcTemplate.queryForList(monthlySql, inicio, finExclusive);

            String statusSql =
                "SELECT I_ESTATUS AS estatus, COUNT(*) AS total " +
                "FROM TBL_SOLICITUDES_CREDITO WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY I_ESTATUS";

            List<Map<String, Object>> statusRows = jdbcTemplate.queryForList(statusSql, inicio, finExclusive);
            List<Map<String, Object>> status = new ArrayList<>();
            for (Map<String, Object> row : statusRows) {
                Integer code = toInt(row.get("estatus"));
                Map<String, Object> mapped = new HashMap<>();
                mapped.put("estatus", code);
                mapped.put("label", estatusLabel(code));
                mapped.put("total", toInt(row.get("total")));
                status.add(mapped);
            }

            String dailySql =
                "SELECT DATE_FORMAT(DT_FECHA_SOLICITUD, '%Y-%m-%d') AS dia, " +
                " COUNT(*) AS total, " +
                " SUM(CASE WHEN I_ESTATUS IN (3,5) THEN 1 ELSE 0 END) AS aprobadas, " +
                " SUM(CASE WHEN I_ESTATUS = 4 THEN 1 ELSE 0 END) AS rechazadas " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY dia " +
                "ORDER BY dia";

            List<Map<String, Object>> daily = jdbcTemplate.queryForList(dailySql, inicio, finExclusive);

            String topEmpresasSql =
                "SELECT VC_EMPRESA AS label, COUNT(*) AS total " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY VC_EMPRESA " +
                "ORDER BY total DESC " +
                "LIMIT 5";

            List<Map<String, Object>> topEmpresas = jdbcTemplate.queryForList(topEmpresasSql, inicio, finExclusive);

            String topEmpresasMontoSql =
                "SELECT VC_EMPRESA AS label, SUM(IFNULL(DC_MONTO_SOLICITADO,0)) AS total " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY VC_EMPRESA " +
                "ORDER BY total DESC " +
                "LIMIT 5";

            List<Map<String, Object>> topEmpresasMonto = jdbcTemplate.queryForList(topEmpresasMontoSql, inicio, finExclusive);

            String topProductosSql =
                "SELECT VC_PRODUCTO AS label, COUNT(*) AS total " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY VC_PRODUCTO " +
                "ORDER BY total DESC " +
                "LIMIT 5";

            List<Map<String, Object>> topProductos = jdbcTemplate.queryForList(topProductosSql, inicio, finExclusive);

            String sucursalesSql =
                "SELECT I_SUCURSAL AS label, COUNT(*) AS total " +
                "FROM TBL_SOLICITUDES_CREDITO " +
                "WHERE DT_FECHA_SOLICITUD >= ? AND DT_FECHA_SOLICITUD < ? " +
                "GROUP BY I_SUCURSAL " +
                "ORDER BY total DESC " +
                "LIMIT 10";

            List<Map<String, Object>> sucursales = jdbcTemplate.queryForList(sucursalesSql, inicio, finExclusive);

            Map<String, Object> series = new HashMap<>();
            series.put("monthly", monthly);
            series.put("status", status);
            series.put("daily", daily);
            series.put("topEmpresas", topEmpresas);
            series.put("topEmpresasMonto", topEmpresasMonto);
            series.put("topProductos", topProductos);
            series.put("sucursales", sucursales);

            payload.put("kpis", normalizeKpis(kpis));
            payload.put("series", series);
            return ResponseEntity.ok(payload);
        } catch (Exception e) {
            logger.error("Error al obtener metricas de VoBo Nomina", e);
            payload.put("error", "No se pudieron cargar las metricas.");
            return ResponseEntity.internalServerError().body(payload);
        }
    }

    private Map<String, Object> normalizeKpis(Map<String, Object> raw) {
        Map<String, Object> kpis = new HashMap<>();
        int total = toInt(raw.get("total"));
        int aprobadas = toInt(raw.get("aprobadas"));
        int rechazadas = toInt(raw.get("rechazadas"));
        int respondidas = toInt(raw.get("respondidas"));
        int slaCumplidas = toInt(raw.get("slaCumplidas"));
        kpis.put("total", total);
        kpis.put("aprobadas", aprobadas);
        kpis.put("rechazadas", rechazadas);
        kpis.put("enProceso", toInt(raw.get("enProceso")));
        kpis.put("montoDesembolsado", toBigDecimal(raw.get("montoDesembolsado")));
        kpis.put("avgDiasRespuesta", toDouble(raw.get("avgDiasRespuesta")));
        kpis.put("avgMontoSolicitado", toBigDecimal(raw.get("avgMontoSolicitado")));
        kpis.put("avgMontoAprobado", toBigDecimal(raw.get("avgMontoAprobado")));
        kpis.put("respondidas", respondidas);
        kpis.put("pendientes", toInt(raw.get("pendientes")));
        kpis.put("backlogDiasPromedio", toDouble(raw.get("backlogDiasPromedio")));
        kpis.put("slaCumplidas", slaCumplidas);
        kpis.put("slaTotal", toInt(raw.get("slaTotal")));
        kpis.put("tasaAprobacion", total > 0 ? (aprobadas * 100.0) / total : 0.0);
        kpis.put("tasaRechazo", total > 0 ? (rechazadas * 100.0) / total : 0.0);
        kpis.put("tasaSla", respondidas > 0 ? (slaCumplidas * 100.0) / respondidas : 0.0);
        return kpis;
    }

    private int toInt(Object value) {
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }

    private double toDouble(Object value) {
        if (value == null) return 0.0;
        if (value instanceof Number) return ((Number) value).doubleValue();
        return Double.parseDouble(value.toString());
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        return new BigDecimal(value.toString());
    }

    private LocalDate parseFecha(String value, LocalDate fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        try {
            return LocalDate.parse(value.trim());
        } catch (Exception e) {
            logger.warn("Fecha invalida: " + value + ", usando default", e);
            return fallback;
        }
    }

    private UserDetails currentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        logger.warn("Usuario no autenticado");
        return null;
    }

    private int validarAcceso(String usuarioLogin) {
        List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(usuarioLogin);
        if (usuarios == null || usuarios.isEmpty()) {
            logger.warn("Usuario no encontrado: " + usuarioLogin);
            return 0;
        }
        int rol = usuarios.get(0).getRol_idRol().getPk_id_rol();
        return iRelRolOpcionesRepository.validarAcceso(
            rol,
            opcionesMenuService.buscarRelacionController(rutaController)
        );
    }


    private String estatusLabel(Integer estatus) {
        if (estatus == null) return "Desconocido";
        switch (estatus) {
            case 1: return "En Revision";
            case 2: return "Enviada";
            case 3: return "Aprobada";
            case 4: return "Rechazada";
            case 5: return "Aceptada";
            case 0: return "Cancelada";
            default: return "Desconocido";
        }
    }
}
