package ahorrosbienestar.sib.std.metascomisiones.relaciones.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.ColaboradorRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.PuestoRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.RegionRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.SucursalRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.service.EstructuraMetasComisionesService;
import ahorrosbienestar.sib.std.metascomisiones.security.MetasComisionesAccesoService;
import ahorrosbienestar.sib.std.metascomisiones.security.MetasComisionesFuncionalidadService;

@RestController
@RequestMapping("/metas-comisiones/relaciones")
public class EstructuraMetasComisionesController {

    @Autowired
    private EstructuraMetasComisionesService estructuraService;

    @Autowired
    private MetasComisionesAccesoService accesoService;

    @Autowired
    private MetasComisionesFuncionalidadService funcionalidadService;

    private boolean sinAcceso() {
        return !accesoService.tieneAcceso(Constantes.METAS_COMISIONES_RELACIONES);
    }

    private ResponseEntity<Map<String, Object>> accesoDenegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("ok", false, "message", "No cuenta con permisos para acceder a esta pantalla"));
    }

    private ResponseEntity<Map<String, Object>> funcionalidadDenegada(String funcionalidad) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("ok", false, "message", "No cuenta con permisos para ejecutar la funcionalidad", "funcionalidad", funcionalidad));
    }

    private boolean sinTab(String claveTab) {
        return !funcionalidadService.tieneFuncionalidad(claveTab);
    }

    private boolean sinFuncionalidad(String clave) {
        return !funcionalidadService.tieneFuncionalidad(clave);
    }

    private boolean sinAlgunaFuncionalidad(String... claves) {
        return !funcionalidadService.tieneAlgunaFuncionalidad(claves);
    }

    @GetMapping("/funcionalidades")
    public ResponseEntity<?> funcionalidadesPantalla() {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        return ResponseEntity.ok(Map.of(
                "pantalla", "RELACIONES",
                "funcionalidades", funcionalidadService.obtenerClavesUsuarioActual("RELACIONES")));
    }

    @GetMapping("/catalogo/sucursales")
    public ResponseEntity<?> catalogoSucursales() {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinAlgunaFuncionalidad(
                "RELACIONES_TAB_REGION_SUCURSAL",
                "RELACIONES_TAB_SUCURSAL_EMPLEADO",
                "RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO")) {
            return funcionalidadDenegada("RELACIONES_CATALOGO_SUCURSALES");
        }
        Map<String, Object> data = estructuraService.obtenerDetalleRegionSucursal(1, 5000, null, null, null);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.getOrDefault("items", List.of());
        Map<Integer, Map<String, Object>> unicas = new java.util.LinkedHashMap<>();
        for (Map<String, Object> item : items) {
            Object sucursalNum = item.get("sucursalNum");
            if (sucursalNum == null) {
                continue;
            }
            Integer key = Integer.valueOf(String.valueOf(sucursalNum));
            if (!unicas.containsKey(key)) {
                unicas.put(key, Map.of(
                        "sucursalId", key,
                        "nombreSucursal", String.valueOf(item.getOrDefault("sucursalNom", "")),
                        "regionNum", item.get("regionNum")));
            }
        }
        return ResponseEntity.ok(unicas.values());
    }

    @GetMapping("/regiones")
    public ResponseEntity<List<RegionRelacionDto>> obtenerRegiones() {
        if (sinAcceso()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(estructuraService.obtenerRegiones());
    }

    @GetMapping("/sucursales")
    public ResponseEntity<List<SucursalRelacionDto>> obtenerSucursales(@RequestParam("regionId") Integer regionId) {
        if (sinAcceso()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(estructuraService.obtenerSucursalesPorRegion(regionId));
    }

    @GetMapping("/colaboradores")
    public ResponseEntity<List<ColaboradorRelacionDto>> obtenerColaboradores(@RequestParam("sucursalId") Integer sucursalId) {
        if (sinAcceso()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(estructuraService.obtenerColaboradoresPorSucursal(sucursalId));
    }

    @GetMapping("/puestos")
    public ResponseEntity<List<PuestoRelacionDto>> obtenerPuestos(@RequestParam("colaboradorId") Integer colaboradorId) {
        if (sinAcceso()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(estructuraService.obtenerPuestosPorColaborador(colaboradorId));
    }

    @GetMapping("/puestos-por-sucursal")
    public ResponseEntity<List<PuestoRelacionDto>> obtenerPuestosPorSucursal(@RequestParam("sucursalId") Integer sucursalId) {
        if (sinAcceso()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(estructuraService.obtenerPuestosPorSucursal(sucursalId));
    }

    @PostMapping(value = "/cargar-catalogos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarCatalogos(@RequestParam("sucursalesFile") MultipartFile sucursalesFile,
            @RequestParam("empleadosFile") MultipartFile empleadosFile,
            @RequestParam("puestosProductosFile") MultipartFile puestosProductosFile) throws Exception {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        return ResponseEntity.ok(estructuraService.cargarCatalogos(sucursalesFile, empleadosFile, puestosProductosFile));
    }

    @PostMapping(value = "/cargar-sucursales", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarSoloSucursales(@RequestParam("sucursalesFile") MultipartFile sucursalesFile) throws Exception {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_REGION_SUCURSAL_CARGAR_EXCEL")) {
            return funcionalidadDenegada("RELACIONES_REGION_SUCURSAL_CARGAR_EXCEL");
        }
        return ResponseEntity.ok(estructuraService.cargarSoloSucursales(sucursalesFile));
    }

    @PostMapping(value = "/cargar-empleados", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarSoloEmpleados(@RequestParam("empleadosFile") MultipartFile empleadosFile) throws Exception {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_SUCURSAL_EMPLEADO_CARGAR_EXCEL")) {
            return funcionalidadDenegada("RELACIONES_SUCURSAL_EMPLEADO_CARGAR_EXCEL");
        }
        return ResponseEntity.ok(estructuraService.cargarSoloEmpleados(empleadosFile));
    }

    @PostMapping(value = "/cargar-puestos-productos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarSoloPuestosProductos(@RequestParam("puestosProductosFile") MultipartFile puestosProductosFile) throws Exception {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_PUESTO_PRODUCTO_CARGAR_EXCEL")) {
            return funcionalidadDenegada("RELACIONES_PUESTO_PRODUCTO_CARGAR_EXCEL");
        }
        return ResponseEntity.ok(estructuraService.cargarSoloPuestosProductos(puestosProductosFile));
    }

    @GetMapping("/preview/sucursales")
    public ResponseEntity<?> previewSucursales(@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        return ResponseEntity.ok(estructuraService.obtenerVistaPreviaSucursales(limit));
    }

    @GetMapping("/preview/colaboradores")
    public ResponseEntity<?> previewColaboradores(@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        return ResponseEntity.ok(estructuraService.obtenerVistaPreviaColaboradores(limit));
    }

    @GetMapping("/preview/puesto-producto")
    public ResponseEntity<?> previewPuestoProducto(@RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        return ResponseEntity.ok(estructuraService.obtenerVistaPreviaPuestoProducto(limit));
    }

    @GetMapping("/detalle/puesto-producto")
    public ResponseEntity<?> detallePuestoProducto(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "100") Integer size,
            @RequestParam(value = "search", required = false) String search) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO")) {
            return funcionalidadDenegada("RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetallePuestoProducto(page, size, search));
    }

    @PutMapping("/detalle/puesto-producto/{id}")
    public ResponseEntity<?> actualizarDetallePuestoProducto(@PathVariable("id") Long id, @RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_PUESTO_PRODUCTO_EDITAR")) {
            return funcionalidadDenegada("RELACIONES_PUESTO_PRODUCTO_EDITAR");
        }
        return ResponseEntity.ok(estructuraService.actualizarDetallePuestoProducto(id, body));
    }

    @GetMapping("/detalle/region-sucursal")
    public ResponseEntity<?> detalleRegionSucursal(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "canal", required = false) String canal,
            @RequestParam(value = "estatus", required = false) String estatus) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return funcionalidadDenegada("RELACIONES_TAB_REGION_SUCURSAL");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetalleRegionSucursal(page, size, search, canal, estatus));
    }

    @GetMapping("/detalle/region-sucursal-por-sucursal")
    public ResponseEntity<?> detalleRegionSucursalPorSucursal(@RequestParam("sucursalId") Integer sucursalId) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return funcionalidadDenegada("RELACIONES_TAB_REGION_SUCURSAL");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetalleRegionSucursalPorSucursal(sucursalId));
    }

    @PutMapping("/detalle/region-sucursal/{id}")
    public ResponseEntity<?> actualizarDetalleRegionSucursal(@PathVariable("id") Long id, @RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_REGION_SUCURSAL_EDITAR")) {
            return funcionalidadDenegada("RELACIONES_REGION_SUCURSAL_EDITAR");
        }
        return ResponseEntity.ok(estructuraService.actualizarDetalleRegionSucursal(id, body));
    }

    @PostMapping("/detalle/region-sucursal/alta-region")
    public ResponseEntity<?> altaRegion(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_REGION_SUCURSAL_AGREGAR")) {
            return funcionalidadDenegada("RELACIONES_REGION_SUCURSAL_AGREGAR");
        }
        return ResponseEntity.ok(estructuraService.altaRegion(body));
    }

    @PostMapping("/detalle/region-sucursal/alta-sucursal")
    public ResponseEntity<?> altaSucursal(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_REGION_SUCURSAL_AGREGAR")) {
            return funcionalidadDenegada("RELACIONES_REGION_SUCURSAL_AGREGAR");
        }
        return ResponseEntity.ok(estructuraService.altaSucursal(body));
    }

    @PostMapping("/detalle/region-sucursal/relacionar")
    public ResponseEntity<?> relacionarSucursalRegion(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_REGION_SUCURSAL_MOVER")) {
            return funcionalidadDenegada("RELACIONES_REGION_SUCURSAL_MOVER");
        }
        return ResponseEntity.ok(estructuraService.relacionarSucursalRegion(body));
    }

    @GetMapping("/detalle/region-sucursal/exportar-xlsx")
    public ResponseEntity<ByteArrayResource> exportarRegionSucursalXlsx(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "canal", required = false) String canal,
            @RequestParam(value = "estatus", required = false) String estatus) throws Exception {
        if (sinAcceso() || sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        byte[] bytes = estructuraService.exportarRegionSucursalXlsx(search, canal, estatus);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=region-sucursal.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(bytes.length)
                .body(new ByteArrayResource(bytes));
    }

    @GetMapping("/detalle/region-sucursal/exportar-pdf")
    public ResponseEntity<ByteArrayResource> exportarRegionSucursalPdf(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "canal", required = false) String canal,
            @RequestParam(value = "estatus", required = false) String estatus) throws Exception {
        if (sinAcceso() || sinTab("RELACIONES_TAB_REGION_SUCURSAL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        byte[] bytes = estructuraService.exportarRegionSucursalPdf(search, canal, estatus);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=region-sucursal.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(bytes.length)
                .body(new ByteArrayResource(bytes));
    }

    @GetMapping("/detalle/empleado-sucursal")
    public ResponseEntity<?> detalleEmpleadoSucursal(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "regionNum", required = false) Integer regionNum,
            @RequestParam(value = "sucursalNum", required = false) Integer sucursalNum,
            @RequestParam(value = "puestoId", required = false) Integer puestoId,
            @RequestParam(value = "vigencia", required = false) String vigencia) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return funcionalidadDenegada("RELACIONES_TAB_SUCURSAL_EMPLEADO");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetalleEmpleadoSucursal(page, size, search, regionNum, sucursalNum, puestoId, vigencia));
    }

    @GetMapping("/detalle/empleado-sucursal-por-sucursal")
    public ResponseEntity<?> detalleEmpleadoSucursalPorSucursal(@RequestParam("sucursalId") Integer sucursalId) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return funcionalidadDenegada("RELACIONES_TAB_SUCURSAL_EMPLEADO");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetalleEmpleadoSucursalPorSucursal(sucursalId));
    }

    @PutMapping("/detalle/empleado-sucursal/{id}")
    public ResponseEntity<?> actualizarDetalleEmpleadoSucursal(@PathVariable("id") Long id, @RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_SUCURSAL_EMPLEADO_MOVER")) {
            return funcionalidadDenegada("RELACIONES_SUCURSAL_EMPLEADO_MOVER");
        }
        return ResponseEntity.ok(estructuraService.actualizarDetalleEmpleadoSucursal(id, body));
    }

    @PostMapping("/detalle/empleado-sucursal")
    public ResponseEntity<?> altaDetalleEmpleadoSucursal(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_SUCURSAL_EMPLEADO_AGREGAR")) {
            return funcionalidadDenegada("RELACIONES_SUCURSAL_EMPLEADO_AGREGAR");
        }
        return ResponseEntity.ok(estructuraService.altaDetalleEmpleadoSucursal(body));
    }

    @PutMapping("/detalle/empleado-sucursal/{id}/edicion")
    public ResponseEntity<?> actualizarDetalleEmpleadoSucursalEdicion(@PathVariable("id") Long id,
            @RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_SUCURSAL_EMPLEADO_EDITAR")) {
            return funcionalidadDenegada("RELACIONES_SUCURSAL_EMPLEADO_EDITAR");
        }
        return ResponseEntity.ok(estructuraService.actualizarDetalleEmpleadoSucursalEdicion(id, body));
    }

    @GetMapping("/detalle/empleado-sucursal/{id}/historial")
    public ResponseEntity<?> historialDetalleEmpleadoSucursal(@PathVariable("id") Long id) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_HISTORIAL_CONSULTAR")) {
            return funcionalidadDenegada("RELACIONES_HISTORIAL_CONSULTAR");
        }
        return ResponseEntity.ok(estructuraService.obtenerHistorialEmpleadoSucursal(id));
    }

    @GetMapping("/detalle/empleado-sucursal/exportar-xlsx")
    public ResponseEntity<ByteArrayResource> exportarEmpleadoSucursalXlsx(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "regionNum", required = false) Integer regionNum,
            @RequestParam(value = "sucursalNum", required = false) Integer sucursalNum,
            @RequestParam(value = "puestoId", required = false) Integer puestoId,
            @RequestParam(value = "vigencia", required = false) String vigencia) throws Exception {
        if (sinAcceso() || sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        byte[] bytes = estructuraService.exportarEmpleadoSucursalXlsx(search, regionNum, sucursalNum, puestoId, vigencia);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sucursal-empleado.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(bytes.length)
                .body(new ByteArrayResource(bytes));
    }

    @GetMapping("/detalle/empleado-sucursal/exportar-pdf")
    public ResponseEntity<ByteArrayResource> exportarEmpleadoSucursalPdf(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "regionNum", required = false) Integer regionNum,
            @RequestParam(value = "sucursalNum", required = false) Integer sucursalNum,
            @RequestParam(value = "puestoId", required = false) Integer puestoId,
            @RequestParam(value = "vigencia", required = false) String vigencia) throws Exception {
        if (sinAcceso() || sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        byte[] bytes = estructuraService.exportarEmpleadoSucursalPdf(search, regionNum, sucursalNum, puestoId, vigencia);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sucursal-empleado.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(bytes.length)
                .body(new ByteArrayResource(bytes));
    }

    @GetMapping("/comparativo/empleado-core")
    public ResponseEntity<?> comparativoEmpleadoCore(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "regionNum", required = false) Integer regionNum,
            @RequestParam(value = "sucursalNum", required = false) Integer sucursalNum,
            @RequestParam(value = "puestoId", required = false) Integer puestoId,
            @RequestParam(value = "vigencia", required = false) String vigencia,
            @RequestParam(value = "soloDiferencias", defaultValue = "true") Boolean soloDiferencias) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_EMPLEADO")) {
            return funcionalidadDenegada("RELACIONES_TAB_SUCURSAL_EMPLEADO");
        }
        return ResponseEntity.ok(estructuraService.compararEmpleadoCore(search, regionNum, sucursalNum, puestoId, vigencia,
                Boolean.TRUE.equals(soloDiferencias)));
    }

    @PostMapping("/comparativo/empleado-core/aplicar")
    public ResponseEntity<?> aplicarComparativoEmpleadoCore(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_SUCURSAL_EMPLEADO_EDITAR")) {
            return funcionalidadDenegada("RELACIONES_SUCURSAL_EMPLEADO_EDITAR");
        }
        return ResponseEntity.ok(estructuraService.aplicarDecisionComparativoEmpleadoCore(body));
    }

    @GetMapping("/detalle/puesto-producto-por-sucursal-puesto")
    public ResponseEntity<?> detallePuestoProductoPorSucursalPuesto(@RequestParam("sucursalId") Integer sucursalId,
            @RequestParam("puestoId") Integer puestoId) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO")) {
            return funcionalidadDenegada("RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO");
        }
        return ResponseEntity.ok(estructuraService.obtenerDetallePuestoProductoPorSucursalPuesto(sucursalId, puestoId));
    }

    @PostMapping("/detalle/puesto-producto")
    public ResponseEntity<?> altaDetallePuestoProducto(@RequestBody java.util.Map<String, Object> body) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_PUESTO_PRODUCTO_AGREGAR")) {
            return funcionalidadDenegada("RELACIONES_PUESTO_PRODUCTO_AGREGAR");
        }
        return ResponseEntity.ok(estructuraService.altaDetallePuestoProducto(body));
    }

    @GetMapping("/detalle/puesto-producto/{id}/historial")
    public ResponseEntity<?> historialDetallePuestoProducto(@PathVariable("id") Long id) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinFuncionalidad("RELACIONES_HISTORIAL_CONSULTAR")) {
            return funcionalidadDenegada("RELACIONES_HISTORIAL_CONSULTAR");
        }
        return ResponseEntity.ok(estructuraService.obtenerHistorialDetallePuestoProducto(id));
    }

    @GetMapping("/auditoria")
    public ResponseEntity<?> obtenerAuditoria(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "100") Integer size,
            @RequestParam(value = "search", required = false) String search) {
        if (sinAcceso()) {
            return accesoDenegado();
        }
        if (sinTab("RELACIONES_TAB_HISTORIAL")) {
            return funcionalidadDenegada("RELACIONES_TAB_HISTORIAL");
        }
        return ResponseEntity.ok(estructuraService.obtenerAuditoria(page, size, search));
    }
}
