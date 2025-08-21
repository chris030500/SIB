package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.ClienteRowDTO;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.OperacionRowDTO;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExcelReportServiceImpl implements ExcelReportService {

    private final ReporteClientesOpeRepository repo;
    private static final int BATCH_SIZE = 5000;

    @Autowired
    public ExcelReportServiceImpl(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }

    @Override
    public void generarExcelClientesYOperaciones(Timestamp inicio, Timestamp fin, OutputStream out) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(1000)) {
            workbook.setCompressTempFiles(true);

            // Hoja Clientes
            Sheet clientesSheet = workbook.createSheet("CLIENTES");
            escribirClientes(clientesSheet, inicio, fin);

            // Hoja Operaciones
            Sheet operacionesSheet = workbook.createSheet("OPERACIONES");
            escribirOperaciones(operacionesSheet, inicio, fin);

            // Escribir al OutputStream (HTTP response)
            workbook.write(out);
            out.flush();
        }
    }

    // ================== CLIENTES ==================
    private void escribirClientes(Sheet sheet, Timestamp inicio, Timestamp fin) {
        int rowNum = 0;

        // Encabezados de todas las columnas (según tu lista)
        String[] cols = {
            "TIPO","Estatus","Fecha alta","ClienteID","Fecha nacimiento","Nombre","Genero",
            "Entidad nacimiento","Pais nacimiento","Nacionalidad","Teléfono","Correo",
            "Representante legal","Tipo persona","RFC","CURP","PEPs","Ocupación",
            "Actividad INEGI ID","Actividad INEGI","Actividad BMX ID","Actividad BMX",
            "Actividad SCIAN ID","Nivel riesgo","Calle","# casa","# interior","Colonia",
            "Municipio","Estado","CP","Tipo ocupación","Lugar trabajo","Actividad FR ID",
            "Actividad FR","Sector Económico ID","Sector Económico","Volumen Créditos"
        };
        Row header = sheet.createRow(rowNum++);
        for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);

        // Datos
        LocalDate ini = inicio.toLocalDateTime().toLocalDate();
        LocalDate finD = fin.toLocalDateTime().toLocalDate();
        LocalDate lastDate = ini.minusDays(1);
        int lastId = 0;

        Pageable page = PageRequest.of(0, BATCH_SIZE);
        while (true) {
            List<ClienteRowDTO> clientes = repo.fetchClientes(lastDate, lastId, ini, finD, page);
            if (clientes.isEmpty()) break;

            for (ClienteRowDTO x : clientes) {
                Row r = sheet.createRow(rowNum++);
                int c = 0;

                r.createCell(c++).setCellValue(s(x.tipo));
                r.createCell(c++).setCellValue(s(x.estatus));
                r.createCell(c++).setCellValue(ld(x.fechaAlta));
                r.createCell(c++).setCellValue(n(x.clienteId));
                r.createCell(c++).setCellValue(ld(x.fechaNacimiento));
                r.createCell(c++).setCellValue(s(x.nombre));
                r.createCell(c++).setCellValue(s(x.genero));
                r.createCell(c++).setCellValue(s(x.entidadNac));
                r.createCell(c++).setCellValue(s(x.paisNac));
                r.createCell(c++).setCellValue(s(x.nacionalidad));
                r.createCell(c++).setCellValue(s(x.telefono));
                r.createCell(c++).setCellValue(s(x.correo));
                r.createCell(c++).setCellValue(s(x.representante));
                r.createCell(c++).setCellValue(s(x.tipoPersona));
                r.createCell(c++).setCellValue(s(x.rfc));
                r.createCell(c++).setCellValue(s(x.curp));
                r.createCell(c++).setCellValue(s(x.peps));
                r.createCell(c++).setCellValue(s(x.ocupacion));
                r.createCell(c++).setCellValue(n(x.actividadInegiId));
                r.createCell(c++).setCellValue(s(x.actividadInegi));
                r.createCell(c++).setCellValue(n(x.actividadBmxId));
                r.createCell(c++).setCellValue(s(x.actividadBmx));
                r.createCell(c++).setCellValue(n(x.actividadScianId));
                r.createCell(c++).setCellValue(s(x.nivelRiesgo));
                r.createCell(c++).setCellValue(s(x.calle));
                r.createCell(c++).setCellValue(s(x.numCasa));
                r.createCell(c++).setCellValue(s(x.numInterior));
                r.createCell(c++).setCellValue(s(x.colonia));
                r.createCell(c++).setCellValue(s(x.municipio));
                r.createCell(c++).setCellValue(s(x.estado));
                r.createCell(c++).setCellValue(s(x.cp));
                r.createCell(c++).setCellValue(s(x.tipoOcupacion));
                r.createCell(c++).setCellValue(s(x.lugarTrabajo));
                r.createCell(c++).setCellValue(n(x.actividadFrId));
                r.createCell(c++).setCellValue(s(x.actividadFr));
                r.createCell(c++).setCellValue(n(x.sectorEconId));
                r.createCell(c++).setCellValue(s(x.sectorEcon));
                r.createCell(c++).setCellValue(d(x.volumenCreditos));

                if (x.fechaAlta != null) lastDate = x.fechaAlta;
                if (x.clienteId != null) lastId = x.clienteId;
            }
        }
    }

    // ================== OPERACIONES ==================
    private void escribirOperaciones(Sheet sheet, Timestamp inicio, Timestamp fin) {
        int rowNum = 0;

        // Encabezados (según tu DTO de operaciones)
        String[] cols = {
            "ClienteID","Nombre cliente","Tipo Persona","Fecha","Monto",
            "Destino Beneficiario","Instrumento","Tipo Producto",
            "Descripción Mov","Referencia Mov","Naturaleza Mov",
            "Num Transacción","No Contrato"
        };
        Row header = sheet.createRow(rowNum++);
        for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);

        // Datos
        LocalDate ini = inicio.toLocalDateTime().toLocalDate();
        LocalDate finD = fin.toLocalDateTime().toLocalDate();
        LocalDate lastDate = ini.minusDays(1);
        long lastId = 0;

        Pageable page = PageRequest.of(0, BATCH_SIZE);
        while (true) {
            List<OperacionRowDTO> operaciones = repo.fetchOps(lastDate, lastId, ini, finD, page);
            if (operaciones.isEmpty()) break;

            for (OperacionRowDTO x : operaciones) {
                Row r = sheet.createRow(rowNum++);
                int c = 0;

                r.createCell(c++).setCellValue(n(x.clienteId));
                r.createCell(c++).setCellValue(s(x.nombreCliente));
                r.createCell(c++).setCellValue(s(x.tipoPersona));
                r.createCell(c++).setCellValue(ld(x.fecha));
                r.createCell(c++).setCellValue(d(x.monto));
                r.createCell(c++).setCellValue(s(x.destinoBeneficiario));
                r.createCell(c++).setCellValue(s(x.instrumento));
                r.createCell(c++).setCellValue(s(x.tipoProducto));
                r.createCell(c++).setCellValue(s(x.descripcionMov));
                r.createCell(c++).setCellValue(s(x.referenciaMov));
                r.createCell(c++).setCellValue(s(x.naturalezaMov));
                r.createCell(c++).setCellValue(n(x.numTransaccion));
                r.createCell(c++).setCellValue(n(x.noContrato));

                if (x.fecha != null) lastDate = x.fecha;
                if (x.numTransaccion != null) lastId = x.numTransaccion;
            }
        }
    }

    // ================== Helpers null-safe ==================
    private String s(String v) { return v == null ? "" : v; }
    private String ld(LocalDate d) { return d == null ? "" : d.toString(); }
    private double d(BigDecimal b) { return b == null ? 0d : b.doubleValue(); }
    private long n(Long v) { return v == null ? 0L : v; }
    private int n(Integer v) { return v == null ? 0 : v; }
}
