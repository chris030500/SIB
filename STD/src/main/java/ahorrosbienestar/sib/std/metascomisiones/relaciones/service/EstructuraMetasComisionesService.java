package ahorrosbienestar.sib.std.metascomisiones.relaciones.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.ColaboradorRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.ProductoRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.PuestoRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.RegionRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.SucursalRelacionDto;

@Service
public class EstructuraMetasComisionesService {

    private static final Logger logger = Logger.getLogger(EstructuraMetasComisionesService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void inicializarTablaRelacionPuestoProducto() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_PUESTO_PRODUCTO ("
                    + "PK_ID_RELACION BIGINT NOT NULL AUTO_INCREMENT,"
                    + "I_CLAVE_PUESTO_ID INT NOT NULL,"
                    + "FK_ID_PRODUCTO INT NOT NULL,"
                    + "I_NO_PRODUCTO_EXCEL INT NULL,"
                    + "I_ESTATUS TINYINT(1) NOT NULL DEFAULT 1,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "DT_FECHA_BAJA DATETIME NULL,"
                    + "PRIMARY KEY (PK_ID_RELACION),"
                    + "UNIQUE KEY UK_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO)"
                    + ")";
            jdbcTemplate.execute(sql);

            String sqlDetalle = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO ("
                    + "PK_ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "I_SUCURSAL_NUM INT NOT NULL,"
                    + "I_CLAVE_PUESTO_ID INT NOT NULL,"
                    + "FK_ID_PRODUCTO INT NOT NULL,"
                    + "I_NO_PRODUCTO_EXCEL INT NULL,"
                    + "VC_PRODUCTO_DET_NOM VARCHAR(200) NULL,"
                    + "D_PORC_NUEVO DECIMAL(12,8) NOT NULL DEFAULT 0,"
                    + "D_PORC_RENOVADO DECIMAL(12,8) NOT NULL DEFAULT 0,"
                    + "VC_CANAL VARCHAR(60) NOT NULL DEFAULT 'TRADICIONAL',"
                    + "VC_FUERZA_VENTA VARCHAR(60) NOT NULL DEFAULT 'SUCURSAL',"
                    + "DT_VIGENCIA_INICIO DATE NULL,"
                    + "DT_VIGENCIA_FIN DATE NULL,"
                    + "I_ESTATUS TINYINT(1) NOT NULL DEFAULT 1,"
                    + "I_VERSION INT NOT NULL DEFAULT 1,"
                    + "VC_USUARIO_ALTA VARCHAR(150) NULL,"
                    + "VC_USUARIO_MODIFICACION VARCHAR(150) NULL,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "DT_FECHA_MODIFICACION DATETIME NULL,"
                    + "PRIMARY KEY (PK_ID),"
                    + "UNIQUE KEY UK_MC_SUC_PUESTO_PRODUCTO (I_SUCURSAL_NUM, I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO)"
                    + ")";
            jdbcTemplate.execute(sqlDetalle);
            asegurarColumna("TBL_MC_PUESTO_PRODUCTO", "I_NO_PRODUCTO_EXCEL", "INT NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "I_NO_PRODUCTO_EXCEL", "INT NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "VC_CANAL", "VARCHAR(60) NOT NULL DEFAULT 'TRADICIONAL'");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "VC_FUERZA_VENTA", "VARCHAR(60) NOT NULL DEFAULT 'SUCURSAL'");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "DT_VIGENCIA_INICIO", "DATE NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "DT_VIGENCIA_FIN", "DATE NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "I_ESTATUS", "TINYINT(1) NOT NULL DEFAULT 1");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "I_VERSION", "INT NOT NULL DEFAULT 1");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "VC_USUARIO_ALTA", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "VC_USUARIO_MODIFICACION", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_SUCURSAL_PUESTO_PRODUCTO", "DT_FECHA_MODIFICACION", "DATETIME NULL");
            inicializarTablaHistoricoPuestoProducto();
        } catch (Exception e) {
            logger.warn("No se pudo crear/verificar TBL_MC_PUESTO_PRODUCTO: " + e.getMessage());
        }
    }

    private void asegurarColumna(String tabla, String columna, String definicionSql) {
        try {
            Integer existe = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS "
                            + "WHERE TABLE_SCHEMA = 'SIB' AND TABLE_NAME = ? AND COLUMN_NAME = ?",
                    Integer.class, tabla, columna);
            if (existe == null || existe == 0) {
                jdbcTemplate.execute("ALTER TABLE SIB." + tabla + " ADD COLUMN " + columna + " " + definicionSql);
            }
        } catch (Exception e) {
            logger.warn("No se pudo asegurar columna " + columna + " en " + tabla + ": " + e.getMessage());
        }
    }

    public void inicializarTablasCatalogo() {
        try {
            String suc = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_SUCURSAL_REGION ("
                    + "PK_ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "I_REGION_TIPO_NUM INT NULL,"
                    + "I_REGION_NUM INT NULL,"
                    + "I_SUCURSAL_NUM INT NULL,"
                    + "VC_SUCURSAL_NOM VARCHAR(200) NULL,"
                    + "VC_SUCURSAL_ESTATUS VARCHAR(50) NULL,"
                    + "VC_CANALES VARCHAR(60) NOT NULL DEFAULT 'TRADICIONAL',"
                    + "VC_REGION_NOM VARCHAR(200) NULL,"
                    + "VC_ESTADO VARCHAR(100) NULL,"
                    + "I_VERSION INT NOT NULL DEFAULT 1,"
                    + "VC_USUARIO_ALTA VARCHAR(150) NULL,"
                    + "VC_USUARIO_MODIFICACION VARCHAR(150) NULL,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "DT_FECHA_MODIFICACION DATETIME NULL,"
                    + "PRIMARY KEY (PK_ID),"
                    + "UNIQUE KEY UK_MC_SUCURSAL_NUM (I_SUCURSAL_NUM)"
                    + ")";
            jdbcTemplate.execute(suc);
            try {
                jdbcTemplate.execute("ALTER TABLE SIB.TBL_MC_SUCURSAL_REGION MODIFY COLUMN I_SUCURSAL_NUM INT NULL");
            } catch (Exception ignore) {
            }
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "VC_CANALES", "VARCHAR(60) NOT NULL DEFAULT 'TRADICIONAL'");
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "I_VERSION", "INT NOT NULL DEFAULT 1");
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "VC_USUARIO_ALTA", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "VC_USUARIO_MODIFICACION", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "DT_FECHA_ALTA", "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP");
            asegurarColumna("TBL_MC_SUCURSAL_REGION", "DT_FECHA_MODIFICACION", "DATETIME NULL");

            String emp = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_EMPLEADO_UBICACION ("
                    + "PK_ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "I_EMPLEADO_NUM INT NOT NULL,"
                    + "VC_EMPLEADO_NOM VARCHAR(250) NULL,"
                    + "I_REGION_TIPO_NUM INT NULL,"
                    + "I_REGION_NUM INT NULL,"
                    + "I_SUCURSAL_NUM INT NULL,"
                    + "VC_SUCURSAL_NOM VARCHAR(200) NULL,"
                    + "I_PUESTO_ID INT NULL,"
                    + "VC_PUESTO_DESC VARCHAR(200) NULL,"
                    + "VC_APLICA_META VARCHAR(10) NULL,"
                    + "VC_ESTADO VARCHAR(100) NULL,"
                    + "I_PERIODO_NUM INT NULL,"
                    + "DT_VIGENCIA_INICIO DATE NULL,"
                    + "DT_VIGENCIA_FIN DATE NULL,"
                    + "I_VERSION INT NOT NULL DEFAULT 1,"
                    + "VC_USUARIO_ALTA VARCHAR(150) NULL,"
                    + "VC_USUARIO_MODIFICACION VARCHAR(150) NULL,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "DT_FECHA_MODIFICACION DATETIME NULL,"
                    + "PRIMARY KEY (PK_ID),"
                    + "KEY IX_MC_EMPLEADO_NUM (I_EMPLEADO_NUM),"
                    + "KEY IX_MC_SUCURSAL_NUM (I_SUCURSAL_NUM)"
                    + ")";
            jdbcTemplate.execute(emp);
            try {
                jdbcTemplate.execute("DROP INDEX UK_MC_EMPLEADO_NUM ON SIB.TBL_MC_EMPLEADO_UBICACION");
            } catch (Exception ignore) {
                // Si no existe, no hacemos nada.
            }
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "DT_VIGENCIA_INICIO", "DATE NULL");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "DT_VIGENCIA_FIN", "DATE NULL");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "I_VERSION", "INT NOT NULL DEFAULT 1");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "VC_USUARIO_ALTA", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "VC_USUARIO_MODIFICACION", "VARCHAR(150) NULL");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "DT_FECHA_ALTA", "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP");
            asegurarColumna("TBL_MC_EMPLEADO_UBICACION", "DT_FECHA_MODIFICACION", "DATETIME NULL");

            inicializarTablaAuditoria();
            inicializarTablaHistoricoEmpleadoUbicacion();
        } catch (Exception e) {
            logger.warn("No se pudieron crear tablas de catalogo de metas/comisiones: " + e.getMessage());
        }
    }

    private void inicializarTablaAuditoria() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_REL_AUDITORIA ("
                    + "PK_ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "VC_EVENTO VARCHAR(80) NOT NULL,"
                    + "VC_ENTIDAD VARCHAR(80) NOT NULL,"
                    + "I_ENTIDAD_ID BIGINT NULL,"
                    + "VC_USUARIO VARCHAR(150) NULL,"
                    + "VC_DETALLE VARCHAR(2000) NULL,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (PK_ID),"
                    + "KEY IX_MC_REL_AUDITORIA_FECHA (DT_FECHA_ALTA)"
                    + ")";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            logger.warn("No se pudo crear/verificar tabla de auditoria: " + e.getMessage());
        }
    }

    private void inicializarTablaHistoricoPuestoProducto() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_SPP_HISTORICO ("
                    + "PK_ID_HIST BIGINT NOT NULL AUTO_INCREMENT,"
                    + "FK_ID_REGISTRO BIGINT NOT NULL,"
                    + "VC_EVENTO VARCHAR(60) NOT NULL,"
                    + "I_SUCURSAL_NUM INT NULL,"
                    + "I_CLAVE_PUESTO_ID INT NULL,"
                    + "FK_ID_PRODUCTO INT NULL,"
                    + "I_NO_PRODUCTO_EXCEL INT NULL,"
                    + "VC_PRODUCTO_DET_NOM VARCHAR(200) NULL,"
                    + "D_PORC_NUEVO DECIMAL(12,8) NULL,"
                    + "D_PORC_RENOVADO DECIMAL(12,8) NULL,"
                    + "VC_CANAL VARCHAR(60) NULL,"
                    + "VC_FUERZA_VENTA VARCHAR(60) NULL,"
                    + "DT_VIGENCIA_INICIO DATE NULL,"
                    + "DT_VIGENCIA_FIN DATE NULL,"
                    + "I_ESTATUS TINYINT(1) NULL,"
                    + "I_VERSION INT NULL,"
                    + "VC_USUARIO_ACCION VARCHAR(150) NULL,"
                    + "DT_FECHA_ACCION DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (PK_ID_HIST),"
                    + "KEY IX_MC_SPP_HIST_REG (FK_ID_REGISTRO),"
                    + "KEY IX_MC_SPP_HIST_FECHA (DT_FECHA_ACCION)"
                    + ")";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            logger.warn("No se pudo crear/verificar tabla historica de puesto-producto: " + e.getMessage());
        }
    }

    private void inicializarTablaHistoricoEmpleadoUbicacion() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_EU_HISTORICO ("
                    + "PK_ID_HIST BIGINT NOT NULL AUTO_INCREMENT,"
                    + "FK_ID_REGISTRO BIGINT NOT NULL,"
                    + "VC_EVENTO VARCHAR(60) NOT NULL,"
                    + "I_EMPLEADO_NUM INT NULL,"
                    + "VC_EMPLEADO_NOM VARCHAR(250) NULL,"
                    + "I_REGION_NUM INT NULL,"
                    + "I_SUCURSAL_NUM INT NULL,"
                    + "VC_SUCURSAL_NOM VARCHAR(200) NULL,"
                    + "I_PUESTO_ID INT NULL,"
                    + "VC_PUESTO_DESC VARCHAR(200) NULL,"
                    + "VC_APLICA_META VARCHAR(10) NULL,"
                    + "VC_ESTADO VARCHAR(100) NULL,"
                    + "I_PERIODO_NUM INT NULL,"
                    + "DT_VIGENCIA_INICIO DATE NULL,"
                    + "DT_VIGENCIA_FIN DATE NULL,"
                    + "I_VERSION INT NULL,"
                    + "VC_USUARIO_ACCION VARCHAR(150) NULL,"
                    + "DT_FECHA_ACCION DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (PK_ID_HIST),"
                    + "KEY IX_MC_EU_HIST_REG (FK_ID_REGISTRO),"
                    + "KEY IX_MC_EU_HIST_FECHA (DT_FECHA_ACCION)"
                    + ")";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            logger.warn("No se pudo crear/verificar tabla historica de empleado-ubicacion: " + e.getMessage());
        }
    }

    public Map<String, Object> cargarCatalogos(MultipartFile sucursalesFile, MultipartFile empleadosFile,
            MultipartFile puestosProductosFile) throws IOException {

        inicializarTablasCatalogo();
        inicializarTablaRelacionPuestoProducto();
        List<Map<String, Object>> erroresSucursales = new ArrayList<>();
        List<Map<String, Object>> erroresEmpleados = new ArrayList<>();
        List<Map<String, Object>> erroresPuestosProductos = new ArrayList<>();

        int sucursalesProcesadas = cargarCatalogoSucursales(sucursalesFile, erroresSucursales);
        Map<String, Integer> puestosDetectados = cargarCatalogoEmpleados(empleadosFile, erroresEmpleados);
        Map<String, Object> resumenPuestosProductos = cargarCatalogoPuestosProductos(puestosProductosFile, puestosDetectados,
                erroresPuestosProductos);

        Map<String, Object> result = new HashMap<>();
        result.put("sucursalesProcesadas", sucursalesProcesadas);
        result.put("puestosDetectados", puestosDetectados);
        result.putAll(resumenPuestosProductos);
        result.put("erroresSucursales", limitarErrores(erroresSucursales, 200));
        result.put("erroresEmpleados", limitarErrores(erroresEmpleados, 200));
        result.put("erroresPuestosProductos", limitarErrores(erroresPuestosProductos, 200));
        result.put("previewSucursales", obtenerVistaPreviaSucursales(20));
        result.put("previewColaboradores", obtenerVistaPreviaColaboradores(20));
        result.put("previewPuestoProducto", obtenerVistaPreviaPuestoProducto(20));
        registrarAuditoria("CARGA_EXCEL", "CARGA_COMPLETA", null,
                "sucursalesProcesadas=" + sucursalesProcesadas
                        + ", empleados=" + contarTabla("TBL_MC_EMPLEADO_UBICACION")
                        + ", relPP=" + resumenPuestosProductos.get("relacionesGeneradas"));
        return result;
    }

    public Map<String, Object> cargarSoloSucursales(MultipartFile sucursalesFile) throws IOException {
        inicializarTablasCatalogo();
        List<Map<String, Object>> errores = new ArrayList<>();
        int sucursalesProcesadas = cargarCatalogoSucursales(sucursalesFile, errores);

        Map<String, Object> result = new HashMap<>();
        result.put("sucursalesProcesadas", sucursalesProcesadas);
        result.put("totalSucursalRegion", contarTabla("TBL_MC_SUCURSAL_REGION"));
        result.put("errores", limitarErrores(errores, 200));
        result.put("previewSucursales", obtenerVistaPreviaSucursales(20));
        registrarAuditoria("CARGA_EXCEL", "SUCURSAL_REGION", null, "procesadas=" + sucursalesProcesadas);
        return result;
    }

    public Map<String, Object> cargarSoloEmpleados(MultipartFile empleadosFile) throws IOException {
        inicializarTablasCatalogo();
        List<Map<String, Object>> errores = new ArrayList<>();
        Map<String, Integer> puestosDetectados = cargarCatalogoEmpleados(empleadosFile, errores);

        Map<String, Object> result = new HashMap<>();
        result.put("puestosDetectados", puestosDetectados);
        result.put("totalEmpleadoUbicacion", contarTabla("TBL_MC_EMPLEADO_UBICACION"));
        result.put("errores", limitarErrores(errores, 200));
        result.put("previewColaboradores", obtenerVistaPreviaColaboradores(20));
        registrarAuditoria("CARGA_EXCEL", "EMPLEADO_UBICACION", null,
                "registros=" + contarTabla("TBL_MC_EMPLEADO_UBICACION"));
        return result;
    }

    public Map<String, Object> cargarSoloPuestosProductos(MultipartFile puestosProductosFile) throws IOException {
        inicializarTablasCatalogo();
        inicializarTablaRelacionPuestoProducto();

        Map<String, Integer> puestosDetectados = detectarPuestosDesdeTablaEmpleados();
        List<Map<String, Object>> errores = new ArrayList<>();
        Map<String, Object> resumenPuestosProductos = cargarCatalogoPuestosProductos(puestosProductosFile, puestosDetectados, errores);

        Map<String, Object> result = new HashMap<>();
        result.put("puestosDetectados", puestosDetectados);
        result.putAll(resumenPuestosProductos);
        result.put("errores", limitarErrores(errores, 200));
        result.put("previewPuestoProducto", obtenerVistaPreviaPuestoProducto(20));
        registrarAuditoria("CARGA_EXCEL", "SUCURSAL_PUESTO_PRODUCTO", null,
                "relaciones=" + resumenPuestosProductos.get("relacionesGeneradas"));
        return result;
    }

    private int cargarCatalogoSucursales(MultipartFile file) throws IOException {
        return cargarCatalogoSucursales(file, null);
    }

    private int cargarCatalogoSucursales(MultipartFile file, List<Map<String, Object>> errores) throws IOException {
        DataFormatter fmt = new DataFormatter();
        int rows = 0;

        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_SUCURSAL_REGION");

        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Map<String, Integer> headers = extraerHeaders(sheet.getRow(0), fmt);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                try {
                    Integer regionTipoNum = toInteger(fmt.formatCellValue(row.getCell(1)));
                    Integer regionNum = toInteger(fmt.formatCellValue(row.getCell(2)));
                    Integer sucursalNum = toInteger(fmt.formatCellValue(row.getCell(4)));
                    String sucursalNom = trim(fmt.formatCellValue(row.getCell(5)));
                    String sucursalEstatus = trim(fmt.formatCellValue(row.getCell(6)));
                    String estado = trim(fmt.formatCellValue(row.getCell(11)));
                    String regionNom = trim(fmt.formatCellValue(row.getCell(12)));
                    String canales = resolverCanalesDesdeLayout(row, fmt, headers);

                    if (sucursalNum == null) {
                        agregarErrorCarga(errores, i + 1, "I_SUCURSAL_NUM vacio o invalido");
                        continue;
                    }

                    jdbcTemplate.update(
                            "INSERT INTO SIB.TBL_MC_SUCURSAL_REGION "
                                    + "(I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, VC_SUCURSAL_ESTATUS, VC_CANALES, VC_REGION_NOM, VC_ESTADO, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, NOW()) "
                                    + "ON DUPLICATE KEY UPDATE I_REGION_TIPO_NUM=VALUES(I_REGION_TIPO_NUM), I_REGION_NUM=VALUES(I_REGION_NUM), "
                                    + "VC_SUCURSAL_NOM=VALUES(VC_SUCURSAL_NOM), VC_SUCURSAL_ESTATUS=VALUES(VC_SUCURSAL_ESTATUS), "
                                    + "VC_CANALES=VALUES(VC_CANALES), VC_REGION_NOM=VALUES(VC_REGION_NOM), VC_ESTADO=VALUES(VC_ESTADO), "
                                    + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = VALUES(VC_USUARIO_MODIFICACION), DT_FECHA_MODIFICACION = NOW()",
                            regionTipoNum, regionNum, sucursalNum, sucursalNom, sucursalEstatus, canales, regionNom, estado,
                            obtenerUsuarioActual(), obtenerUsuarioActual());
                    rows++;
                } catch (Exception e) {
                    agregarErrorCarga(errores, i + 1, "Error procesando fila: " + e.getMessage());
                }
            }
        }

        return rows;
    }

    private Map<String, Integer> extraerHeaders(Row headerRow, DataFormatter fmt) {
        Map<String, Integer> headers = new HashMap<>();
        if (headerRow == null) {
            return headers;
        }
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String value = normalizarTexto(fmt.formatCellValue(headerRow.getCell(i)));
            if (value != null) {
                headers.put(value, i);
            }
        }
        return headers;
    }

    private String resolverCanalesDesdeLayout(Row row, DataFormatter fmt, Map<String, Integer> headers) {
        List<String> candidates = Arrays.asList(
                valorHeader(row, fmt, headers, "VCCANAL"),
                valorHeader(row, fmt, headers, "CANAL"),
                valorHeader(row, fmt, headers, "CANALES"),
                valorHeader(row, fmt, headers, "CANALVENTA"),
                valorHeader(row, fmt, headers, "TIPOCANAL"));
        for (String candidate : candidates) {
            String normalized = normalizarCanales(candidate);
            if (normalized != null) {
                return normalized;
            }
        }
        return "TRADICIONAL";
    }

    private String valorHeader(Row row, DataFormatter fmt, Map<String, Integer> headers, String normalizedHeader) {
        Integer idx = headers.get(normalizedHeader);
        if (idx == null) {
            return null;
        }
        return trim(fmt.formatCellValue(row.getCell(idx)));
    }

    private Map<String, Integer> cargarCatalogoEmpleados(MultipartFile file) throws IOException {
        return cargarCatalogoEmpleados(file, null);
    }

    private Map<String, Integer> cargarCatalogoEmpleados(MultipartFile file, List<Map<String, Object>> errores) throws IOException {
        DataFormatter fmt = new DataFormatter();
        Map<String, Integer> puestos = new HashMap<>();

        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_EMPLEADO_UBICACION");

        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                try {
                    Integer regionTipoNum = toInteger(fmt.formatCellValue(row.getCell(3)));
                    Integer regionNum = toInteger(fmt.formatCellValue(row.getCell(4)));
                    Integer sucursalNum = toInteger(fmt.formatCellValue(row.getCell(6)));
                    String sucursalNom = trim(fmt.formatCellValue(row.getCell(7)));
                    Integer puestoId = toInteger(fmt.formatCellValue(row.getCell(8)));
                    String puestoDesc = trim(fmt.formatCellValue(row.getCell(9)));
                    String aplicaMeta = trim(fmt.formatCellValue(row.getCell(10)));
                    Integer empleadoNum = toInteger(fmt.formatCellValue(row.getCell(11)));
                    String empleadoNom = trim(fmt.formatCellValue(row.getCell(12)));
                    String estado = trim(fmt.formatCellValue(row.getCell(15)));
                    Integer periodoNum = toInteger(fmt.formatCellValue(row.getCell(16)));
                    String vigenciaInicio = periodoNum != null && String.valueOf(periodoNum).length() >= 6
                            ? (String.valueOf(periodoNum).substring(0, 4) + "-" + String.valueOf(periodoNum).substring(4, 6) + "-01")
                            : null;

                    if (empleadoNum == null) {
                        agregarErrorCarga(errores, i + 1, "I_EMPLEADO_NUM vacio o invalido");
                        continue;
                    }

                    jdbcTemplate.update(
                            "INSERT INTO SIB.TBL_MC_EMPLEADO_UBICACION "
                                    + "(I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, 1, ?, ?, NOW())",
                            empleadoNum, empleadoNom, regionTipoNum, regionNum, sucursalNum, sucursalNom, puestoId, puestoDesc,
                            aplicaMeta, estado, periodoNum, toSqlDateOrNull(vigenciaInicio), obtenerUsuarioActual(), obtenerUsuarioActual());

                    if (puestoId != null && puestoDesc != null) {
                        String norm = puestoDesc.toUpperCase();
                        if (norm.contains("GERENTE")) {
                            puestos.put("GERENTE", puestoId);
                        } else if (norm.contains("EJECUTIVO")) {
                            puestos.put("EJECUTIVO", puestoId);
                        } else if (norm.contains("CAJERO")) {
                            puestos.put("CAJERO", puestoId);
                        } else if (norm.contains("PROMOTOR") || norm.contains("JR")) {
                            puestos.put("PROMOTOR_JR", puestoId);
                        }
                    }
                } catch (Exception e) {
                    agregarErrorCarga(errores, i + 1, "Error procesando fila: " + e.getMessage());
                }
            }
        }

        return puestos;
    }

    private Map<String, Object> cargarCatalogoPuestosProductos(MultipartFile file, Map<String, Integer> puestosDetectados)
            throws IOException {
        return cargarCatalogoPuestosProductos(file, puestosDetectados, null);
    }

    private Map<String, Object> cargarCatalogoPuestosProductos(MultipartFile file, Map<String, Integer> puestosDetectados,
            List<Map<String, Object>> erroresCarga) throws IOException {
        DataFormatter fmt = new DataFormatter();
        int relaciones = 0;
        int filasProcesadas = 0;
        int productosNoEncontrados = 0;
        int productosResueltosConFallbackExcel = 0;
        int filasSinSucursal = 0;
        int filasSinPuestosRol = 0;

        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_PUESTO_PRODUCTO");
        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO");
        Map<Integer, Integer> productosPorNumero = cargarProductosPorNumero();
        Map<String, Integer> productosPorNombreNormalizado = cargarProductosPorNombreNormalizado();
        List<Map.Entry<String, Integer>> productosPorNombreLista = new ArrayList<>(productosPorNombreNormalizado.entrySet());

        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                try {
                    Integer sucursalNumero = toInteger(fmt.formatCellValue(row.getCell(1)));
                    Integer productoNumero = toInteger(fmt.formatCellValue(row.getCell(3)));
                    String productoDetNom = trim(fmt.formatCellValue(row.getCell(4)));
                    Double gerenteN = toDouble(fmt.formatCellValue(row.getCell(5)));
                    Double ejecutivoN = toDouble(fmt.formatCellValue(row.getCell(6)));
                    Double cajeroN = toDouble(fmt.formatCellValue(row.getCell(7)));
                    Double promotorN = toDouble(fmt.formatCellValue(row.getCell(8)));
                    Double gerenteR = toDouble(fmt.formatCellValue(row.getCell(9)));
                    Double ejecutivoR = toDouble(fmt.formatCellValue(row.getCell(10)));
                    Double cajeroR = toDouble(fmt.formatCellValue(row.getCell(11)));
                    Double promotorR = toDouble(fmt.formatCellValue(row.getCell(12)));

                    if (sucursalNumero == null) {
                        filasSinSucursal++;
                        agregarErrorCarga(erroresCarga, i + 1, "I_SUCURSAL_NUM vacio o invalido");
                        continue;
                    }
                    if (!porcentajeValido(gerenteN) || !porcentajeValido(ejecutivoN) || !porcentajeValido(cajeroN)
                            || !porcentajeValido(promotorN) || !porcentajeValido(gerenteR) || !porcentajeValido(ejecutivoR)
                            || !porcentajeValido(cajeroR) || !porcentajeValido(promotorR)) {
                        agregarErrorCarga(erroresCarga, i + 1, "Porcentajes fuera de rango [0,100]");
                        continue;
                    }

                    filasProcesadas++;

                    Integer productoId = resolverProductoId(productoNumero, productoDetNom, productosPorNumero,
                            productosPorNombreNormalizado, productosPorNombreLista);
                    if (productoId == null) {
                        productosNoEncontrados++;
                        productoId = generarProductoIdFallback(productoNumero, productoDetNom);
                        productosResueltosConFallbackExcel++;
                    }

                    Map<String, Integer> puestosPorSucursal = obtenerPuestosPorRolEnSucursal(sucursalNumero, puestosDetectados);
                    if (puestosPorSucursal.get("GERENTE") == null
                            && puestosPorSucursal.get("EJECUTIVO") == null
                            && puestosPorSucursal.get("CAJERO") == null
                            && puestosPorSucursal.get("PROMOTOR_JR") == null) {
                        filasSinPuestosRol++;
                        agregarErrorCarga(erroresCarga, i + 1, "No se detectaron puestos en sucursal " + sucursalNumero);
                        continue;
                    }

                    relaciones += insertarRelacionPorPorcentaje(sucursalNumero, puestosPorSucursal.get("GERENTE"), productoId,
                            productoNumero, productoDetNom, gerenteN, gerenteR);

                    relaciones += insertarRelacionPorPorcentaje(sucursalNumero, puestosPorSucursal.get("EJECUTIVO"), productoId,
                            productoNumero, productoDetNom, ejecutivoN, ejecutivoR);

                    relaciones += insertarRelacionPorPorcentaje(sucursalNumero, puestosPorSucursal.get("CAJERO"), productoId,
                            productoNumero, productoDetNom, cajeroN, cajeroR);

                    relaciones += insertarRelacionPorPorcentaje(sucursalNumero, puestosPorSucursal.get("PROMOTOR_JR"), productoId,
                            productoNumero, productoDetNom, promotorN, promotorR);
                } catch (Exception e) {
                    agregarErrorCarga(erroresCarga, i + 1, "Error procesando fila: " + e.getMessage());
                }
            }
        }

        Map<String, Object> resumen = new HashMap<>();
        resumen.put("filasPuestosProductosProcesadas", filasProcesadas);
        resumen.put("relacionesGeneradas", relaciones);
        resumen.put("productosNoEncontrados", productosNoEncontrados);
        resumen.put("filasSinRegionDetectada", 0);
        resumen.put("filasSinSucursal", filasSinSucursal);
        resumen.put("filasSinPuestosDetectados", filasSinPuestosRol);
        resumen.put("productosResueltosConFallbackExcel", productosResueltosConFallbackExcel);
        resumen.put("totalTablaPuestoProducto", contarTabla("TBL_MC_PUESTO_PRODUCTO"));
        resumen.put("totalTablaSucursalPuestoProducto", contarTabla("TBL_MC_SUCURSAL_PUESTO_PRODUCTO"));
        resumen.put("errores", limitarErrores(erroresCarga, 200));
        return resumen;
    }

    private int insertarRelacionPorPorcentaje(Integer sucursalNum, Integer puestoId, Integer productoId, Integer productoNumero,
            String productoDetNom,
            Double porcentajeNue, Double porcentajeRen) {
        if (puestoId == null) {
            return 0;
        }

        double n = porcentajeNue == null ? 0.0 : porcentajeNue.doubleValue();
        double r = porcentajeRen == null ? 0.0 : porcentajeRen.doubleValue();
        if (n <= 0.0 && r <= 0.0) {
            return 0;
        }

        String sql = "INSERT INTO SIB.TBL_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, I_ESTATUS, DT_FECHA_BAJA) "
                + "VALUES (?, ?, ?, 1, NULL) "
                + "ON DUPLICATE KEY UPDATE I_NO_PRODUCTO_EXCEL = VALUES(I_NO_PRODUCTO_EXCEL), I_ESTATUS = 1, DT_FECHA_BAJA = NULL";
        jdbcTemplate.update(sql, puestoId, productoId, productoNumero);

        String sqlDetalle = "INSERT INTO SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO "
                + "(I_SUCURSAL_NUM, I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, VC_PRODUCTO_DET_NOM, D_PORC_NUEVO, D_PORC_RENOVADO, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE I_NO_PRODUCTO_EXCEL = VALUES(I_NO_PRODUCTO_EXCEL), VC_PRODUCTO_DET_NOM = VALUES(VC_PRODUCTO_DET_NOM), "
                + "D_PORC_NUEVO = VALUES(D_PORC_NUEVO), D_PORC_RENOVADO = VALUES(D_PORC_RENOVADO), "
                + "VC_USUARIO_MODIFICACION = VALUES(VC_USUARIO_MODIFICACION), DT_FECHA_MODIFICACION = NOW(), I_VERSION = COALESCE(I_VERSION,1) + 1";
        String usuario = obtenerUsuarioActual();
        jdbcTemplate.update(sqlDetalle, sucursalNum, puestoId, productoId, productoNumero, productoDetNom, n, r, usuario, usuario);
        return 1;
    }

    private Integer generarProductoIdFallback(Integer productoNumero, String productoDetNom) {
        if (productoNumero != null) {
            return 1000000000 + productoNumero;
        }
        int h = Math.abs((productoDetNom == null ? "SIN_NOMBRE" : productoDetNom).hashCode());
        return 1500000000 + (h % 100000000);
    }

    private Integer buscarProductoIdPorNumero(Integer numeroProducto) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT PK_ID_PRODUCTO FROM SIB.TBL_CAT_PRODUCTO WHERE I_NO_PRODUCTO = ? LIMIT 1",
                    new Object[] { numeroProducto }, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer buscarProductoIdPorNombre(String nombreProducto) {
        if (nombreProducto == null || nombreProducto.isBlank()) {
            return null;
        }
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT PK_ID_PRODUCTO FROM SIB.TBL_CAT_PRODUCTO "
                            + "WHERE UPPER(TRIM(VC_NOMBRE_PRODUCTO)) = UPPER(TRIM(?)) LIMIT 1",
                    new Object[] { nombreProducto }, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Map<Integer, Integer> cargarProductosPorNumero() {
        Map<Integer, Integer> map = new HashMap<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT PK_ID_PRODUCTO, I_NO_PRODUCTO FROM SIB.TBL_CAT_PRODUCTO");
            for (Map<String, Object> row : rows) {
                Integer id = toInteger(row.get("PK_ID_PRODUCTO"));
                Integer no = toInteger(row.get("I_NO_PRODUCTO"));
                if (id != null && no != null) {
                    map.put(no, id);
                }
            }
        } catch (Exception e) {
            logger.warn("No se pudo cargar catalogo de productos por numero: " + e.getMessage());
        }
        return map;
    }

    private Map<String, Integer> cargarProductosPorNombreNormalizado() {
        Map<String, Integer> map = new HashMap<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT PK_ID_PRODUCTO, VC_NOMBRE_PRODUCTO FROM SIB.TBL_CAT_PRODUCTO");
            for (Map<String, Object> row : rows) {
                Integer id = toInteger(row.get("PK_ID_PRODUCTO"));
                String nombre = row.get("VC_NOMBRE_PRODUCTO") == null ? null : String.valueOf(row.get("VC_NOMBRE_PRODUCTO"));
                String key = normalizarTexto(nombre);
                if (id != null && key != null && !key.isBlank()) {
                    map.putIfAbsent(key, id);
                }
            }
        } catch (Exception e) {
            logger.warn("No se pudo cargar catalogo de productos por nombre: " + e.getMessage());
        }
        return map;
    }

    private Integer resolverProductoId(Integer numeroProducto, String nombreProducto, Map<Integer, Integer> productosPorNumero,
            Map<String, Integer> productosPorNombreNormalizado, List<Map.Entry<String, Integer>> productosPorNombreLista) {
        if (numeroProducto != null) {
            Integer id = productosPorNumero.get(numeroProducto);
            if (id != null) {
                return id;
            }
        }

        String nombreNormalizado = normalizarTexto(nombreProducto);
        if (nombreNormalizado != null && !nombreNormalizado.isBlank()) {
            Integer id = productosPorNombreNormalizado.get(nombreNormalizado);
            if (id != null) {
                return id;
            }
            for (Map.Entry<String, Integer> e : productosPorNombreLista) {
                String key = e.getKey();
                if (key.contains(nombreNormalizado) || nombreNormalizado.contains(key)) {
                    return e.getValue();
                }
            }
        }

        // Fallback final con queries directas para compatibilidad con datos legacy.
        Integer idByNombre = buscarProductoIdPorNombre(nombreProducto);
        if (idByNombre != null) {
            return idByNombre;
        }
        if (numeroProducto != null) {
            return buscarProductoIdPorNumero(numeroProducto);
        }
        return null;
    }

    private Map<String, Integer> obtenerPuestosPorRolEnSucursal(Integer sucursalNumero, Map<String, Integer> puestosFallback) {
        Map<String, Integer> result = new HashMap<>();
        if (puestosFallback != null) {
            result.putAll(puestosFallback);
        }

        if (sucursalNumero == null) {
            return result;
        }

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT DISTINCT I_PUESTO_ID, UPPER(COALESCE(VC_PUESTO_DESC, '')) AS VC_PUESTO_DESC "
                            + "FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                            + "WHERE I_SUCURSAL_NUM = ?",
                    sucursalNumero);

            for (Map<String, Object> row : rows) {
                Integer puestoId = row.get("I_PUESTO_ID") == null ? null : Integer.parseInt(String.valueOf(row.get("I_PUESTO_ID")));
                String desc = row.get("VC_PUESTO_DESC") == null ? "" : String.valueOf(row.get("VC_PUESTO_DESC"));
                if (puestoId == null) {
                    continue;
                }
                if (desc.contains("GERENTE")) {
                    result.put("GERENTE", puestoId);
                } else if (desc.contains("EJECUTIVO")) {
                    result.put("EJECUTIVO", puestoId);
                } else if (desc.contains("CAJERO")) {
                    result.put("CAJERO", puestoId);
                } else if (desc.contains("PROMOTOR") || desc.contains("JR")) {
                    result.put("PROMOTOR_JR", puestoId);
                }
            }
        } catch (Exception e) {
            logger.warn("No se pudieron detectar puestos por sucursal " + sucursalNumero + ": " + e.getMessage());
        }

        return result;
    }

    private Map<String, Integer> detectarPuestosDesdeTablaEmpleados() {
        Map<String, Integer> puestos = new HashMap<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT DISTINCT I_PUESTO_ID, UPPER(COALESCE(VC_PUESTO_DESC, '')) AS VC_PUESTO_DESC "
                            + "FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                            + "WHERE I_PUESTO_ID IS NOT NULL");

            for (Map<String, Object> row : rows) {
                Integer puestoId = toInteger(row.get("I_PUESTO_ID"));
                String desc = row.get("VC_PUESTO_DESC") == null ? "" : String.valueOf(row.get("VC_PUESTO_DESC"));
                if (puestoId == null) {
                    continue;
                }
                if (desc.contains("GERENTE")) {
                    puestos.put("GERENTE", puestoId);
                } else if (desc.contains("EJECUTIVO")) {
                    puestos.put("EJECUTIVO", puestoId);
                } else if (desc.contains("CAJERO")) {
                    puestos.put("CAJERO", puestoId);
                } else if (desc.contains("PROMOTOR") || desc.contains("JR")) {
                    puestos.put("PROMOTOR_JR", puestoId);
                }
            }
        } catch (Exception e) {
            logger.warn("No se pudieron detectar puestos desde TBL_MC_EMPLEADO_UBICACION: " + e.getMessage());
        }
        return puestos;
    }

    private boolean hayDatosTabla(String tableName) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM SIB." + tableName, Integer.class);
        return count != null && count > 0;
    }

    private int contarTabla(String tableName) {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM SIB." + tableName, Integer.class);
            return count == null ? 0 : count.intValue();
        } catch (Exception e) {
            logger.warn("No se pudo contar la tabla " + tableName + ": " + e.getMessage());
            return 0;
        }
    }

    public List<RegionRelacionDto> obtenerRegiones() {
        inicializarTablasCatalogo();
        if (hayDatosTabla("TBL_MC_SUCURSAL_REGION")) {
            String sql = "SELECT DISTINCT I_REGION_NUM AS regionId, COALESCE(MAX(VC_REGION_NOM), CONCAT('REGION ', I_REGION_NUM)) AS nombreRegion "
                    + "FROM SIB.TBL_MC_SUCURSAL_REGION "
                    + "GROUP BY I_REGION_NUM "
                    + "ORDER BY I_REGION_NUM";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                RegionRelacionDto dto = new RegionRelacionDto();
                dto.setRegionId(rs.getInt("regionId"));
                dto.setNombreRegion(rs.getString("nombreRegion"));
                return dto;
            });
        }

        String sql = "SELECT DISTINCT s.I_REGIONID AS regionId, CONCAT('REGION ', s.I_REGIONID) AS nombreRegion "
                + "FROM SIB.TBL_SUCURSALES s "
                + "WHERE s.I_REGIONID IS NOT NULL "
                + "ORDER BY s.I_REGIONID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RegionRelacionDto dto = new RegionRelacionDto();
            dto.setRegionId(rs.getInt("regionId"));
            dto.setNombreRegion(rs.getString("nombreRegion"));
            return dto;
        });
    }

    public List<SucursalRelacionDto> obtenerSucursalesPorRegion(Integer regionId) {
        inicializarTablasCatalogo();
        if (hayDatosTabla("TBL_MC_SUCURSAL_REGION")) {
            String sql = "SELECT I_SUCURSAL_NUM AS sucursalId, VC_SUCURSAL_NOM AS nombreSucursal "
                    + "FROM SIB.TBL_MC_SUCURSAL_REGION "
                    + "WHERE I_REGION_NUM = ? "
                    + "ORDER BY VC_SUCURSAL_NOM";
            return jdbcTemplate.query(sql, new Object[] { regionId }, (rs, rowNum) -> {
                SucursalRelacionDto dto = new SucursalRelacionDto();
                dto.setSucursalId(rs.getInt("sucursalId"));
                dto.setNombreSucursal(rs.getString("nombreSucursal"));
                return dto;
            });
        }

        String sql = "SELECT s.PK_ID_SUCURSAL AS sucursalId, s.VC_NOMBRESUCURSAL AS nombreSucursal "
                + "FROM SIB.TBL_SUCURSALES s "
                + "WHERE s.I_REGIONID = ? "
                + "ORDER BY s.VC_NOMBRESUCURSAL";

        return jdbcTemplate.query(sql, new Object[] { regionId }, (rs, rowNum) -> {
            SucursalRelacionDto dto = new SucursalRelacionDto();
            dto.setSucursalId(rs.getInt("sucursalId"));
            dto.setNombreSucursal(rs.getString("nombreSucursal"));
            return dto;
        });
    }

    public List<ColaboradorRelacionDto> obtenerColaboradoresPorSucursal(Integer sucursalId) {
        inicializarTablasCatalogo();
        if (hayDatosTabla("TBL_MC_EMPLEADO_UBICACION")) {
            String sql = "SELECT DISTINCT I_EMPLEADO_NUM AS colaboradorId, VC_EMPLEADO_NOM AS nombreColaborador "
                    + "FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                    + "WHERE I_SUCURSAL_NUM = ? "
                    + "AND (VC_APLICA_META IS NULL OR UPPER(VC_APLICA_META) = 'SI') "
                    + "ORDER BY VC_EMPLEADO_NOM";
            return jdbcTemplate.query(sql, new Object[] { sucursalId }, (rs, rowNum) -> {
                ColaboradorRelacionDto dto = new ColaboradorRelacionDto();
                dto.setColaboradorId(rs.getInt("colaboradorId"));
                dto.setNombreColaborador(rs.getString("nombreColaborador"));
                return dto;
            });
        }

        String sql = "SELECT e.EmpleadoID AS colaboradorId, "
                + "CONCAT_WS(' ', e.PrimerNombre, e.SegundoNombre, e.ApellidoPat, e.ApellidoMat) AS nombreColaborador "
                + "FROM CE.TBL_MICROFIN_EMPLEADOS e "
                + "WHERE e.Estatus = 'A' AND e.SucursalID = ? "
                + "ORDER BY nombreColaborador";

        return jdbcTemplate.query(sql, new Object[] { sucursalId }, (rs, rowNum) -> {
            ColaboradorRelacionDto dto = new ColaboradorRelacionDto();
            dto.setColaboradorId(rs.getInt("colaboradorId"));
            dto.setNombreColaborador(rs.getString("nombreColaborador"));
            return dto;
        });
    }

    public List<PuestoRelacionDto> obtenerPuestosPorColaborador(Integer colaboradorId) {
        inicializarTablasCatalogo();
        if (hayDatosTabla("TBL_MC_EMPLEADO_UBICACION")) {
            String sql = "SELECT I_PUESTO_ID AS clavePuestoId, VC_PUESTO_DESC AS nombrePuesto "
                    + "FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                    + "WHERE I_EMPLEADO_NUM = ? "
                    + "LIMIT 1";
            return jdbcTemplate.query(sql, new Object[] { colaboradorId }, (rs, rowNum) -> {
                PuestoRelacionDto dto = new PuestoRelacionDto();
                dto.setClavePuestoId(rs.getInt("clavePuestoId"));
                dto.setNombrePuesto(rs.getString("nombrePuesto"));
                return dto;
            });
        }

        String sql = "SELECT p.ClavePuestoID AS clavePuestoId, p.Descripcion AS nombrePuesto "
                + "FROM CE.TBL_MICROFIN_EMPLEADOS e "
                + "INNER JOIN CE.TBL_MICROFIN_PUESTOS p ON p.ClavePuestoID = e.ClavePuestoID "
                + "WHERE e.EmpleadoID = ? "
                + "LIMIT 1";

        return jdbcTemplate.query(sql, new Object[] { colaboradorId }, (rs, rowNum) -> {
            PuestoRelacionDto dto = new PuestoRelacionDto();
            dto.setClavePuestoId(rs.getInt("clavePuestoId"));
            dto.setNombrePuesto(rs.getString("nombrePuesto"));
            return dto;
        });
    }

    public List<PuestoRelacionDto> obtenerPuestosPorSucursal(Integer sucursalId) {
        inicializarTablasCatalogo();
        if (hayDatosTabla("TBL_MC_EMPLEADO_UBICACION")) {
            String sql = "SELECT x.clavePuestoId, x.nombrePuesto "
                    + "FROM ("
                    + "SELECT e.I_PUESTO_ID AS clavePuestoId, MAX(COALESCE(e.VC_PUESTO_DESC, CONCAT('PUESTO ', e.I_PUESTO_ID))) AS nombrePuesto "
                    + "FROM SIB.TBL_MC_EMPLEADO_UBICACION e "
                    + "WHERE e.I_SUCURSAL_NUM = ? "
                    + "AND e.I_PUESTO_ID IS NOT NULL "
                    + "AND (e.VC_APLICA_META IS NULL OR UPPER(e.VC_APLICA_META) = 'SI') "
                    + "GROUP BY e.I_PUESTO_ID "
                    + "UNION "
                    + "SELECT d.I_CLAVE_PUESTO_ID AS clavePuestoId, MAX(COALESCE(e2.VC_PUESTO_DESC, CONCAT('PUESTO ', d.I_CLAVE_PUESTO_ID))) AS nombrePuesto "
                    + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO d "
                    + "LEFT JOIN SIB.TBL_MC_EMPLEADO_UBICACION e2 "
                    + "ON e2.I_SUCURSAL_NUM = d.I_SUCURSAL_NUM AND e2.I_PUESTO_ID = d.I_CLAVE_PUESTO_ID "
                    + "WHERE d.I_SUCURSAL_NUM = ? "
                    + "GROUP BY d.I_CLAVE_PUESTO_ID "
                    + ") x "
                    + "ORDER BY x.nombrePuesto";
            return jdbcTemplate.query(sql, new Object[] { sucursalId, sucursalId }, (rs, rowNum) -> {
                PuestoRelacionDto dto = new PuestoRelacionDto();
                dto.setClavePuestoId(rs.getInt("clavePuestoId"));
                dto.setNombrePuesto(rs.getString("nombrePuesto"));
                return dto;
            });
        }
        return List.of();
    }

    public List<ProductoRelacionDto> obtenerProductosPorPuesto(Integer clavePuestoId) {
        inicializarTablaRelacionPuestoProducto();

        String sql = "SELECT rel.FK_ID_PRODUCTO AS productoId, "
                + "COALESCE(p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO) AS numeroProducto, "
                + "COALESCE(MAX(det.VC_PRODUCTO_DET_NOM), p.VC_NOMBRE_PRODUCTO, CONCAT('PRODUCTO ', COALESCE(rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO))) AS nombreProducto "
                + "FROM SIB.TBL_MC_PUESTO_PRODUCTO rel "
                + "LEFT JOIN SIB.TBL_CAT_PRODUCTO p ON p.PK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                + "LEFT JOIN SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO det ON det.I_CLAVE_PUESTO_ID = rel.I_CLAVE_PUESTO_ID "
                + "AND det.FK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                + "WHERE rel.I_CLAVE_PUESTO_ID = ? AND rel.I_ESTATUS = 1 "
                + "GROUP BY rel.FK_ID_PRODUCTO, p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, p.VC_NOMBRE_PRODUCTO "
                + "ORDER BY nombreProducto";

        return jdbcTemplate.query(sql, new Object[] { clavePuestoId }, (rs, rowNum) -> {
            ProductoRelacionDto dto = new ProductoRelacionDto();
            dto.setProductoId(rs.getInt("productoId"));
            dto.setNumeroProducto(rs.getInt("numeroProducto"));
            dto.setNombreProducto(rs.getString("nombreProducto"));
            return dto;
        });
    }

    public List<ProductoRelacionDto> obtenerProductosDisponibles() {
        String sql = "SELECT p.PK_ID_PRODUCTO AS productoId, p.I_NO_PRODUCTO AS numeroProducto, p.VC_NOMBRE_PRODUCTO AS nombreProducto "
                + "FROM SIB.TBL_CAT_PRODUCTO p "
                + "WHERE p.I_ESTATUS = 1 "
                + "ORDER BY p.VC_NOMBRE_PRODUCTO";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProductoRelacionDto dto = new ProductoRelacionDto();
            dto.setProductoId(rs.getInt("productoId"));
            dto.setNumeroProducto(rs.getInt("numeroProducto"));
            dto.setNombreProducto(rs.getString("nombreProducto"));
            return dto;
        });
    }

    public int asignarProductoAPuesto(Integer clavePuestoId, Integer productoId) {
        inicializarTablaRelacionPuestoProducto();

        String sql = "INSERT INTO SIB.TBL_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_ESTATUS, DT_FECHA_BAJA) "
                + "VALUES (?, ?, 1, NULL) "
                + "ON DUPLICATE KEY UPDATE I_ESTATUS = 1, DT_FECHA_BAJA = NULL";
        return jdbcTemplate.update(sql, clavePuestoId, productoId);
    }

    public int desasignarProductoDePuesto(Integer clavePuestoId, Integer productoId) {
        inicializarTablaRelacionPuestoProducto();

        String sql = "UPDATE SIB.TBL_MC_PUESTO_PRODUCTO "
                + "SET I_ESTATUS = 0, DT_FECHA_BAJA = NOW() "
                + "WHERE I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ?";
        return jdbcTemplate.update(sql, clavePuestoId, productoId);
    }

    public List<Map<String, Object>> obtenerVistaPreviaSucursales(int limit) {
        inicializarTablasCatalogo();
        return jdbcTemplate.queryForList(
                "SELECT I_REGION_NUM AS region, I_SUCURSAL_NUM AS sucursal, VC_SUCURSAL_NOM AS nombreSucursal, VC_REGION_NOM AS nombreRegion "
                        + "FROM SIB.TBL_MC_SUCURSAL_REGION ORDER BY I_REGION_NUM, I_SUCURSAL_NUM LIMIT ?",
                limit);
    }

    public List<Map<String, Object>> obtenerVistaPreviaColaboradores(int limit) {
        inicializarTablasCatalogo();
        return jdbcTemplate.queryForList(
                "SELECT I_EMPLEADO_NUM AS empleadoNum, VC_EMPLEADO_NOM AS empleadoNom, I_SUCURSAL_NUM AS sucursal, VC_SUCURSAL_NOM AS sucursalNom, "
                        + "I_PUESTO_ID AS puestoId, VC_PUESTO_DESC AS puestoDesc "
                        + "FROM SIB.TBL_MC_EMPLEADO_UBICACION ORDER BY I_REGION_NUM, I_SUCURSAL_NUM, VC_EMPLEADO_NOM LIMIT ?",
                limit);
    }

    public List<Map<String, Object>> obtenerVistaPreviaPuestoProducto(int limit) {
        inicializarTablaRelacionPuestoProducto();
        return jdbcTemplate.queryForList(
                "SELECT rel.I_SUCURSAL_NUM AS sucursalNum, rel.I_CLAVE_PUESTO_ID AS puestoId, COALESCE(p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO) AS productoNum, "
                        + "COALESCE(rel.VC_PRODUCTO_DET_NOM, p.VC_NOMBRE_PRODUCTO) AS productoNom, "
                        + "rel.D_PORC_NUEVO AS porcNuevo, rel.D_PORC_RENOVADO AS porcRenovado "
                        + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO rel "
                        + "LEFT JOIN SIB.TBL_CAT_PRODUCTO p ON p.PK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                        + "ORDER BY rel.I_SUCURSAL_NUM, rel.I_CLAVE_PUESTO_ID, productoNum LIMIT ?",
                limit);
    }

    public Map<String, Object> obtenerDetallePuestoProducto(int page, int size, String search) {
        inicializarTablaRelacionPuestoProducto();

        int safePage = page < 1 ? 1 : page;
        int safeSize = size < 1 ? 100 : Math.min(size, 1000);
        int offset = (safePage - 1) * safeSize;

        String baseFrom = " FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO rel "
                + "LEFT JOIN SIB.TBL_CAT_PRODUCTO p ON p.PK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                + "LEFT JOIN SIB.TBL_MC_SUCURSAL_REGION suc ON suc.I_SUCURSAL_NUM = rel.I_SUCURSAL_NUM "
                + "LEFT JOIN ("
                + "SELECT I_SUCURSAL_NUM, I_PUESTO_ID, "
                + "GROUP_CONCAT(DISTINCT VC_EMPLEADO_NOM ORDER BY VC_EMPLEADO_NOM SEPARATOR ', ') AS empleados, "
                + "MAX(VC_PUESTO_DESC) AS puestoNom "
                + "FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                + "GROUP BY I_SUCURSAL_NUM, I_PUESTO_ID"
                + ") emp ON emp.I_SUCURSAL_NUM = rel.I_SUCURSAL_NUM AND emp.I_PUESTO_ID = rel.I_CLAVE_PUESTO_ID ";

        List<Object> params = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            where.append(" AND (CAST(rel.I_SUCURSAL_NUM AS CHAR) LIKE ? "
                    + "OR CAST(rel.I_CLAVE_PUESTO_ID AS CHAR) LIKE ? "
                    + "OR CAST(COALESCE(p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO) AS CHAR) LIKE ? "
                    + "OR UPPER(COALESCE(rel.VC_PRODUCTO_DET_NOM, p.VC_NOMBRE_PRODUCTO, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(emp.empleados, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(suc.VC_SUCURSAL_NOM, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(emp.puestoNom, '')) LIKE UPPER(?)) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }

        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) " + baseFrom + where, params.toArray(), Integer.class);
        int totalRows = total == null ? 0 : total.intValue();
        int totalPages = totalRows == 0 ? 1 : (int) Math.ceil((double) totalRows / safeSize);

        String sql = "SELECT rel.PK_ID AS id, rel.I_SUCURSAL_NUM AS sucursalNum, rel.I_CLAVE_PUESTO_ID AS puestoId, "
                + "COALESCE(suc.VC_SUCURSAL_NOM, '') AS sucursalNom, "
                + "COALESCE(p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO) AS productoNum, "
                + "COALESCE(emp.empleados, '') AS empleadoNom, "
                + "COALESCE(emp.puestoNom, '') AS puestoNom, "
                + "COALESCE(rel.VC_PRODUCTO_DET_NOM, p.VC_NOMBRE_PRODUCTO) AS productoNom, "
                + "rel.D_PORC_NUEVO AS porcNuevo, rel.D_PORC_RENOVADO AS porcRenovado, "
                + "rel.VC_CANAL AS canal, rel.VC_FUERZA_VENTA AS fuerzaVenta, rel.DT_VIGENCIA_INICIO AS vigenciaInicio, "
                + "rel.DT_VIGENCIA_FIN AS vigenciaFin, rel.I_ESTATUS AS estatus, rel.I_VERSION AS version "
                + baseFrom + where
                + " ORDER BY rel.I_SUCURSAL_NUM, rel.I_CLAVE_PUESTO_ID, productoNum LIMIT ? OFFSET ?";

        List<Object> dataParams = new ArrayList<>(params);
        dataParams.add(safeSize);
        dataParams.add(offset);

        List<Map<String, Object>> items = jdbcTemplate.queryForList(sql, dataParams.toArray());

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("page", safePage);
        out.put("size", safeSize);
        out.put("total", totalRows);
        out.put("totalPages", totalPages);
        out.put("items", items);
        return out;
    }

    public Map<String, Object> actualizarDetallePuestoProducto(Long id, Map<String, Object> body) {
        inicializarTablaRelacionPuestoProducto();

        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        Integer puestoId = toInteger(body.get("puestoId"));
        String productoNom = trim(body.get("productoNom") == null ? null : String.valueOf(body.get("productoNom")));
        Double porcNuevo = toDouble(body.get("porcNuevo"));
        Double porcRenovado = toDouble(body.get("porcRenovado"));
        String canal = trim(body.get("canal") == null ? null : String.valueOf(body.get("canal")));
        String fuerzaVenta = trim(body.get("fuerzaVenta") == null ? null : String.valueOf(body.get("fuerzaVenta")));
        String vigenciaInicio = trim(body.get("vigenciaInicio") == null ? null : String.valueOf(body.get("vigenciaInicio")));
        String vigenciaFin = trim(body.get("vigenciaFin") == null ? null : String.valueOf(body.get("vigenciaFin")));
        Integer estatus = body.containsKey("estatus") ? toInteger(body.get("estatus")) : null;

        Integer oldSucursal = null;
        Integer oldPuesto = null;
        Integer fkProducto = null;
        String oldProductoNom = null;
        Double oldPorcNuevo = 0.0;
        Double oldPorcRenovado = 0.0;
        String oldCanal = null;
        String oldFuerzaVenta = null;
        String oldVigenciaInicio = null;
        String oldVigenciaFin = null;
        Integer oldEstatus = 1;
        try {
            Map<String, Object> old = jdbcTemplate.queryForMap(
                    "SELECT I_SUCURSAL_NUM AS sucursalNum, I_CLAVE_PUESTO_ID AS puestoId, FK_ID_PRODUCTO AS fkProducto, "
                            + "VC_PRODUCTO_DET_NOM AS productoNom, D_PORC_NUEVO AS porcNuevo, D_PORC_RENOVADO AS porcRenovado, "
                            + "VC_CANAL AS canal, VC_FUERZA_VENTA AS fuerzaVenta, DT_VIGENCIA_INICIO AS vigenciaInicio, "
                            + "DT_VIGENCIA_FIN AS vigenciaFin, I_ESTATUS AS estatus "
                            + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE PK_ID = ?",
                    id);
            oldSucursal = toInteger(old.get("sucursalNum"));
            oldPuesto = toInteger(old.get("puestoId"));
            fkProducto = toInteger(old.get("fkProducto"));
            oldProductoNom = trim(old.get("productoNom") == null ? null : String.valueOf(old.get("productoNom")));
            oldPorcNuevo = toDouble(old.get("porcNuevo"));
            oldPorcRenovado = toDouble(old.get("porcRenovado"));
            oldCanal = trim(old.get("canal") == null ? null : String.valueOf(old.get("canal")));
            oldFuerzaVenta = trim(old.get("fuerzaVenta") == null ? null : String.valueOf(old.get("fuerzaVenta")));
            oldVigenciaInicio = old.get("vigenciaInicio") == null ? null : String.valueOf(old.get("vigenciaInicio"));
            oldVigenciaFin = old.get("vigenciaFin") == null ? null : String.valueOf(old.get("vigenciaFin"));
            oldEstatus = toInteger(old.get("estatus"));
        } catch (Exception ignore) {
        }

        Integer sucursalFinal = sucursalNum != null ? sucursalNum : oldSucursal;
        Integer puestoFinal = puestoId != null ? puestoId : oldPuesto;
        String productoNomFinal = productoNom != null ? productoNom : oldProductoNom;
        Double porcNuevoFinal = body.containsKey("porcNuevo") ? porcNuevo : oldPorcNuevo;
        Double porcRenovadoFinal = body.containsKey("porcRenovado") ? porcRenovado : oldPorcRenovado;
        String canalFinal = canal != null ? canal : oldCanal;
        String fuerzaVentaFinal = fuerzaVenta != null ? fuerzaVenta : oldFuerzaVenta;
        String vigenciaInicioFinal = vigenciaInicio != null ? vigenciaInicio : oldVigenciaInicio;
        String vigenciaFinFinal = vigenciaFin != null ? vigenciaFin : oldVigenciaFin;
        Integer estatusFinal = estatus != null ? estatus : oldEstatus;

        if (!porcentajeValido(porcNuevoFinal) || !porcentajeValido(porcRenovadoFinal)) {
            throw new IllegalArgumentException("Los porcentajes deben estar en el rango [0,100]");
        }
        validarRangoVigencia(vigenciaInicioFinal, vigenciaFinFinal);

        int updated = 0;
        if (fkProducto != null && sucursalFinal != null && puestoFinal != null) {
            List<Map<String, Object>> destino = jdbcTemplate.queryForList(
                    "SELECT PK_ID AS id FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO "
                            + "WHERE I_SUCURSAL_NUM = ? AND I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ? AND PK_ID <> ? LIMIT 1",
                    sucursalFinal, puestoFinal, fkProducto, id);

            Long destinoId = destino.isEmpty() ? null : ((Number) destino.get(0).get("id")).longValue();
            validarTotalesProductoSucursal(sucursalFinal, fkProducto, porcNuevoFinal, porcRenovadoFinal, id, destinoId);

            if (!destino.isEmpty()) {
                registrarHistoricoDetallePuestoProducto(destinoId, "ANTES_UPDATE");
                updated += jdbcTemplate.update(
                        "UPDATE SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO "
                                + "SET VC_PRODUCTO_DET_NOM = ?, D_PORC_NUEVO = ?, D_PORC_RENOVADO = ?, "
                                + "VC_CANAL = COALESCE(?, VC_CANAL), VC_FUERZA_VENTA = COALESCE(?, VC_FUERZA_VENTA), "
                                + "DT_VIGENCIA_INICIO = ?, DT_VIGENCIA_FIN = ?, I_ESTATUS = COALESCE(?, I_ESTATUS), "
                                + "VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW(), I_VERSION = COALESCE(I_VERSION,1) + 1 "
                                + "WHERE PK_ID = ?",
                        productoNomFinal, porcNuevoFinal, porcRenovadoFinal, canalFinal, fuerzaVentaFinal,
                        toSqlDateOrNull(vigenciaInicioFinal), toSqlDateOrNull(vigenciaFinFinal), estatusFinal,
                        obtenerUsuarioActual(), destinoId);
                registrarHistoricoDetallePuestoProducto(destinoId, "DESPUES_UPDATE");
                updated += jdbcTemplate.update("DELETE FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE PK_ID = ?", id);
            } else {
                registrarHistoricoDetallePuestoProducto(id, "ANTES_UPDATE");
                String sql = "UPDATE SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO "
                        + "SET I_SUCURSAL_NUM = ?, I_CLAVE_PUESTO_ID = COALESCE(?, I_CLAVE_PUESTO_ID), "
                        + "VC_PRODUCTO_DET_NOM = ?, D_PORC_NUEVO = ?, D_PORC_RENOVADO = ?, "
                        + "VC_CANAL = COALESCE(?, VC_CANAL), VC_FUERZA_VENTA = COALESCE(?, VC_FUERZA_VENTA), "
                        + "DT_VIGENCIA_INICIO = ?, DT_VIGENCIA_FIN = ?, I_ESTATUS = COALESCE(?, I_ESTATUS), "
                        + "VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW(), I_VERSION = COALESCE(I_VERSION,1) + 1 "
                        + "WHERE PK_ID = ?";
                updated = jdbcTemplate.update(sql, sucursalFinal, puestoFinal, productoNomFinal, porcNuevoFinal, porcRenovadoFinal,
                        canalFinal, fuerzaVentaFinal, toSqlDateOrNull(vigenciaInicioFinal), toSqlDateOrNull(vigenciaFinFinal),
                        estatusFinal, obtenerUsuarioActual(), id);
                registrarHistoricoDetallePuestoProducto(id, "DESPUES_UPDATE");
            }
        }

        if (fkProducto != null && puestoFinal != null) {
            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_ESTATUS, DT_FECHA_BAJA) "
                            + "VALUES (?, ?, 1, NULL) "
                            + "ON DUPLICATE KEY UPDATE I_ESTATUS = 1, DT_FECHA_BAJA = NULL",
                    puestoFinal, fkProducto);

            if (oldPuesto != null && !oldPuesto.equals(puestoFinal)) {
                Integer remain = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ?",
                        Integer.class, oldPuesto, fkProducto);
                if (remain != null && remain == 0) {
                    jdbcTemplate.update(
                            "UPDATE SIB.TBL_MC_PUESTO_PRODUCTO SET I_ESTATUS = 0, DT_FECHA_BAJA = NOW() WHERE I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ?",
                            oldPuesto, fkProducto);
                }
            }
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("updated", updated);
        out.put("id", id);
        registrarAuditoria("UPDATE", "SUCURSAL_PUESTO_PRODUCTO", id,
                "sucursal=" + sucursalFinal + ", puesto=" + puestoFinal + ", producto=" + fkProducto
                        + ", nuevo=" + porcNuevoFinal + ", renovado=" + porcRenovadoFinal + ", canal=" + canalFinal
                        + ", fuerza=" + fuerzaVentaFinal + ", estatus=" + estatusFinal + ", updated=" + updated);
        return out;
    }

    public Map<String, Object> obtenerDetalleRegionSucursal(int page, int size, String search, String canal, String estatus) {
        inicializarTablasCatalogo();
        int safePage = page < 1 ? 1 : page;
        int safeSize = size < 1 ? 50 : Math.min(size, 500);
        int offset = (safePage - 1) * safeSize;

        String baseFrom = " FROM SIB.TBL_MC_SUCURSAL_REGION r ";
        List<Object> params = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            where.append(" AND (CAST(r.I_REGION_NUM AS CHAR) LIKE ? "
                    + "OR CAST(r.I_SUCURSAL_NUM AS CHAR) LIKE ? "
                    + "OR UPPER(COALESCE(r.VC_SUCURSAL_NOM, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(r.VC_REGION_NOM, '')) LIKE UPPER(?)) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }
        String canalNorm = normalizarCanales(canal);
        if (canalNorm != null) {
            where.append(" AND UPPER(COALESCE(r.VC_CANALES, 'TRADICIONAL')) = ? ");
            params.add(canalNorm);
        }
        String estatusNorm = normalizarEstatusSql(estatus);
        if (estatusNorm != null) {
            where.append(" AND UPPER(COALESCE(r.VC_SUCURSAL_ESTATUS, '')) LIKE ? ");
            params.add("%" + estatusNorm + "%");
        }

        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) " + baseFrom + where, params.toArray(), Integer.class);
        int totalRows = total == null ? 0 : total.intValue();
        int totalPages = totalRows == 0 ? 1 : (int) Math.ceil((double) totalRows / safeSize);

        String sql = "SELECT r.PK_ID AS id, r.I_REGION_NUM AS regionNum, r.VC_REGION_NOM AS regionNom, "
                + "r.I_SUCURSAL_NUM AS sucursalNum, r.VC_SUCURSAL_NOM AS sucursalNom, r.VC_SUCURSAL_ESTATUS AS sucursalEstatus, "
                + "r.VC_CANALES AS canales, r.I_VERSION AS version, r.VC_USUARIO_MODIFICACION AS usuarioModificacion, "
                + "r.DT_FECHA_MODIFICACION AS fechaModificacion "
                + baseFrom + where
                + " ORDER BY r.I_REGION_NUM, r.I_SUCURSAL_NUM LIMIT ? OFFSET ?";

        List<Object> dataParams = new ArrayList<>(params);
        dataParams.add(safeSize);
        dataParams.add(offset);

        List<Map<String, Object>> items = jdbcTemplate.queryForList(sql, dataParams.toArray());
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("page", safePage);
        out.put("size", safeSize);
        out.put("total", totalRows);
        out.put("totalPages", totalPages);
        out.put("items", items);
        return out;
    }

    public Map<String, Object> actualizarDetalleRegionSucursal(Long id, Map<String, Object> body) {
        inicializarTablasCatalogo();
        Integer regionNum = toInteger(body.get("regionNum"));
        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        String regionNom = trim(body.get("regionNom") == null ? null : String.valueOf(body.get("regionNom")));
        String sucursalNom = trim(body.get("sucursalNom") == null ? null : String.valueOf(body.get("sucursalNom")));
        String sucursalEstatus = trim(body.get("sucursalEstatus") == null ? null : String.valueOf(body.get("sucursalEstatus")));
        String canales = normalizarCanales(body.get("canales") == null ? null : String.valueOf(body.get("canales")));

        String sql = "UPDATE SIB.TBL_MC_SUCURSAL_REGION "
                + "SET I_REGION_NUM = COALESCE(?, I_REGION_NUM), "
                + "I_SUCURSAL_NUM = COALESCE(?, I_SUCURSAL_NUM), "
                + "VC_REGION_NOM = COALESCE(?, VC_REGION_NOM), "
                + "VC_SUCURSAL_NOM = COALESCE(?, VC_SUCURSAL_NOM), "
                + "VC_SUCURSAL_ESTATUS = COALESCE(?, VC_SUCURSAL_ESTATUS), "
                + "VC_CANALES = COALESCE(?, VC_CANALES), "
                + "I_VERSION = COALESCE(I_VERSION, 1) + 1, "
                + "VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() "
                + "WHERE PK_ID = ?";
        int updated = jdbcTemplate.update(sql, regionNum, sucursalNum, regionNom, sucursalNom, sucursalEstatus, canales,
                obtenerUsuarioActual(), id);

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("updated", updated);
        out.put("id", id);
        out.put("sucursalEstatus", sucursalEstatus);
        out.put("canales", canales);
        registrarAuditoria("UPDATE", "SUCURSAL_REGION", id,
                "region=" + regionNum + ", sucursal=" + sucursalNum + ", estatus=" + sucursalEstatus + ", canales=" + canales + ", updated=" + updated);
        return out;
    }

    public List<Map<String, Object>> obtenerDetalleRegionSucursalPorSucursal(Integer sucursalId) {
        inicializarTablasCatalogo();
        return jdbcTemplate.queryForList(
                "SELECT PK_ID AS id, I_REGION_NUM AS regionNum, VC_REGION_NOM AS regionNom, "
                        + "I_SUCURSAL_NUM AS sucursalNum, VC_SUCURSAL_NOM AS sucursalNom, VC_SUCURSAL_ESTATUS AS sucursalEstatus, "
                        + "VC_CANALES AS canales, I_VERSION AS version, VC_USUARIO_MODIFICACION AS usuarioModificacion, DT_FECHA_MODIFICACION AS fechaModificacion "
                        + "FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_SUCURSAL_NUM = ? ORDER BY PK_ID",
                sucursalId);
    }

    public Map<String, Object> altaRegion(Map<String, Object> body) {
        inicializarTablasCatalogo();
        Integer regionNum = toInteger(body.get("regionNum"));
        String regionNom = trim(body.get("regionNom") == null ? null : String.valueOf(body.get("regionNom")));
        if (regionNum == null) {
            throw new IllegalArgumentException("regionNum es requerido");
        }

        Integer existe = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_REGION_NUM = ?",
                Integer.class, regionNum);
        if (existe == null || existe == 0) {
            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_SUCURSAL_REGION "
                            + "(I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_REGION_NOM, VC_SUCURSAL_NOM, VC_SUCURSAL_ESTATUS, VC_CANALES, VC_ESTADO, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                            + "VALUES (1, ?, NULL, ?, NULL, 'ACTIVA', 'TRADICIONAL', NULL, 1, ?, ?, NOW())",
                    regionNum, regionNom, obtenerUsuarioActual(), obtenerUsuarioActual());
        } else {
            jdbcTemplate.update(
                    "UPDATE SIB.TBL_MC_SUCURSAL_REGION SET VC_REGION_NOM = COALESCE(?, VC_REGION_NOM), "
                            + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() WHERE I_REGION_NUM = ?",
                    regionNom, obtenerUsuarioActual(), regionNum);
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("ok", true);
        out.put("regionNum", regionNum);
        return out;
    }

    public Map<String, Object> altaSucursal(Map<String, Object> body) {
        inicializarTablasCatalogo();
        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        String sucursalNom = trim(body.get("sucursalNom") == null ? null : String.valueOf(body.get("sucursalNom")));
        String canales = normalizarCanales(body.get("canales") == null ? null : String.valueOf(body.get("canales")));
        if (sucursalNum == null) {
            throw new IllegalArgumentException("sucursalNum es requerido");
        }

        jdbcTemplate.update(
                "INSERT INTO SIB.TBL_MC_SUCURSAL_REGION "
                        + "(I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_REGION_NOM, VC_SUCURSAL_NOM, VC_SUCURSAL_ESTATUS, VC_CANALES, VC_ESTADO, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                        + "VALUES (1, NULL, ?, NULL, ?, 'ACTIVA', ?, NULL, 1, ?, ?, NOW()) "
                        + "ON DUPLICATE KEY UPDATE VC_SUCURSAL_NOM = VALUES(VC_SUCURSAL_NOM), VC_SUCURSAL_ESTATUS = 'ACTIVA', "
                        + "VC_CANALES = VALUES(VC_CANALES), I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = VALUES(VC_USUARIO_MODIFICACION), DT_FECHA_MODIFICACION = NOW()",
                sucursalNum, sucursalNom, canales, obtenerUsuarioActual(), obtenerUsuarioActual());

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("ok", true);
        out.put("sucursalNum", sucursalNum);
        out.put("canales", canales);
        registrarAuditoria("CREATE", "SUCURSAL_REGION", null,
                "sucursal=" + sucursalNum + ", canales=" + canales);
        return out;
    }

    public Map<String, Object> relacionarSucursalRegion(Map<String, Object> body) {
        inicializarTablasCatalogo();
        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        Integer regionNum = toInteger(body.get("regionNum"));
        if (sucursalNum == null || regionNum == null) {
            throw new IllegalArgumentException("sucursalNum y regionNum son requeridos");
        }

        String regionNom = null;
        try {
            regionNom = jdbcTemplate.queryForObject(
                    "SELECT MAX(VC_REGION_NOM) FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_REGION_NUM = ?",
                    String.class, regionNum);
        } catch (Exception ignore) {
        }

        int updated = jdbcTemplate.update(
                "UPDATE SIB.TBL_MC_SUCURSAL_REGION SET I_REGION_NUM = ?, VC_REGION_NOM = COALESCE(?, VC_REGION_NOM), "
                        + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() WHERE I_SUCURSAL_NUM = ?",
                regionNum, regionNom, obtenerUsuarioActual(), sucursalNum);

        if (updated == 0) {
            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_SUCURSAL_REGION "
                            + "(I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_REGION_NOM, VC_SUCURSAL_NOM, VC_SUCURSAL_ESTATUS, VC_CANALES, VC_ESTADO, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                            + "VALUES (1, ?, ?, ?, NULL, 'ACTIVA', 'TRADICIONAL', NULL, 1, ?, ?, NOW())",
                    regionNum, sucursalNum, regionNom, obtenerUsuarioActual(), obtenerUsuarioActual());
            updated = 1;
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("ok", true);
        out.put("updated", updated);
        registrarAuditoria("RELACIONAR", "SUCURSAL_REGION", null,
                "sucursal=" + sucursalNum + ", region=" + regionNum + ", updated=" + updated);
        return out;
    }

    public byte[] exportarRegionSucursalXlsx(String search, String canal, String estatus) throws IOException {
        List<Map<String, Object>> rows = consultarRegionSucursalExport(search, canal, estatus);
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("RegionSucursal");
            Row header = sheet.createRow(0);
            String[] cols = { "Region", "Nombre Region", "Sucursal", "Nombre Sucursal", "Canales", "Estatus", "Version",
                    "Usuario Modificacion", "Fecha Modificacion" };
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }
            int rowIdx = 1;
            for (Map<String, Object> r : rows) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(str(r.get("regionNum")));
                row.createCell(1).setCellValue(str(r.get("regionNom")));
                row.createCell(2).setCellValue(str(r.get("sucursalNum")));
                row.createCell(3).setCellValue(str(r.get("sucursalNom")));
                row.createCell(4).setCellValue(str(r.get("canales")));
                row.createCell(5).setCellValue(str(r.get("sucursalEstatus")));
                row.createCell(6).setCellValue(str(r.get("version")));
                row.createCell(7).setCellValue(str(r.get("usuarioModificacion")));
                row.createCell(8).setCellValue(str(r.get("fechaModificacion")));
            }
            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(baos);
            return baos.toByteArray();
        }
    }

    public byte[] exportarRegionSucursalPdf(String search, String canal, String estatus) throws Exception {
        List<Map<String, Object>> rows = consultarRegionSucursalExport(search, canal, estatus);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            document.add(new Paragraph("Metas y Comisiones - Region / Sucursal", titleFont));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] { 1.2f, 2.2f, 1.2f, 2.4f, 1.5f, 1.3f });
            addPdfHeader(table, "Region");
            addPdfHeader(table, "Nombre Region");
            addPdfHeader(table, "Sucursal");
            addPdfHeader(table, "Nombre Sucursal");
            addPdfHeader(table, "Canales");
            addPdfHeader(table, "Estatus");
            for (Map<String, Object> r : rows) {
                table.addCell(new Phrase(str(r.get("regionNum")), cellFont));
                table.addCell(new Phrase(str(r.get("regionNom")), cellFont));
                table.addCell(new Phrase(str(r.get("sucursalNum")), cellFont));
                table.addCell(new Phrase(str(r.get("sucursalNom")), cellFont));
                table.addCell(new Phrase(str(r.get("canales")), cellFont));
                table.addCell(new Phrase(str(r.get("sucursalEstatus")), cellFont));
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        }
    }

    private void addPdfHeader(PdfPTable table, String text) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        table.addCell(cell);
    }

    private List<Map<String, Object>> consultarRegionSucursalExport(String search, String canal, String estatus) {
        inicializarTablasCatalogo();
        StringBuilder sql = new StringBuilder(
                "SELECT r.PK_ID AS id, r.I_REGION_NUM AS regionNum, r.VC_REGION_NOM AS regionNom, "
                        + "r.I_SUCURSAL_NUM AS sucursalNum, r.VC_SUCURSAL_NOM AS sucursalNom, "
                        + "r.VC_CANALES AS canales, r.VC_SUCURSAL_ESTATUS AS sucursalEstatus, r.I_VERSION AS version, "
                        + "r.VC_USUARIO_MODIFICACION AS usuarioModificacion, r.DT_FECHA_MODIFICACION AS fechaModificacion "
                        + "FROM SIB.TBL_MC_SUCURSAL_REGION r WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            sql.append(" AND (CAST(r.I_REGION_NUM AS CHAR) LIKE ? OR CAST(r.I_SUCURSAL_NUM AS CHAR) LIKE ? "
                    + "OR UPPER(COALESCE(r.VC_SUCURSAL_NOM, '')) LIKE UPPER(?) OR UPPER(COALESCE(r.VC_REGION_NOM, '')) LIKE UPPER(?)) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }
        String canalNorm = normalizarCanales(canal);
        if (canalNorm != null) {
            sql.append(" AND UPPER(COALESCE(r.VC_CANALES, 'TRADICIONAL')) = ? ");
            params.add(canalNorm);
        }
        String estatusNorm = normalizarEstatusSql(estatus);
        if (estatusNorm != null) {
            sql.append(" AND UPPER(COALESCE(r.VC_SUCURSAL_ESTATUS, '')) LIKE ? ");
            params.add("%" + estatusNorm + "%");
        }
        sql.append(" ORDER BY r.I_REGION_NUM, r.I_SUCURSAL_NUM ");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    public Map<String, Object> obtenerDetalleEmpleadoSucursal(int page, int size, String search, Integer regionNum,
            Integer sucursalNum, Integer puestoId, String vigencia) {
        inicializarTablasCatalogo();
        int safePage = page < 1 ? 1 : page;
        int safeSize = size < 1 ? 50 : Math.min(size, 500);
        int offset = (safePage - 1) * safeSize;

        String baseFrom = " FROM SIB.TBL_MC_EMPLEADO_UBICACION e ";
        List<Object> params = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            where.append(" AND (CAST(e.I_EMPLEADO_NUM AS CHAR) LIKE ? "
                    + "OR CAST(e.I_SUCURSAL_NUM AS CHAR) LIKE ? "
                    + "OR CAST(e.I_REGION_NUM AS CHAR) LIKE ? "
                    + "OR CAST(e.I_PUESTO_ID AS CHAR) LIKE ? "
                    + "OR UPPER(COALESCE(e.VC_EMPLEADO_NOM, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(e.VC_SUCURSAL_NOM, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(e.VC_PUESTO_DESC, '')) LIKE UPPER(?)) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }
        if (regionNum != null) {
            where.append(" AND e.I_REGION_NUM = ? ");
            params.add(regionNum);
        }
        if (sucursalNum != null) {
            where.append(" AND e.I_SUCURSAL_NUM = ? ");
            params.add(sucursalNum);
        }
        if (puestoId != null) {
            where.append(" AND e.I_PUESTO_ID = ? ");
            params.add(puestoId);
        }
        String vigenciaNorm = trim(vigencia);
        if (vigenciaNorm != null && !vigenciaNorm.isBlank()) {
            String v = vigenciaNorm.toUpperCase();
            if ("ACTUAL".equals(v)) {
                where.append(" AND (e.DT_VIGENCIA_INICIO IS NULL OR e.DT_VIGENCIA_INICIO <= CURDATE()) "
                        + "AND (e.DT_VIGENCIA_FIN IS NULL OR e.DT_VIGENCIA_FIN >= CURDATE()) ");
            } else if ("HISTORICA".equals(v)) {
                where.append(" AND NOT ((e.DT_VIGENCIA_INICIO IS NULL OR e.DT_VIGENCIA_INICIO <= CURDATE()) "
                        + "AND (e.DT_VIGENCIA_FIN IS NULL OR e.DT_VIGENCIA_FIN >= CURDATE())) ");
            }
        }

        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) " + baseFrom + where, params.toArray(), Integer.class);
        int totalRows = total == null ? 0 : total.intValue();
        int totalPages = totalRows == 0 ? 1 : (int) Math.ceil((double) totalRows / safeSize);

        String sql = "SELECT e.PK_ID AS id, e.I_EMPLEADO_NUM AS empleadoNum, e.VC_EMPLEADO_NOM AS empleadoNom, "
                + "e.I_REGION_NUM AS regionNum, e.I_SUCURSAL_NUM AS sucursalNum, e.VC_SUCURSAL_NOM AS sucursalNom, "
                + "e.I_PUESTO_ID AS puestoId, e.VC_PUESTO_DESC AS puestoDesc, "
                + "e.VC_APLICA_META AS aplicaMeta, e.VC_ESTADO AS estado, e.I_PERIODO_NUM AS periodoNum, "
                + "e.DT_VIGENCIA_INICIO AS vigenciaInicio, e.DT_VIGENCIA_FIN AS vigenciaFin, e.I_VERSION AS version, "
                + "CASE WHEN (e.DT_VIGENCIA_INICIO IS NULL OR e.DT_VIGENCIA_INICIO <= CURDATE()) "
                + "AND (e.DT_VIGENCIA_FIN IS NULL OR e.DT_VIGENCIA_FIN >= CURDATE()) THEN 'ACTUAL' ELSE 'HISTORICA' END AS vigenciaEstado "
                + baseFrom + where
                + " ORDER BY e.I_REGION_NUM, e.I_SUCURSAL_NUM, e.VC_PUESTO_DESC, e.VC_EMPLEADO_NOM LIMIT ? OFFSET ?";

        List<Object> dataParams = new ArrayList<>(params);
        dataParams.add(safeSize);
        dataParams.add(offset);

        List<Map<String, Object>> items = jdbcTemplate.queryForList(sql, dataParams.toArray());
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("page", safePage);
        out.put("size", safeSize);
        out.put("total", totalRows);
        out.put("totalPages", totalPages);
        out.put("items", items);
        return out;
    }

    public Map<String, Object> actualizarDetalleEmpleadoSucursal(Long id, Map<String, Object> body) {
        inicializarTablasCatalogo();
        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        Integer empleadoNum = null;
        registrarHistoricoEmpleadoUbicacion(id, "ANTES_UPDATE");

        try {
            empleadoNum = jdbcTemplate.queryForObject(
                    "SELECT I_EMPLEADO_NUM FROM SIB.TBL_MC_EMPLEADO_UBICACION WHERE PK_ID = ?",
                    Integer.class, id);
        } catch (Exception e) {
            empleadoNum = null;
        }

        if (empleadoNum == null) {
            Map<String, Object> out = new LinkedHashMap<>();
            out.put("updated", 0);
            out.put("id", id);
            out.put("message", "No se encontro el empleado para el id proporcionado");
            return out;
        }

        Map<String, Object> sucInfo = null;
        if (sucursalNum != null) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT I_REGION_NUM AS regionNum, VC_SUCURSAL_NOM AS sucursalNom "
                            + "FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_SUCURSAL_NUM = ? LIMIT 1",
                    sucursalNum);
            if (!rows.isEmpty()) {
                sucInfo = rows.get(0);
            }
        }

        Integer regionNum = sucInfo == null ? null : toInteger(sucInfo.get("regionNum"));
        String sucursalNom = sucInfo == null ? null : trim(String.valueOf(sucInfo.get("sucursalNom")));

        String sql = "UPDATE SIB.TBL_MC_EMPLEADO_UBICACION "
                + "SET I_SUCURSAL_NUM = ?, I_REGION_NUM = COALESCE(?, I_REGION_NUM), VC_SUCURSAL_NOM = COALESCE(?, VC_SUCURSAL_NOM), "
                + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() "
                + "WHERE I_EMPLEADO_NUM = ?";
        int updated = jdbcTemplate.update(sql, sucursalNum, regionNum, sucursalNom, obtenerUsuarioActual(), empleadoNum);

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("updated", updated);
        out.put("id", id);
        out.put("empleadoNum", empleadoNum);
        registrarHistoricoEmpleadoUbicacion(id, "UPDATE");
        registrarAuditoria("UPDATE", "EMPLEADO_UBICACION", id,
                "empleadoNum=" + empleadoNum + ", sucursal=" + sucursalNum + ", region=" + regionNum + ", updated=" + updated);
        return out;
    }

    public Map<String, Object> altaDetalleEmpleadoSucursal(Map<String, Object> body) {
        inicializarTablasCatalogo();

        Integer empleadoNum = toInteger(body.get("empleadoNum"));
        if (empleadoNum == null) {
            throw new IllegalArgumentException("empleadoNum es requerido");
        }

        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        if (sucursalNum == null) {
            throw new IllegalArgumentException("sucursalNum es requerido");
        }

        String empleadoNom = trim(body.get("empleadoNom") == null ? null : String.valueOf(body.get("empleadoNom")));
        Integer puestoId = toInteger(body.get("puestoId"));
        String puestoDesc = trim(body.get("puestoDesc") == null ? null : String.valueOf(body.get("puestoDesc")));
        String aplicaMeta = trim(body.get("aplicaMeta") == null ? "SI" : String.valueOf(body.get("aplicaMeta")));
        String estado = trim(body.get("estado") == null ? "ACTIVO" : String.valueOf(body.get("estado")));
        Integer periodoNum = toInteger(body.get("periodoNum"));
        String vigenciaInicio = trim(body.get("vigenciaInicio") == null ? null : String.valueOf(body.get("vigenciaInicio")));
        String vigenciaFin = trim(body.get("vigenciaFin") == null ? null : String.valueOf(body.get("vigenciaFin")));
        validarRangoVigencia(vigenciaInicio, vigenciaFin);
        validarDuplicidadEmpleadoSucursalPuesto(null, empleadoNum, sucursalNum, puestoId);

        if (empleadoNom == null || empleadoNom.isBlank()) {
            empleadoNom = "EMPLEADO " + empleadoNum;
        }

        Map<String, Object> sucInfo = null;
        List<Map<String, Object>> sucRows = jdbcTemplate.queryForList(
                "SELECT I_REGION_NUM AS regionNum, VC_SUCURSAL_NOM AS sucursalNom "
                        + "FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_SUCURSAL_NUM = ? LIMIT 1",
                sucursalNum);
        if (!sucRows.isEmpty()) {
            sucInfo = sucRows.get(0);
        }

        Integer regionNum = sucInfo == null ? null : toInteger(sucInfo.get("regionNum"));
        String sucursalNom = sucInfo == null ? null : trim(String.valueOf(sucInfo.get("sucursalNom")));

        int inserted = jdbcTemplate.update(
                "INSERT INTO SIB.TBL_MC_EMPLEADO_UBICACION "
                        + "(I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, NOW())",
                empleadoNum, empleadoNom, regionNum, sucursalNum, sucursalNom, puestoId, puestoDesc, aplicaMeta, estado,
                periodoNum, toSqlDateOrNull(vigenciaInicio), toSqlDateOrNull(vigenciaFin), obtenerUsuarioActual(), obtenerUsuarioActual());

        Long id = null;
        try {
            id = jdbcTemplate.queryForObject("SELECT MAX(PK_ID) FROM SIB.TBL_MC_EMPLEADO_UBICACION WHERE I_EMPLEADO_NUM = ?",
                    Long.class, empleadoNum);
        } catch (Exception ignore) {
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("inserted", inserted);
        out.put("id", id);
        out.put("empleadoNum", empleadoNum);
        registrarHistoricoEmpleadoUbicacion(id, "CREATE");
        registrarAuditoria("CREATE", "EMPLEADO_UBICACION", id,
                "empleadoNum=" + empleadoNum + ", sucursal=" + sucursalNum + ", puesto=" + puestoId);
        return out;
    }

    public Map<String, Object> actualizarDetalleEmpleadoSucursalEdicion(Long id, Map<String, Object> body) {
        inicializarTablasCatalogo();

        Map<String, Object> actual = jdbcTemplate.queryForMap(
                "SELECT I_EMPLEADO_NUM AS empleadoNum, VC_EMPLEADO_NOM AS empleadoNom, I_REGION_NUM AS regionNum, "
                        + "I_SUCURSAL_NUM AS sucursalNum, VC_SUCURSAL_NOM AS sucursalNom, I_PUESTO_ID AS puestoId, "
                        + "VC_PUESTO_DESC AS puestoDesc, VC_APLICA_META AS aplicaMeta, VC_ESTADO AS estado, I_PERIODO_NUM AS periodoNum, "
                        + "DT_VIGENCIA_INICIO AS vigenciaInicio, DT_VIGENCIA_FIN AS vigenciaFin "
                        + "FROM SIB.TBL_MC_EMPLEADO_UBICACION WHERE PK_ID = ?",
                id);

        Integer empleadoNum = body.containsKey("empleadoNum") ? toInteger(body.get("empleadoNum")) : toInteger(actual.get("empleadoNum"));
        String empleadoNom = body.containsKey("empleadoNom")
                ? trim(body.get("empleadoNom") == null ? null : String.valueOf(body.get("empleadoNom")))
                : trim(actual.get("empleadoNom") == null ? null : String.valueOf(actual.get("empleadoNom")));
        Integer sucursalNum = body.containsKey("sucursalNum") ? toInteger(body.get("sucursalNum")) : toInteger(actual.get("sucursalNum"));
        Integer puestoId = body.containsKey("puestoId") ? toInteger(body.get("puestoId")) : toInteger(actual.get("puestoId"));
        String puestoDesc = body.containsKey("puestoDesc")
                ? trim(body.get("puestoDesc") == null ? null : String.valueOf(body.get("puestoDesc")))
                : trim(actual.get("puestoDesc") == null ? null : String.valueOf(actual.get("puestoDesc")));
        String aplicaMeta = body.containsKey("aplicaMeta")
                ? trim(body.get("aplicaMeta") == null ? null : String.valueOf(body.get("aplicaMeta")))
                : trim(actual.get("aplicaMeta") == null ? null : String.valueOf(actual.get("aplicaMeta")));
        String estado = body.containsKey("estado")
                ? trim(body.get("estado") == null ? null : String.valueOf(body.get("estado")))
                : trim(actual.get("estado") == null ? null : String.valueOf(actual.get("estado")));
        Integer periodoNum = body.containsKey("periodoNum") ? toInteger(body.get("periodoNum")) : toInteger(actual.get("periodoNum"));
        String vigenciaInicio = body.containsKey("vigenciaInicio")
                ? trim(body.get("vigenciaInicio") == null ? null : String.valueOf(body.get("vigenciaInicio")))
                : trim(actual.get("vigenciaInicio") == null ? null : String.valueOf(actual.get("vigenciaInicio")));
        String vigenciaFin = body.containsKey("vigenciaFin")
                ? trim(body.get("vigenciaFin") == null ? null : String.valueOf(body.get("vigenciaFin")))
                : trim(actual.get("vigenciaFin") == null ? null : String.valueOf(actual.get("vigenciaFin")));
        validarRangoVigencia(vigenciaInicio, vigenciaFin);

        Integer regionNum = toInteger(actual.get("regionNum"));
        String sucursalNom = trim(actual.get("sucursalNom") == null ? null : String.valueOf(actual.get("sucursalNom")));

        if (sucursalNum != null) {
            List<Map<String, Object>> sucRows = jdbcTemplate.queryForList(
                    "SELECT I_REGION_NUM AS regionNum, VC_SUCURSAL_NOM AS sucursalNom "
                            + "FROM SIB.TBL_MC_SUCURSAL_REGION WHERE I_SUCURSAL_NUM = ? LIMIT 1",
                    sucursalNum);
            if (!sucRows.isEmpty()) {
                regionNum = toInteger(sucRows.get(0).get("regionNum"));
                sucursalNom = trim(String.valueOf(sucRows.get(0).get("sucursalNom")));
            }
        }

        validarDuplicidadEmpleadoSucursalPuesto(id, empleadoNum, sucursalNum, puestoId);
        registrarHistoricoEmpleadoUbicacion(id, "ANTES_UPDATE");
        int updated = jdbcTemplate.update(
                "UPDATE SIB.TBL_MC_EMPLEADO_UBICACION "
                        + "SET I_EMPLEADO_NUM = ?, VC_EMPLEADO_NOM = ?, I_REGION_NUM = ?, I_SUCURSAL_NUM = ?, VC_SUCURSAL_NOM = ?, "
                        + "I_PUESTO_ID = ?, VC_PUESTO_DESC = ?, VC_APLICA_META = ?, VC_ESTADO = ?, I_PERIODO_NUM = ?, "
                        + "DT_VIGENCIA_INICIO = ?, DT_VIGENCIA_FIN = ?, I_VERSION = COALESCE(I_VERSION, 1) + 1, "
                        + "VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() "
                        + "WHERE PK_ID = ?",
                empleadoNum, empleadoNom, regionNum, sucursalNum, sucursalNom, puestoId, puestoDesc, aplicaMeta, estado,
                periodoNum, toSqlDateOrNull(vigenciaInicio), toSqlDateOrNull(vigenciaFin), obtenerUsuarioActual(), id);

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("updated", updated);
        out.put("id", id);
        registrarHistoricoEmpleadoUbicacion(id, "UPDATE");
        registrarAuditoria("UPDATE", "EMPLEADO_UBICACION", id,
                "edicionIndividual=true, empleadoNum=" + empleadoNum + ", sucursal=" + sucursalNum + ", puesto=" + puestoId);
        return out;
    }

    public List<Map<String, Object>> obtenerDetalleEmpleadoSucursalPorSucursal(Integer sucursalId) {
        inicializarTablasCatalogo();
        return jdbcTemplate.queryForList(
                "SELECT e.PK_ID AS id, e.I_EMPLEADO_NUM AS empleadoNum, e.VC_EMPLEADO_NOM AS empleadoNom, "
                        + "e.I_REGION_NUM AS regionNum, e.I_SUCURSAL_NUM AS sucursalNum, e.VC_SUCURSAL_NOM AS sucursalNom, "
                        + "e.I_PUESTO_ID AS puestoId, e.VC_PUESTO_DESC AS puestoDesc, "
                        + "e.VC_APLICA_META AS aplicaMeta, e.VC_ESTADO AS estado, e.I_PERIODO_NUM AS periodoNum, "
                        + "e.DT_VIGENCIA_INICIO AS vigenciaInicio, e.DT_VIGENCIA_FIN AS vigenciaFin, e.I_VERSION AS version, "
                        + "CASE WHEN (e.DT_VIGENCIA_INICIO IS NULL OR e.DT_VIGENCIA_INICIO <= CURDATE()) "
                        + "AND (e.DT_VIGENCIA_FIN IS NULL OR e.DT_VIGENCIA_FIN >= CURDATE()) THEN 'ACTUAL' ELSE 'HISTORICA' END AS vigenciaEstado "
                        + "FROM SIB.TBL_MC_EMPLEADO_UBICACION e WHERE e.I_SUCURSAL_NUM = ? "
                        + "ORDER BY e.VC_EMPLEADO_NOM",
                sucursalId);
    }

    public List<Map<String, Object>> obtenerHistorialEmpleadoSucursal(Long id) {
        inicializarTablasCatalogo();
        return jdbcTemplate.queryForList(
                "SELECT FK_ID_REGISTRO AS id, VC_EVENTO AS evento, I_EMPLEADO_NUM AS empleadoNum, VC_EMPLEADO_NOM AS empleadoNom, "
                        + "I_REGION_NUM AS regionNum, I_SUCURSAL_NUM AS sucursalNum, VC_SUCURSAL_NOM AS sucursalNom, "
                        + "I_PUESTO_ID AS puestoId, VC_PUESTO_DESC AS puestoDesc, VC_APLICA_META AS aplicaMeta, VC_ESTADO AS estado, "
                        + "I_PERIODO_NUM AS periodoNum, DT_VIGENCIA_INICIO AS vigenciaInicio, DT_VIGENCIA_FIN AS vigenciaFin, "
                        + "I_VERSION AS version, VC_USUARIO_ACCION AS usuario, DT_FECHA_ACCION AS fecha "
                        + "FROM SIB.TBL_MC_EU_HISTORICO WHERE FK_ID_REGISTRO = ? ORDER BY PK_ID_HIST DESC",
                id);
    }


    public byte[] exportarEmpleadoSucursalXlsx(String search, Integer regionNum, Integer sucursalNum, Integer puestoId, String vigencia)
            throws IOException {
        List<Map<String, Object>> rows = consultarEmpleadoSucursalExport(search, regionNum, sucursalNum, puestoId, vigencia);
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("SucursalEmpleado");
            Row header = sheet.createRow(0);
            String[] cols = { "Region", "Sucursal", "Empleado Num", "Empleado", "Puesto Id", "Puesto", "Aplica Meta",
                    "Estado", "Periodo", "Vigencia Inicio", "Vigencia Fin", "Estado Vigencia", "Version" };
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }
            int rowIdx = 1;
            for (Map<String, Object> r : rows) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(str(r.get("regionNum")));
                row.createCell(1).setCellValue(str(r.get("sucursalNum")) + " - " + str(r.get("sucursalNom")));
                row.createCell(2).setCellValue(str(r.get("empleadoNum")));
                row.createCell(3).setCellValue(str(r.get("empleadoNom")));
                row.createCell(4).setCellValue(str(r.get("puestoId")));
                row.createCell(5).setCellValue(str(r.get("puestoDesc")));
                row.createCell(6).setCellValue(str(r.get("aplicaMeta")));
                row.createCell(7).setCellValue(str(r.get("estado")));
                row.createCell(8).setCellValue(str(r.get("periodoNum")));
                row.createCell(9).setCellValue(str(r.get("vigenciaInicio")));
                row.createCell(10).setCellValue(str(r.get("vigenciaFin")));
                row.createCell(11).setCellValue(str(r.get("vigenciaEstado")));
                row.createCell(12).setCellValue(str(r.get("version")));
            }
            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(baos);
            return baos.toByteArray();
        }
    }

    public byte[] exportarEmpleadoSucursalPdf(String search, Integer regionNum, Integer sucursalNum, Integer puestoId, String vigencia)
            throws Exception {
        List<Map<String, Object>> rows = consultarEmpleadoSucursalExport(search, regionNum, sucursalNum, puestoId, vigencia);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            document.add(new Paragraph("Metas y Comisiones - Sucursal / Empleado", titleFont));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100f);
            addPdfHeader(table, "Region");
            addPdfHeader(table, "Sucursal");
            addPdfHeader(table, "Empleado");
            addPdfHeader(table, "Puesto");
            addPdfHeader(table, "Aplica Meta");
            addPdfHeader(table, "Vigencia");
            addPdfHeader(table, "Estado");
            for (Map<String, Object> r : rows) {
                table.addCell(new Phrase(str(r.get("regionNum")), cellFont));
                table.addCell(new Phrase(str(r.get("sucursalNum")) + " - " + str(r.get("sucursalNom")), cellFont));
                table.addCell(new Phrase(str(r.get("empleadoNum")) + " - " + str(r.get("empleadoNom")), cellFont));
                table.addCell(new Phrase(str(r.get("puestoDesc")), cellFont));
                table.addCell(new Phrase(str(r.get("aplicaMeta")), cellFont));
                table.addCell(new Phrase(str(r.get("vigenciaInicio")) + " a " + str(r.get("vigenciaFin")), cellFont));
                table.addCell(new Phrase(str(r.get("vigenciaEstado")), cellFont));
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        }
    }

    private List<Map<String, Object>> consultarEmpleadoSucursalExport(String search, Integer regionNum, Integer sucursalNum,
            Integer puestoId, String vigencia) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) obtenerDetalleEmpleadoSucursal(1, 5000, search, regionNum, sucursalNum,
                puestoId, vigencia).get("items");
        return items == null ? List.of() : items;
    }

    public Map<String, Object> compararEmpleadoCore(String search, Integer regionNum, Integer sucursalNum, Integer puestoId,
            String vigencia, boolean soloDiferencias) {
        inicializarTablasCatalogo();
        Map<Integer, Map<String, Object>> actual = cargarMapaEmpleadoActual(search, regionNum, sucursalNum, puestoId, vigencia);
        Map<Integer, Map<String, Object>> core = cargarMapaEmpleadoCore(search, regionNum, sucursalNum, puestoId);
        List<Integer> empleados = new ArrayList<>();
        empleados.addAll(actual.keySet());
        core.keySet().forEach(id -> {
            if (!empleados.contains(id)) {
                empleados.add(id);
            }
        });

        List<Map<String, Object>> items = new ArrayList<>();
        int iguales = 0;
        int soloExcel = 0;
        int soloCore = 0;
        int diferentes = 0;

        for (Integer empleadoNum : empleados) {
            Map<String, Object> actualRow = actual.get(empleadoNum);
            Map<String, Object> coreRow = core.get(empleadoNum);
            String estado = calcularEstadoComparativoEmpleado(actualRow, coreRow);
            if ("IGUAL".equals(estado)) {
                iguales++;
                if (soloDiferencias) {
                    continue;
                }
            } else if ("SOLO_EXCEL".equals(estado)) {
                soloExcel++;
            } else if ("SOLO_CORE".equals(estado)) {
                soloCore++;
            } else {
                diferentes++;
            }

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("empleadoNum", empleadoNum);
            item.put("estadoComparativo", estado);
            item.put("resumenDiferencias", construirResumenDiferenciasEmpleado(actualRow, coreRow));
            item.put("actual", normalizarVistaComparativoEmpleado(actualRow));
            item.put("core", normalizarVistaComparativoEmpleado(coreRow));
            items.add(item);
        }

        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("iguales", iguales);
        resumen.put("soloExcel", soloExcel);
        resumen.put("soloCore", soloCore);
        resumen.put("diferentes", diferentes);
        resumen.put("total", items.size());

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("resumen", resumen);
        out.put("items", items);
        out.put("fuenteActual", "TBL_MC_EMPLEADO_UBICACION");
        out.put("fuenteCore", "CE.TBL_MICROFIN_EMPLEADOS");
        return out;
    }

    public Map<String, Object> aplicarDecisionComparativoEmpleadoCore(Map<String, Object> body) {
        inicializarTablasCatalogo();
        String decision = trim(str(body.get("decision")));
        if (decision == null || decision.isBlank()) {
            decision = "EXCEL";
        }
        decision = decision.toUpperCase();
        @SuppressWarnings("unchecked")
        List<Object> rawIds = body.get("empleadoNums") instanceof List<?> ? (List<Object>) body.get("empleadoNums") : List.of();
        List<Integer> empleadoNums = rawIds.stream()
                .map(EstructuraMetasComisionesService::toInteger)
                .filter(v -> v != null)
                .distinct()
                .collect(Collectors.toList());

        if (empleadoNums.isEmpty()) {
            throw new IllegalArgumentException("Selecciona al menos un empleado para aplicar la decision");
        }
        if ("EXCEL".equals(decision)) {
            registrarAuditoria("COMPARATIVO_CORE", "EMPLEADO_UBICACION", null,
                    "decision=EXCEL, empleados=" + empleadoNums.size());
            Map<String, Object> out = new LinkedHashMap<>();
            out.put("decision", "EXCEL");
            out.put("procesados", empleadoNums.size());
            out.put("insertados", 0);
            out.put("actualizados", 0);
            out.put("desactivados", 0);
            out.put("message", "Se conservaron los registros actuales del catalogo");
            return out;
        }

        Map<Integer, Map<String, Object>> actual = cargarMapaEmpleadoActual(null, null, null, null, "ACTUAL");
        Map<Integer, Map<String, Object>> core = cargarMapaEmpleadoCore(null, null, null, null);
        java.time.LocalDate hoy = java.time.LocalDate.now();
        Integer periodoActual = (hoy.getYear() * 100) + hoy.getMonthValue();
        int insertados = 0;
        int actualizados = 0;
        int desactivados = 0;

        for (Integer empleadoNum : empleadoNums) {
            Map<String, Object> actualRow = actual.get(empleadoNum);
            Map<String, Object> coreRow = core.get(empleadoNum);
            if (actualRow == null && coreRow == null) {
                continue;
            }
            if (coreRow == null && actualRow != null) {
                Long id = ((Number) actualRow.get("id")).longValue();
                registrarHistoricoEmpleadoUbicacion(id, "ANTES_SYNC_CORE_DESACTIVA");
                jdbcTemplate.update(
                        "UPDATE SIB.TBL_MC_EMPLEADO_UBICACION "
                                + "SET VC_ESTADO = ?, DT_VIGENCIA_FIN = COALESCE(DT_VIGENCIA_FIN, ?), "
                                + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() "
                                + "WHERE PK_ID = ?",
                        "INACTIVO_CORE", java.sql.Date.valueOf(hoy), obtenerUsuarioActual(), id);
                registrarHistoricoEmpleadoUbicacion(id, "SYNC_CORE_DESACTIVA");
                desactivados++;
                continue;
            }
            if (coreRow != null && actualRow == null) {
                Integer pk = jdbcTemplate.queryForObject(
                        "SELECT COALESCE(MAX(PK_ID), 0) + 1 FROM SIB.TBL_MC_EMPLEADO_UBICACION", Integer.class);
                jdbcTemplate.update(
                        "INSERT INTO SIB.TBL_MC_EMPLEADO_UBICACION "
                                + "(PK_ID, I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, "
                                + "I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, "
                                + "I_VERSION, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                                + "VALUES (?, ?, ?, NULL, ?, ?, ?, ?, ?, 'SI', ?, ?, ?, NULL, 1, ?, ?, NOW())",
                        pk,
                        empleadoNum,
                        trim(str(coreRow.get("empleadoNom"))),
                        toInteger(coreRow.get("regionNum")),
                        toInteger(coreRow.get("sucursalNum")),
                        trim(str(coreRow.get("sucursalNom"))),
                        toInteger(coreRow.get("puestoId")),
                        trim(str(coreRow.get("puestoDesc"))),
                        trim(str(coreRow.get("estado"))),
                        periodoActual,
                        java.sql.Date.valueOf(hoy),
                        obtenerUsuarioActual(),
                        obtenerUsuarioActual());
                registrarHistoricoEmpleadoUbicacion(Long.valueOf(pk), "SYNC_CORE_CREATE");
                insertados++;
                continue;
            }
            if (!coincideComparativoEmpleado(actualRow, coreRow)) {
                Long id = ((Number) actualRow.get("id")).longValue();
                registrarHistoricoEmpleadoUbicacion(id, "ANTES_SYNC_CORE_UPDATE");
                jdbcTemplate.update(
                        "UPDATE SIB.TBL_MC_EMPLEADO_UBICACION "
                                + "SET VC_EMPLEADO_NOM = ?, I_REGION_NUM = ?, I_SUCURSAL_NUM = ?, VC_SUCURSAL_NOM = ?, "
                                + "I_PUESTO_ID = ?, VC_PUESTO_DESC = ?, VC_ESTADO = ?, DT_VIGENCIA_FIN = NULL, "
                                + "I_VERSION = COALESCE(I_VERSION, 1) + 1, VC_USUARIO_MODIFICACION = ?, DT_FECHA_MODIFICACION = NOW() "
                                + "WHERE PK_ID = ?",
                        trim(str(coreRow.get("empleadoNom"))),
                        toInteger(coreRow.get("regionNum")),
                        toInteger(coreRow.get("sucursalNum")),
                        trim(str(coreRow.get("sucursalNom"))),
                        toInteger(coreRow.get("puestoId")),
                        trim(str(coreRow.get("puestoDesc"))),
                        trim(str(coreRow.get("estado"))),
                        obtenerUsuarioActual(),
                        id);
                registrarHistoricoEmpleadoUbicacion(id, "SYNC_CORE_UPDATE");
                actualizados++;
            }
        }

        registrarAuditoria("COMPARATIVO_CORE", "EMPLEADO_UBICACION", null,
                "decision=CORE, procesados=" + empleadoNums.size() + ", insertados=" + insertados
                        + ", actualizados=" + actualizados + ", desactivados=" + desactivados);

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("decision", "CORE");
        out.put("procesados", empleadoNums.size());
        out.put("insertados", insertados);
        out.put("actualizados", actualizados);
        out.put("desactivados", desactivados);
        return out;
    }

    private Map<Integer, Map<String, Object>> cargarMapaEmpleadoActual(String search, Integer regionNum, Integer sucursalNum,
            Integer puestoId, String vigencia) {
        StringBuilder sql = new StringBuilder(
                "SELECT e.PK_ID AS id, e.I_EMPLEADO_NUM AS empleadoNum, e.VC_EMPLEADO_NOM AS empleadoNom, "
                        + "e.I_REGION_NUM AS regionNum, e.I_SUCURSAL_NUM AS sucursalNum, e.VC_SUCURSAL_NOM AS sucursalNom, "
                        + "e.I_PUESTO_ID AS puestoId, e.VC_PUESTO_DESC AS puestoDesc, e.VC_ESTADO AS estado "
                        + "FROM SIB.TBL_MC_EMPLEADO_UBICACION e "
                        + "INNER JOIN (SELECT I_EMPLEADO_NUM, MAX(PK_ID) AS maxId FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                        + "WHERE (DT_VIGENCIA_INICIO IS NULL OR DT_VIGENCIA_INICIO <= CURDATE()) "
                        + "AND (DT_VIGENCIA_FIN IS NULL OR DT_VIGENCIA_FIN >= CURDATE()) "
                        + "GROUP BY I_EMPLEADO_NUM) x ON x.maxId = e.PK_ID "
                        + "WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        agregarFiltrosEmpleadoComparativo(sql, params, "e", search, regionNum, sucursalNum, puestoId, vigencia, true);
        sql.append(" ORDER BY e.I_EMPLEADO_NUM ");
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString(), params.toArray());
        Map<Integer, Map<String, Object>> out = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Integer empleadoNum = toInteger(row.get("empleadoNum"));
            if (empleadoNum != null) {
                out.put(empleadoNum, row);
            }
        }
        return out;
    }

    private Map<Integer, Map<String, Object>> cargarMapaEmpleadoCore(String search, Integer regionNum, Integer sucursalNum,
            Integer puestoId) {
        StringBuilder sql = new StringBuilder(
                "SELECT e.EmpleadoID AS empleadoNum, "
                        + "CONCAT_WS(' ', e.PrimerNombre, e.SegundoNombre, e.ApellidoPat, e.ApellidoMat) AS empleadoNom, "
                        + "sr.I_REGION_NUM AS regionNum, e.SucursalID AS sucursalNum, "
                        + "COALESCE(sr.VC_SUCURSAL_NOM, CONCAT('SUCURSAL ', e.SucursalID)) AS sucursalNom, "
                        + "p.ClavePuestoID AS puestoId, p.Descripcion AS puestoDesc, e.Estatus AS estado "
                        + "FROM CE.TBL_MICROFIN_EMPLEADOS e "
                        + "LEFT JOIN CE.TBL_MICROFIN_PUESTOS p ON p.ClavePuestoID = e.ClavePuestoID "
                        + "LEFT JOIN SIB.TBL_MC_SUCURSAL_REGION sr ON sr.I_SUCURSAL_NUM = e.SucursalID "
                        + "WHERE e.Estatus = 'A' ");
        List<Object> params = new ArrayList<>();
        agregarFiltrosEmpleadoComparativo(sql, params, null, search, regionNum, sucursalNum, puestoId, null, false);
        sql.append(" ORDER BY e.EmpleadoID ");
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString(), params.toArray());
        Map<Integer, Map<String, Object>> out = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Integer empleadoNum = toInteger(row.get("empleadoNum"));
            if (empleadoNum != null) {
                out.put(empleadoNum, row);
            }
        }
        return out;
    }

    private void agregarFiltrosEmpleadoComparativo(StringBuilder sql, List<Object> params, String alias, String search,
            Integer regionNum, Integer sucursalNum, Integer puestoId, String vigencia, boolean permitirVigencia) {
        String empleadoExpr = alias == null ? "e.EmpleadoID" : alias + ".I_EMPLEADO_NUM";
        String nombreExpr = alias == null
                ? "CONCAT_WS(' ', e.PrimerNombre, e.SegundoNombre, e.ApellidoPat, e.ApellidoMat)"
                : alias + ".VC_EMPLEADO_NOM";
        String sucursalExpr = alias == null ? "e.SucursalID" : alias + ".I_SUCURSAL_NUM";
        String puestoExpr = alias == null ? "p.ClavePuestoID" : alias + ".I_PUESTO_ID";
        String regionExpr = alias == null ? "sr.I_REGION_NUM" : alias + ".I_REGION_NUM";
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            sql.append(" AND (CAST(").append(empleadoExpr).append(" AS CHAR) LIKE ? ")
                    .append("OR UPPER(COALESCE(").append(nombreExpr).append(", '')) LIKE UPPER(?) ")
                    .append("OR CAST(").append(sucursalExpr).append(" AS CHAR) LIKE ? ")
                    .append("OR CAST(").append(puestoExpr).append(" AS CHAR) LIKE ?) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }
        if (regionNum != null) {
            sql.append(" AND ").append(regionExpr).append(" = ? ");
            params.add(regionNum);
        }
        if (sucursalNum != null) {
            sql.append(" AND ").append(sucursalExpr).append(" = ? ");
            params.add(sucursalNum);
        }
        if (puestoId != null) {
            sql.append(" AND ").append(puestoExpr).append(" = ? ");
            params.add(puestoId);
        }
        if (permitirVigencia) {
            String vigenciaNorm = trim(vigencia);
            if (vigenciaNorm != null && !vigenciaNorm.isBlank()) {
                String v = vigenciaNorm.toUpperCase();
                if ("ACTUAL".equals(v)) {
                    sql.append(" AND (").append(alias).append(".DT_VIGENCIA_INICIO IS NULL OR ").append(alias)
                            .append(".DT_VIGENCIA_INICIO <= CURDATE()) ")
                            .append("AND (").append(alias).append(".DT_VIGENCIA_FIN IS NULL OR ").append(alias)
                            .append(".DT_VIGENCIA_FIN >= CURDATE()) ");
                } else if ("HISTORICA".equals(v)) {
                    sql.append(" AND NOT ((").append(alias).append(".DT_VIGENCIA_INICIO IS NULL OR ").append(alias)
                            .append(".DT_VIGENCIA_INICIO <= CURDATE()) ")
                            .append("AND (").append(alias).append(".DT_VIGENCIA_FIN IS NULL OR ").append(alias)
                            .append(".DT_VIGENCIA_FIN >= CURDATE())) ");
                }
            }
        }
    }

    private String calcularEstadoComparativoEmpleado(Map<String, Object> actual, Map<String, Object> core) {
        if (actual == null && core == null) {
            return "SIN_DATOS";
        }
        if (actual == null) {
            return "SOLO_CORE";
        }
        if (core == null) {
            return "SOLO_EXCEL";
        }
        return coincideComparativoEmpleado(actual, core) ? "IGUAL" : "DIFERENTE";
    }

    private boolean coincideComparativoEmpleado(Map<String, Object> actual, Map<String, Object> core) {
        return java.util.Objects.equals(toInteger(actual.get("regionNum")), toInteger(core.get("regionNum")))
                && java.util.Objects.equals(toInteger(actual.get("sucursalNum")), toInteger(core.get("sucursalNum")))
                && coincidePuestoComparativo(actual, core)
                && java.util.Objects.equals(normalizarTexto(str(actual.get("empleadoNom"))), normalizarTexto(str(core.get("empleadoNom"))))
                && java.util.Objects.equals(normalizarTexto(str(actual.get("puestoDesc"))), normalizarTexto(str(core.get("puestoDesc"))));
    }

    private String construirResumenDiferenciasEmpleado(Map<String, Object> actual, Map<String, Object> core) {
        if (actual == null) {
            return "Solo existe en core";
        }
        if (core == null) {
            return "Solo existe en catalogo actual";
        }
        List<String> partes = new ArrayList<>();
        if (!java.util.Objects.equals(toInteger(actual.get("regionNum")), toInteger(core.get("regionNum")))) {
            partes.add("Region");
        }
        if (!java.util.Objects.equals(toInteger(actual.get("sucursalNum")), toInteger(core.get("sucursalNum")))) {
            partes.add("Sucursal");
        }
        if (!coincidePuestoComparativo(actual, core)) {
            partes.add("Puesto");
        }
        if (!java.util.Objects.equals(normalizarTexto(str(actual.get("empleadoNom"))), normalizarTexto(str(core.get("empleadoNom"))))) {
            partes.add("Nombre");
        }
        if (partes.isEmpty()) {
            return "Sin diferencias";
        }
        return String.join(", ", partes);
    }

    private Map<String, Object> normalizarVistaComparativoEmpleado(Map<String, Object> row) {
        if (row == null) {
            return null;
        }
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("id", toInteger(row.get("id")));
        out.put("empleadoNum", toInteger(row.get("empleadoNum")));
        out.put("empleadoNom", trim(str(row.get("empleadoNom"))));
        out.put("regionNum", toInteger(row.get("regionNum")));
        out.put("sucursalNum", toInteger(row.get("sucursalNum")));
        out.put("sucursalNom", trim(str(row.get("sucursalNom"))));
        out.put("puestoId", toInteger(row.get("puestoId")));
        out.put("puestoDesc", trim(str(row.get("puestoDesc"))));
        out.put("estado", trim(str(row.get("estado"))));
        return out;
    }

    private boolean coincidePuestoComparativo(Map<String, Object> actual, Map<String, Object> core) {
        Integer actualId = toInteger(actual.get("puestoId"));
        Integer coreId = toInteger(core.get("puestoId"));
        String actualDesc = normalizarTexto(str(actual.get("puestoDesc")));
        String coreDesc = normalizarTexto(str(core.get("puestoDesc")));
        if (actualId != null && coreId != null) {
            return java.util.Objects.equals(actualId, coreId);
        }
        return java.util.Objects.equals(actualDesc, coreDesc);
    }

    public List<Map<String, Object>> obtenerDetallePuestoProductoPorSucursalPuesto(Integer sucursalId, Integer puestoId) {
        inicializarTablaRelacionPuestoProducto();
        return jdbcTemplate.queryForList(
                "SELECT rel.PK_ID AS id, rel.I_SUCURSAL_NUM AS sucursalNum, rel.I_CLAVE_PUESTO_ID AS puestoId, "
                        + "COALESCE(p.I_NO_PRODUCTO, rel.I_NO_PRODUCTO_EXCEL, rel.FK_ID_PRODUCTO) AS productoNum, "
                        + "COALESCE(rel.VC_PRODUCTO_DET_NOM, p.VC_NOMBRE_PRODUCTO) AS productoNom, "
                        + "rel.D_PORC_NUEVO AS porcNuevo, rel.D_PORC_RENOVADO AS porcRenovado, "
                        + "rel.VC_CANAL AS canal, rel.VC_FUERZA_VENTA AS fuerzaVenta, "
                        + "rel.DT_VIGENCIA_INICIO AS vigenciaInicio, rel.DT_VIGENCIA_FIN AS vigenciaFin, "
                        + "rel.I_ESTATUS AS estatus, rel.I_VERSION AS version "
                        + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO rel "
                        + "LEFT JOIN SIB.TBL_CAT_PRODUCTO p ON p.PK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                        + "WHERE rel.I_SUCURSAL_NUM = ? AND rel.I_CLAVE_PUESTO_ID = ? "
                        + "ORDER BY productoNum",
                sucursalId, puestoId);
    }

    public Map<String, Object> altaDetallePuestoProducto(Map<String, Object> body) {
        inicializarTablaRelacionPuestoProducto();

        Integer sucursalNum = toInteger(body.get("sucursalNum"));
        Integer puestoId = toInteger(body.get("puestoId"));
        Integer productoNum = toInteger(body.get("productoNum"));
        String productoNom = trim(body.get("productoNom") == null ? null : String.valueOf(body.get("productoNom")));
        Double porcNuevo = toDouble(body.get("porcNuevo"));
        Double porcRenovado = toDouble(body.get("porcRenovado"));
        String canal = trim(body.get("canal") == null ? "TRADICIONAL" : String.valueOf(body.get("canal")));
        String fuerzaVenta = trim(body.get("fuerzaVenta") == null ? "SUCURSAL" : String.valueOf(body.get("fuerzaVenta")));
        String vigenciaInicio = trim(body.get("vigenciaInicio") == null ? null : String.valueOf(body.get("vigenciaInicio")));
        String vigenciaFin = trim(body.get("vigenciaFin") == null ? null : String.valueOf(body.get("vigenciaFin")));
        Integer estatus = body.containsKey("estatus") ? toInteger(body.get("estatus")) : 1;

        if (sucursalNum == null || puestoId == null) {
            throw new IllegalArgumentException("sucursalNum y puestoId son requeridos");
        }
        if (productoNum == null && (productoNom == null || productoNom.isBlank())) {
            throw new IllegalArgumentException("productoNum o productoNom es requerido");
        }
        if (!porcentajeValido(porcNuevo) || !porcentajeValido(porcRenovado)) {
            throw new IllegalArgumentException("Los porcentajes deben estar en el rango [0,100]");
        }
        validarRangoVigencia(vigenciaInicio, vigenciaFin);

        Map<Integer, Integer> productosPorNumero = cargarProductosPorNumero();
        Map<String, Integer> productosPorNombre = cargarProductosPorNombreNormalizado();
        List<Map.Entry<String, Integer>> productosLista = new ArrayList<>(productosPorNombre.entrySet());

        Integer productoId = resolverProductoId(productoNum, productoNom, productosPorNumero, productosPorNombre, productosLista);
        if (productoId == null) {
            productoId = generarProductoIdFallback(productoNum, productoNom);
        }

        Long existenteId = null;
        try {
            existenteId = jdbcTemplate.queryForObject(
                    "SELECT PK_ID FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE I_SUCURSAL_NUM = ? AND I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ? LIMIT 1",
                    Long.class, sucursalNum, puestoId, productoId);
        } catch (Exception ignore) {
        }
        validarTotalesProductoSucursal(sucursalNum, productoId, porcNuevo, porcRenovado, existenteId);

        jdbcTemplate.update(
                "INSERT INTO SIB.TBL_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, I_ESTATUS, DT_FECHA_BAJA) "
                        + "VALUES (?, ?, ?, 1, NULL) "
                        + "ON DUPLICATE KEY UPDATE I_NO_PRODUCTO_EXCEL = VALUES(I_NO_PRODUCTO_EXCEL), I_ESTATUS = 1, DT_FECHA_BAJA = NULL",
                puestoId, productoId, productoNum);

        jdbcTemplate.update(
                "INSERT INTO SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO "
                        + "(I_SUCURSAL_NUM, I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, VC_PRODUCTO_DET_NOM, D_PORC_NUEVO, D_PORC_RENOVADO, "
                        + "VC_CANAL, VC_FUERZA_VENTA, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_ESTATUS, VC_USUARIO_ALTA, VC_USUARIO_MODIFICACION, DT_FECHA_MODIFICACION) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) "
                        + "ON DUPLICATE KEY UPDATE I_NO_PRODUCTO_EXCEL = VALUES(I_NO_PRODUCTO_EXCEL), VC_PRODUCTO_DET_NOM = VALUES(VC_PRODUCTO_DET_NOM), "
                        + "D_PORC_NUEVO = VALUES(D_PORC_NUEVO), D_PORC_RENOVADO = VALUES(D_PORC_RENOVADO), "
                        + "VC_CANAL = VALUES(VC_CANAL), VC_FUERZA_VENTA = VALUES(VC_FUERZA_VENTA), "
                        + "DT_VIGENCIA_INICIO = VALUES(DT_VIGENCIA_INICIO), DT_VIGENCIA_FIN = VALUES(DT_VIGENCIA_FIN), "
                        + "I_ESTATUS = VALUES(I_ESTATUS), VC_USUARIO_MODIFICACION = VALUES(VC_USUARIO_MODIFICACION), "
                        + "DT_FECHA_MODIFICACION = NOW(), I_VERSION = COALESCE(I_VERSION,1) + 1",
                sucursalNum, puestoId, productoId, productoNum, productoNom, porcNuevo, porcRenovado,
                canal, fuerzaVenta, toSqlDateOrNull(vigenciaInicio), toSqlDateOrNull(vigenciaFin), estatus,
                obtenerUsuarioActual(), obtenerUsuarioActual());

        Long id = null;
        try {
            id = jdbcTemplate.queryForObject(
                    "SELECT PK_ID FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE I_SUCURSAL_NUM = ? AND I_CLAVE_PUESTO_ID = ? AND FK_ID_PRODUCTO = ? LIMIT 1",
                    Long.class, sucursalNum, puestoId, productoId);
        } catch (Exception ignore) {
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("inserted", 1);
        out.put("id", id);
        out.put("sucursalNum", sucursalNum);
        out.put("puestoId", puestoId);
        out.put("productoId", productoId);
        registrarHistoricoDetallePuestoProducto(id, "CREATE_OR_UPSERT");
        registrarAuditoria("CREATE", "SUCURSAL_PUESTO_PRODUCTO", id,
                "sucursal=" + sucursalNum + ", puesto=" + puestoId + ", producto=" + productoId
                        + ", nuevo=" + porcNuevo + ", renovado=" + porcRenovado);
        return out;
    }

    public List<Map<String, Object>> obtenerHistorialDetallePuestoProducto(Long id) {
        inicializarTablaRelacionPuestoProducto();
        return jdbcTemplate.queryForList(
                "SELECT PK_ID_HIST AS idHist, FK_ID_REGISTRO AS registroId, VC_EVENTO AS evento, I_SUCURSAL_NUM AS sucursalNum, "
                        + "I_CLAVE_PUESTO_ID AS puestoId, FK_ID_PRODUCTO AS productoId, I_NO_PRODUCTO_EXCEL AS productoNum, "
                        + "VC_PRODUCTO_DET_NOM AS productoNom, D_PORC_NUEVO AS porcNuevo, D_PORC_RENOVADO AS porcRenovado, "
                        + "VC_CANAL AS canal, VC_FUERZA_VENTA AS fuerzaVenta, DT_VIGENCIA_INICIO AS vigenciaInicio, "
                        + "DT_VIGENCIA_FIN AS vigenciaFin, I_ESTATUS AS estatus, I_VERSION AS version, "
                        + "VC_USUARIO_ACCION AS usuario, DT_FECHA_ACCION AS fecha "
                        + "FROM SIB.TBL_MC_SPP_HISTORICO WHERE FK_ID_REGISTRO = ? ORDER BY PK_ID_HIST DESC",
                id);
    }

    public Map<String, Object> obtenerAuditoria(int page, int size, String search) {
        inicializarTablaAuditoria();
        int safePage = page < 1 ? 1 : page;
        int safeSize = size < 1 ? 50 : Math.min(size, 500);
        int offset = (safePage - 1) * safeSize;

        String baseFrom = " FROM SIB.TBL_MC_REL_AUDITORIA a ";
        List<Object> params = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        if (search != null && !search.isBlank()) {
            String q = "%" + search.trim() + "%";
            where.append(" AND (UPPER(COALESCE(a.VC_EVENTO, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(a.VC_ENTIDAD, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(a.VC_USUARIO, '')) LIKE UPPER(?) "
                    + "OR UPPER(COALESCE(a.VC_DETALLE, '')) LIKE UPPER(?)) ");
            params.add(q);
            params.add(q);
            params.add(q);
            params.add(q);
        }

        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) " + baseFrom + where, params.toArray(), Integer.class);
        int totalRows = total == null ? 0 : total.intValue();
        int totalPages = totalRows == 0 ? 1 : (int) Math.ceil((double) totalRows / safeSize);

        String sql = "SELECT a.PK_ID AS id, a.VC_EVENTO AS evento, a.VC_ENTIDAD AS entidad, a.I_ENTIDAD_ID AS entidadId, "
                + "a.VC_USUARIO AS usuario, a.VC_DETALLE AS detalle, a.DT_FECHA_ALTA AS fecha "
                + baseFrom + where
                + " ORDER BY a.DT_FECHA_ALTA DESC, a.PK_ID DESC LIMIT ? OFFSET ?";
        List<Object> dataParams = new ArrayList<>(params);
        dataParams.add(safeSize);
        dataParams.add(offset);

        List<Map<String, Object>> items = jdbcTemplate.queryForList(sql, dataParams.toArray());
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("page", safePage);
        out.put("size", safeSize);
        out.put("total", totalRows);
        out.put("totalPages", totalPages);
        out.put("items", items);
        return out;
    }

    private boolean porcentajeValido(Double value) {
        double v = value == null ? 0.0 : value.doubleValue();
        return v >= 0.0 && v <= 100.0;
    }

    private void validarTotalesProductoSucursal(Integer sucursalNum, Integer productoId, Double porcNuevo, Double porcRenovado,
            Long... excludeIds) {
        if (sucursalNum == null || productoId == null) {
            return;
        }

        StringBuilder sql = new StringBuilder(
                "SELECT COALESCE(SUM(D_PORC_NUEVO),0) AS totalNuevo, COALESCE(SUM(D_PORC_RENOVADO),0) AS totalRenovado "
                        + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE I_SUCURSAL_NUM = ? AND FK_ID_PRODUCTO = ?");
        List<Object> params = new ArrayList<>();
        params.add(sucursalNum);
        params.add(productoId);

        if (excludeIds != null && excludeIds.length > 0) {
            List<Long> validIds = new ArrayList<>();
            for (Long id : excludeIds) {
                if (id != null) {
                    validIds.add(id);
                }
            }
            if (!validIds.isEmpty()) {
                sql.append(" AND PK_ID NOT IN (");
                for (int i = 0; i < validIds.size(); i++) {
                    if (i > 0) {
                        sql.append(",");
                    }
                    sql.append("?");
                    params.add(validIds.get(i));
                }
                sql.append(")");
            }
        }

        Map<String, Object> row = jdbcTemplate.queryForMap(sql.toString(), params.toArray());
        double totalNuevo = toDouble(row.get("totalNuevo")) + (porcNuevo == null ? 0.0 : porcNuevo.doubleValue());
        double totalRenovado = toDouble(row.get("totalRenovado")) + (porcRenovado == null ? 0.0 : porcRenovado.doubleValue());

        if (totalNuevo > 100.0 || totalRenovado > 100.0) {
            throw new IllegalArgumentException(
                    "La suma de porcentajes por sucursal-producto no puede exceder 100. Nuevo=" + totalNuevo
                            + ", Renovado=" + totalRenovado);
        }
    }

    private void agregarErrorCarga(List<Map<String, Object>> errores, int fila, String mensaje) {
        if (errores == null) {
            return;
        }
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("fila", fila);
        item.put("mensaje", mensaje);
        errores.add(item);
    }

    private List<Map<String, Object>> limitarErrores(List<Map<String, Object>> errores, int max) {
        if (errores == null || errores.isEmpty()) {
            return List.of();
        }
        int limit = Math.max(1, max);
        if (errores.size() <= limit) {
            return errores;
        }
        return new ArrayList<>(errores.subList(0, limit));
    }

    private String obtenerUsuarioActual() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getName() != null && !auth.getName().isBlank()) {
                return auth.getName();
            }
        } catch (Exception ignore) {
        }
        return "SISTEMA";
    }

    private void registrarAuditoria(String evento, String entidad, Long entidadId, String detalle) {
        try {
            inicializarTablaAuditoria();
            String detalleFinal = detalle;
            if (detalleFinal != null && detalleFinal.length() > 1900) {
                detalleFinal = detalleFinal.substring(0, 1900);
            }
            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_REL_AUDITORIA (VC_EVENTO, VC_ENTIDAD, I_ENTIDAD_ID, VC_USUARIO, VC_DETALLE) "
                            + "VALUES (?, ?, ?, ?, ?)",
                    evento, entidad, entidadId, obtenerUsuarioActual(), detalleFinal);
        } catch (Exception e) {
            logger.warn("No se pudo registrar auditoria: " + e.getMessage());
        }
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }

    private static String str(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String normalizarCanales(String value) {
        String v = trim(value);
        if (v == null || v.isBlank()) {
            return null;
        }
        String normalized = Normalizer.normalize(v, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .replace('_', ' ')
                .replace('-', ' ')
                .trim();
        if (normalized.contains("AMBOS") || (normalized.contains("TRADICIONAL") && normalized.contains("NOMINA"))) {
            return "AMBOS";
        }
        if (Set.of("1", "TRADICIONAL", "CANAL 1").contains(normalized)) {
            return "TRADICIONAL";
        }
        if (Set.of("2", "NOMINA", "NOMINA ", "CANAL 2").contains(normalized) || normalized.contains("NOMINA")) {
            return "NOMINA";
        }
        if (normalized.contains("TRADICIONAL")) {
            return "TRADICIONAL";
        }
        return null;
    }

    private static String normalizarEstatusSql(String value) {
        String v = trim(value);
        if (v == null || v.isBlank() || "ALL".equalsIgnoreCase(v)) {
            return null;
        }
        String up = v.toUpperCase();
        if (up.contains("INACT")) {
            return "INACT";
        }
        if (up.contains("ACT")) {
            return "ACT";
        }
        return up;
    }

    private static Integer toInteger(String value) {
        try {
            String v = trim(value);
            if (v == null || v.isEmpty() || "NULL".equalsIgnoreCase(v)) {
                return null;
            }
            if (v.contains(".")) {
                v = v.substring(0, v.indexOf('.'));
            }
            return Integer.parseInt(v);
        } catch (Exception e) {
            return null;
        }
    }

    private static Double toDouble(String value) {
        try {
            String v = trim(value);
            if (v == null || v.isEmpty() || "NULL".equalsIgnoreCase(v)) {
                return 0.0;
            }
            return Double.parseDouble(v.replace(",", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private static Double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        return toDouble(String.valueOf(value));
    }

    private static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        return toInteger(String.valueOf(value));
    }

    private static String normalizarTexto(String value) {
        String v = trim(value);
        if (v == null || v.isBlank()) {
            return null;
        }
        String normalized = Normalizer.normalize(v, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
        return normalized.replaceAll("[^A-Z0-9]", "");
    }

    private java.sql.Date toSqlDateOrNull(String value) {
        String v = trim(value);
        if (v == null || v.isBlank()) {
            return null;
        }
        try {
            return java.sql.Date.valueOf(v);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha invalido, se espera yyyy-MM-dd");
        }
    }

    private void validarRangoVigencia(String vigenciaInicio, String vigenciaFin) {
        java.sql.Date inicio = toSqlDateOrNull(vigenciaInicio);
        java.sql.Date fin = toSqlDateOrNull(vigenciaFin);
        if (inicio != null && fin != null && fin.before(inicio)) {
            throw new IllegalArgumentException("La vigencia fin no puede ser menor a la vigencia inicio");
        }
    }

    private void registrarHistoricoDetallePuestoProducto(Long id, String evento) {
        if (id == null) {
            return;
        }
        try {
            Map<String, Object> row = jdbcTemplate.queryForMap(
                    "SELECT PK_ID, I_SUCURSAL_NUM, I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, VC_PRODUCTO_DET_NOM, "
                            + "D_PORC_NUEVO, D_PORC_RENOVADO, VC_CANAL, VC_FUERZA_VENTA, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_ESTATUS, I_VERSION "
                            + "FROM SIB.TBL_MC_SUCURSAL_PUESTO_PRODUCTO WHERE PK_ID = ?",
                    id);

            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_SPP_HISTORICO "
                            + "(FK_ID_REGISTRO, VC_EVENTO, I_SUCURSAL_NUM, I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_NO_PRODUCTO_EXCEL, VC_PRODUCTO_DET_NOM, "
                            + "D_PORC_NUEVO, D_PORC_RENOVADO, VC_CANAL, VC_FUERZA_VENTA, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_ESTATUS, I_VERSION, VC_USUARIO_ACCION) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    id,
                    evento,
                    toInteger(row.get("I_SUCURSAL_NUM")),
                    toInteger(row.get("I_CLAVE_PUESTO_ID")),
                    toInteger(row.get("FK_ID_PRODUCTO")),
                    toInteger(row.get("I_NO_PRODUCTO_EXCEL")),
                    trim(row.get("VC_PRODUCTO_DET_NOM") == null ? null : String.valueOf(row.get("VC_PRODUCTO_DET_NOM"))),
                    toDouble(row.get("D_PORC_NUEVO")),
                    toDouble(row.get("D_PORC_RENOVADO")),
                    trim(row.get("VC_CANAL") == null ? null : String.valueOf(row.get("VC_CANAL"))),
                    trim(row.get("VC_FUERZA_VENTA") == null ? null : String.valueOf(row.get("VC_FUERZA_VENTA"))),
                    toSqlDateOrNull(row.get("DT_VIGENCIA_INICIO") == null ? null : String.valueOf(row.get("DT_VIGENCIA_INICIO"))),
                    toSqlDateOrNull(row.get("DT_VIGENCIA_FIN") == null ? null : String.valueOf(row.get("DT_VIGENCIA_FIN"))),
                    toInteger(row.get("I_ESTATUS")),
                    toInteger(row.get("I_VERSION")),
                    obtenerUsuarioActual());
        } catch (Exception e) {
            logger.warn("No se pudo registrar historico de puesto-producto " + id + ": " + e.getMessage());
        }
    }

    private void validarDuplicidadEmpleadoSucursalPuesto(Long id, Integer empleadoNum, Integer sucursalNum, Integer puestoId) {
        if (empleadoNum == null || sucursalNum == null || puestoId == null) {
            return;
        }
        String sql = "SELECT COUNT(*) FROM SIB.TBL_MC_EMPLEADO_UBICACION "
                + "WHERE I_EMPLEADO_NUM = ? AND I_SUCURSAL_NUM = ? AND I_PUESTO_ID = ? ";
        List<Object> params = new ArrayList<>(List.of(empleadoNum, sucursalNum, puestoId));
        if (id != null) {
            sql += "AND PK_ID <> ? ";
            params.add(id);
        }
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        if (count != null && count > 0) {
            throw new IllegalArgumentException("Ya existe una asignacion para el empleado en el mismo puesto y sucursal");
        }
    }

    private void registrarHistoricoEmpleadoUbicacion(Long id, String evento) {
        if (id == null) {
            return;
        }
        try {
            Map<String, Object> row = jdbcTemplate.queryForMap(
                    "SELECT PK_ID, I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, "
                            + "I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_VERSION "
                            + "FROM SIB.TBL_MC_EMPLEADO_UBICACION WHERE PK_ID = ?",
                    id);
            jdbcTemplate.update(
                    "INSERT INTO SIB.TBL_MC_EU_HISTORICO "
                            + "(FK_ID_REGISTRO, VC_EVENTO, I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, "
                            + "I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM, DT_VIGENCIA_INICIO, DT_VIGENCIA_FIN, I_VERSION, VC_USUARIO_ACCION) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    id, evento,
                    toInteger(row.get("I_EMPLEADO_NUM")),
                    trim(str(row.get("VC_EMPLEADO_NOM"))),
                    toInteger(row.get("I_REGION_NUM")),
                    toInteger(row.get("I_SUCURSAL_NUM")),
                    trim(str(row.get("VC_SUCURSAL_NOM"))),
                    toInteger(row.get("I_PUESTO_ID")),
                    trim(str(row.get("VC_PUESTO_DESC"))),
                    trim(str(row.get("VC_APLICA_META"))),
                    trim(str(row.get("VC_ESTADO"))),
                    toInteger(row.get("I_PERIODO_NUM")),
                    toSqlDateOrNull(row.get("DT_VIGENCIA_INICIO") == null ? null : String.valueOf(row.get("DT_VIGENCIA_INICIO"))),
                    toSqlDateOrNull(row.get("DT_VIGENCIA_FIN") == null ? null : String.valueOf(row.get("DT_VIGENCIA_FIN"))),
                    toInteger(row.get("I_VERSION")),
                    obtenerUsuarioActual());
        } catch (Exception e) {
            logger.warn("No se pudo registrar historico de empleado-ubicacion " + id + ": " + e.getMessage());
        }
    }
}


