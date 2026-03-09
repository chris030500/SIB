package ahorrosbienestar.sib.std.metascomisiones.relaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.ColaboradorRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.ProductoRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.PuestoProductoRequest;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.PuestoRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.RegionRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.dto.SucursalRelacionDto;
import ahorrosbienestar.sib.std.metascomisiones.relaciones.service.EstructuraMetasComisionesService;

@RestController
@RequestMapping("/metas-comisiones/relaciones")
public class EstructuraMetasComisionesController {

    @Autowired
    private EstructuraMetasComisionesService estructuraService;

    @GetMapping("/regiones")
    public ResponseEntity<List<RegionRelacionDto>> obtenerRegiones() {
        return ResponseEntity.ok(estructuraService.obtenerRegiones());
    }

    @GetMapping("/sucursales")
    public ResponseEntity<List<SucursalRelacionDto>> obtenerSucursales(@RequestParam("regionId") Integer regionId) {
        return ResponseEntity.ok(estructuraService.obtenerSucursalesPorRegion(regionId));
    }

    @GetMapping("/colaboradores")
    public ResponseEntity<List<ColaboradorRelacionDto>> obtenerColaboradores(@RequestParam("sucursalId") Integer sucursalId) {
        return ResponseEntity.ok(estructuraService.obtenerColaboradoresPorSucursal(sucursalId));
    }

    @GetMapping("/puestos")
    public ResponseEntity<List<PuestoRelacionDto>> obtenerPuestos(@RequestParam("colaboradorId") Integer colaboradorId) {
        return ResponseEntity.ok(estructuraService.obtenerPuestosPorColaborador(colaboradorId));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoRelacionDto>> obtenerProductosPorPuesto(@RequestParam("clavePuestoId") Integer clavePuestoId) {
        return ResponseEntity.ok(estructuraService.obtenerProductosPorPuesto(clavePuestoId));
    }

    @GetMapping("/productos-disponibles")
    public ResponseEntity<List<ProductoRelacionDto>> obtenerProductosDisponibles() {
        return ResponseEntity.ok(estructuraService.obtenerProductosDisponibles());
    }

    @PostMapping("/puesto-producto")
    public ResponseEntity<?> asignarProductoAPuesto(@RequestBody PuestoProductoRequest request) {
        int registros = estructuraService.asignarProductoAPuesto(request.getClavePuestoId(), request.getProductoId());
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/puesto-producto")
    public ResponseEntity<?> desasignarProductoDePuesto(@RequestParam("clavePuestoId") Integer clavePuestoId,
            @RequestParam("productoId") Integer productoId) {
        int registros = estructuraService.desasignarProductoDePuesto(clavePuestoId, productoId);
        return ResponseEntity.ok(registros);
    }

    @PostMapping(value = "/cargar-catalogos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cargarCatalogos(@RequestParam("sucursalesFile") MultipartFile sucursalesFile,
            @RequestParam("empleadosFile") MultipartFile empleadosFile,
            @RequestParam("puestosProductosFile") MultipartFile puestosProductosFile) throws Exception {
        return ResponseEntity.ok(estructuraService.cargarCatalogos(sucursalesFile, empleadosFile, puestosProductosFile));
    }
}
