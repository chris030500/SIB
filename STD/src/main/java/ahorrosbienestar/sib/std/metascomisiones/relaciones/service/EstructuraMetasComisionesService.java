package ahorrosbienestar.sib.std.metascomisiones.relaciones.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                    + "I_ESTATUS TINYINT(1) NOT NULL DEFAULT 1,"
                    + "DT_FECHA_ALTA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "DT_FECHA_BAJA DATETIME NULL,"
                    + "PRIMARY KEY (PK_ID_RELACION),"
                    + "UNIQUE KEY UK_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO)"
                    + ")";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            logger.warn("No se pudo crear/verificar TBL_MC_PUESTO_PRODUCTO: " + e.getMessage());
        }
    }

    public void inicializarTablasCatalogo() {
        try {
            String suc = "CREATE TABLE IF NOT EXISTS SIB.TBL_MC_SUCURSAL_REGION ("
                    + "PK_ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "I_REGION_TIPO_NUM INT NULL,"
                    + "I_REGION_NUM INT NULL,"
                    + "I_SUCURSAL_NUM INT NOT NULL,"
                    + "VC_SUCURSAL_NOM VARCHAR(200) NULL,"
                    + "VC_SUCURSAL_ESTATUS VARCHAR(50) NULL,"
                    + "VC_REGION_NOM VARCHAR(200) NULL,"
                    + "VC_ESTADO VARCHAR(100) NULL,"
                    + "PRIMARY KEY (PK_ID),"
                    + "UNIQUE KEY UK_MC_SUCURSAL_NUM (I_SUCURSAL_NUM)"
                    + ")";
            jdbcTemplate.execute(suc);

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
                    + "PRIMARY KEY (PK_ID),"
                    + "UNIQUE KEY UK_MC_EMPLEADO_NUM (I_EMPLEADO_NUM)"
                    + ")";
            jdbcTemplate.execute(emp);
        } catch (Exception e) {
            logger.warn("No se pudieron crear tablas de catalogo de metas/comisiones: " + e.getMessage());
        }
    }

    public Map<String, Object> cargarCatalogos(MultipartFile sucursalesFile, MultipartFile empleadosFile,
            MultipartFile puestosProductosFile) throws IOException {

        inicializarTablasCatalogo();
        inicializarTablaRelacionPuestoProducto();

        int sucursalesProcesadas = cargarCatalogoSucursales(sucursalesFile);
        Map<String, Integer> puestosDetectados = cargarCatalogoEmpleados(empleadosFile);
        int relacionesGeneradas = cargarCatalogoPuestosProductos(puestosProductosFile, puestosDetectados);

        Map<String, Object> result = new HashMap<>();
        result.put("sucursalesProcesadas", sucursalesProcesadas);
        result.put("puestosDetectados", puestosDetectados);
        result.put("relacionesGeneradas", relacionesGeneradas);
        return result;
    }

    private int cargarCatalogoSucursales(MultipartFile file) throws IOException {
        DataFormatter fmt = new DataFormatter();
        int rows = 0;

        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_SUCURSAL_REGION");

        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                Integer regionTipoNum = toInteger(fmt.formatCellValue(row.getCell(1)));
                Integer regionNum = toInteger(fmt.formatCellValue(row.getCell(2)));
                Integer sucursalNum = toInteger(fmt.formatCellValue(row.getCell(4)));
                String sucursalNom = trim(fmt.formatCellValue(row.getCell(5)));
                String sucursalEstatus = trim(fmt.formatCellValue(row.getCell(6)));
                String estado = trim(fmt.formatCellValue(row.getCell(11)));
                String regionNom = trim(fmt.formatCellValue(row.getCell(12)));

                if (sucursalNum == null) {
                    continue;
                }

                jdbcTemplate.update(
                        "INSERT INTO SIB.TBL_MC_SUCURSAL_REGION (I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, VC_SUCURSAL_ESTATUS, VC_REGION_NOM, VC_ESTADO) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                                + "ON DUPLICATE KEY UPDATE I_REGION_TIPO_NUM=VALUES(I_REGION_TIPO_NUM), I_REGION_NUM=VALUES(I_REGION_NUM), "
                                + "VC_SUCURSAL_NOM=VALUES(VC_SUCURSAL_NOM), VC_SUCURSAL_ESTATUS=VALUES(VC_SUCURSAL_ESTATUS), "
                                + "VC_REGION_NOM=VALUES(VC_REGION_NOM), VC_ESTADO=VALUES(VC_ESTADO)",
                        regionTipoNum, regionNum, sucursalNum, sucursalNom, sucursalEstatus, regionNom, estado);
                rows++;
            }
        }

        return rows;
    }

    private Map<String, Integer> cargarCatalogoEmpleados(MultipartFile file) throws IOException {
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

                if (empleadoNum == null) {
                    continue;
                }

                jdbcTemplate.update(
                        "INSERT INTO SIB.TBL_MC_EMPLEADO_UBICACION (I_EMPLEADO_NUM, VC_EMPLEADO_NOM, I_REGION_TIPO_NUM, I_REGION_NUM, I_SUCURSAL_NUM, VC_SUCURSAL_NOM, I_PUESTO_ID, VC_PUESTO_DESC, VC_APLICA_META, VC_ESTADO, I_PERIODO_NUM) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                                + "ON DUPLICATE KEY UPDATE VC_EMPLEADO_NOM=VALUES(VC_EMPLEADO_NOM), I_REGION_TIPO_NUM=VALUES(I_REGION_TIPO_NUM), I_REGION_NUM=VALUES(I_REGION_NUM), "
                                + "I_SUCURSAL_NUM=VALUES(I_SUCURSAL_NUM), VC_SUCURSAL_NOM=VALUES(VC_SUCURSAL_NOM), I_PUESTO_ID=VALUES(I_PUESTO_ID), "
                                + "VC_PUESTO_DESC=VALUES(VC_PUESTO_DESC), VC_APLICA_META=VALUES(VC_APLICA_META), VC_ESTADO=VALUES(VC_ESTADO), I_PERIODO_NUM=VALUES(I_PERIODO_NUM)",
                        empleadoNum, empleadoNom, regionTipoNum, regionNum, sucursalNum, sucursalNom, puestoId, puestoDesc,
                        aplicaMeta, estado, periodoNum);

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
            }
        }

        return puestos;
    }

    private int cargarCatalogoPuestosProductos(MultipartFile file, Map<String, Integer> puestosDetectados) throws IOException {
        DataFormatter fmt = new DataFormatter();
        int relaciones = 0;

        jdbcTemplate.update("DELETE FROM SIB.TBL_MC_PUESTO_PRODUCTO");

        try (InputStream is = file.getInputStream(); Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                Integer productoNumero = toInteger(fmt.formatCellValue(row.getCell(3)));
                if (productoNumero == null) {
                    continue;
                }

                Integer productoId = buscarProductoIdPorNumero(productoNumero);
                if (productoId == null) {
                    continue;
                }

                relaciones += insertarRelacionPorPorcentaje(puestosDetectados.get("GERENTE"), productoId,
                        toDouble(fmt.formatCellValue(row.getCell(5))), toDouble(fmt.formatCellValue(row.getCell(9))));

                relaciones += insertarRelacionPorPorcentaje(puestosDetectados.get("EJECUTIVO"), productoId,
                        toDouble(fmt.formatCellValue(row.getCell(6))), toDouble(fmt.formatCellValue(row.getCell(10))));

                relaciones += insertarRelacionPorPorcentaje(puestosDetectados.get("CAJERO"), productoId,
                        toDouble(fmt.formatCellValue(row.getCell(7))), toDouble(fmt.formatCellValue(row.getCell(11))));

                relaciones += insertarRelacionPorPorcentaje(puestosDetectados.get("PROMOTOR_JR"), productoId,
                        toDouble(fmt.formatCellValue(row.getCell(8))), toDouble(fmt.formatCellValue(row.getCell(12))));
            }
        }

        return relaciones;
    }

    private int insertarRelacionPorPorcentaje(Integer puestoId, Integer productoId, Double porcentajeNue,
            Double porcentajeRen) {
        if (puestoId == null) {
            return 0;
        }

        double n = porcentajeNue == null ? 0.0 : porcentajeNue.doubleValue();
        double r = porcentajeRen == null ? 0.0 : porcentajeRen.doubleValue();
        if (n <= 0.0 && r <= 0.0) {
            return 0;
        }

        String sql = "INSERT INTO SIB.TBL_MC_PUESTO_PRODUCTO (I_CLAVE_PUESTO_ID, FK_ID_PRODUCTO, I_ESTATUS, DT_FECHA_BAJA) "
                + "VALUES (?, ?, 1, NULL) "
                + "ON DUPLICATE KEY UPDATE I_ESTATUS = 1, DT_FECHA_BAJA = NULL";
        jdbcTemplate.update(sql, puestoId, productoId);
        return 1;
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

    private boolean hayDatosTabla(String tableName) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM SIB." + tableName, Integer.class);
        return count != null && count > 0;
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
            String sql = "SELECT I_EMPLEADO_NUM AS colaboradorId, VC_EMPLEADO_NOM AS nombreColaborador "
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

    public List<ProductoRelacionDto> obtenerProductosPorPuesto(Integer clavePuestoId) {
        inicializarTablaRelacionPuestoProducto();

        String sql = "SELECT p.PK_ID_PRODUCTO AS productoId, p.I_NO_PRODUCTO AS numeroProducto, p.VC_NOMBRE_PRODUCTO AS nombreProducto "
                + "FROM SIB.TBL_MC_PUESTO_PRODUCTO rel "
                + "INNER JOIN SIB.TBL_CAT_PRODUCTO p ON p.PK_ID_PRODUCTO = rel.FK_ID_PRODUCTO "
                + "WHERE rel.I_CLAVE_PUESTO_ID = ? AND rel.I_ESTATUS = 1 "
                + "ORDER BY p.VC_NOMBRE_PRODUCTO";

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

    private static String trim(String value) {
        return value == null ? null : value.trim();
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
}
