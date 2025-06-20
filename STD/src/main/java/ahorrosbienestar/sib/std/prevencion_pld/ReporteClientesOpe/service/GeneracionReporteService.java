package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DescargaReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DescargaReporteRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesoReporteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class GeneracionReporteService {

    private final ProcesoReporteRepository procesoRepo;
    private final DescargaReporteRepository descargaRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public GeneracionReporteService(ProcesoReporteRepository procesoRepo,
                                    DescargaReporteRepository descargaRepo) {
        this.procesoRepo = procesoRepo;
        this.descargaRepo = descargaRepo;
    }

    public ResponseEntity<?> guardarFechasProceso(ProcesoReporte proceso) {
        if (procesoRepo.countPepCargados() == 0) {
            return ResponseEntity.badRequest().body("Debe cargar primero archivo PEP");
        }
        procesoRepo.save(proceso);
        return ResponseEntity.ok("Fechas guardadas correctamente");
    }
/*
    @Transactional
    public ResponseEntity<?> procesarAhora(String usuario) {
        // Llamar SP_TBL_PLD_PROCESO_REPORTE para registrar la carga y obtener idProceso
        StoredProcedureQuery sp1 = entityManager.createStoredProcedureQuery("SIB.SP_TBL_PLD_PROCESO_REPORTE");
        sp1.registerStoredProcedureParameter("p_usuarioCarga", String.class, ParameterMode.IN);
        sp1.registerStoredProcedureParameter("p_idProceso", Integer.class, ParameterMode.OUT);
        sp1.setParameter("p_usuarioCarga", usuario);
        sp1.execute();
        Integer idProceso = (Integer) sp1.getOutputParameterValue("p_idProceso");

        // Llamar SP_TBL_PLD_DESCARGAS para generar tabla OPERACIONES2 y poblar DESCARGAS
        StoredProcedureQuery sp2 = entityManager.createStoredProcedureQuery("SIB.SP_TBL_PLD_DESCARGAS");
        sp2.registerStoredProcedureParameter("p_idProceso", Integer.class, ParameterMode.IN);
        sp2.registerStoredProcedureParameter("p_usuarioEjecuto", String.class, ParameterMode.IN);
        sp2.registerStoredProcedureParameter("p_registros", Integer.class, ParameterMode.OUT);
        sp2.setParameter("p_idProceso", idProceso);
        sp2.setParameter("p_usuarioEjecuto", usuario);
        sp2.execute();
        Integer registros = (Integer) sp2.getOutputParameterValue("p_registros");

        if (registros == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se generaron registros para este proceso.");
        }

        // Crear archivo Excel desde los datos reales en TBL_PLD_DESCARGAS
        List<DescargaReporte> lista = descargaRepo.findByIdProceso(idProceso);
        String nombre = "reporte_peps_" + System.currentTimeMillis() + ".xlsx";
        String ruta = System.getProperty("java.io.tmpdir") + File.separator + nombre;

        try (Workbook wb = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(ruta)) {
            Sheet sheet = wb.createSheet("Reporte");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID Cliente");
            header.createCell(1).setCellValue("Nombre");

            int rowIdx = 1;
            for (DescargaReporte d : lista) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(d.getIdCliente());
                row.createCell(1).setCellValue(d.getNombreCliente());
            }

            wb.write(fos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generando Excel: " + e.getMessage());
        }

        return ResponseEntity.ok("Reporte generado con éxito: " + nombre);
    }*/

    public ResponseEntity<?> confirmarReporte(boolean confirmado) {
        if (!confirmado) {
            URI uri = URI.create("/pantalla/cargar-archivos");
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }
        return ResponseEntity.ok("Reporte confirmado");
    }

    public List<DescargaReporte> obtenerDescargas() {
        int anio = Year.now().getValue();
        return descargaRepo.findByAnio(anio);
    }
/*
    public ResponseEntity<Resource> descargarArchivo(Long id) {
        DescargaReporte d = descargaRepo.findById(id).orElseThrow();
        File f = new File(d.getRuta());
        if (!f.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            Resource r = new InputStreamResource(new FileInputStream(f));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + d.getNombreArchivo())
                    .body(r);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/
}

