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
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExcelReportServiceImpl implements ExcelReportService {

    private final ReporteClientesOpeRepository repo;
    private static final int BATCH = 10_000;
    private static final int XLSX_MAX = 1_048_576;

    @Autowired
    public ExcelReportServiceImpl(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }

    // =============== ARCHIVO ÚNICO con 2 hojas ===============
    @Override
    public Path generarExcelClientesYOperaciones(Timestamp iniTs, Timestamp finTs) throws IOException {
        final LocalDate ini = iniTs.toLocalDateTime().toLocalDate();
        final LocalDate fin = finTs.toLocalDateTime().toLocalDate();

        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        wb.setCompressTempFiles(true);

        // --------- OPERACIONES ---------
        Sheet shOps = wb.createSheet("OPERACIONES_1");
        int rowOps = writeOpsHeader(shOps);
        int sheetOps = 1;

        LocalDate lastFOp = ini.minusDays(1); // seek inicial
        long lastIdOp = 0L;
        Pageable page = PageRequest.of(0, BATCH);

        while (true) {
            List<OperacionRowDTO> lote = repo.fetchOps(lastFOp, lastIdOp, ini, fin, page);
            if (lote.isEmpty()) break;

            for (OperacionRowDTO x : lote) {
                if (rowOps >= XLSX_MAX) {
                    shOps = wb.createSheet("OPERACIONES_" + (++sheetOps));
                    rowOps = writeOpsHeader(shOps);
                }
                Row r = shOps.createRow(rowOps++); int c=0;
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

                if (x.fecha != null) lastFOp = x.fecha;
                if (x.numTransaccion != null) lastIdOp = x.numTransaccion;
            }
        }

        // --------- CLIENTES ---------
        Sheet shCli = wb.createSheet("CLIENTES_1");
        int rowCli = writeCliHeader(shCli);
        int sheetCli = 1;

        LocalDate lastFCli = ini.minusDays(1);
        int lastIdCli = 0;

        while (true) {
            List<ClienteRowDTO> lote = repo.fetchClientes(lastFCli, lastIdCli, ini, fin, page);
            if (lote.isEmpty()) break;

            for (ClienteRowDTO x : lote) {
                if (rowCli >= XLSX_MAX) {
                    shCli = wb.createSheet("CLIENTES_" + (++sheetCli));
                    rowCli = writeCliHeader(shCli);
                }
                Row r = shCli.createRow(rowCli++); int c=0;
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

                if (x.fechaAlta != null) lastFCli = x.fechaAlta;
                if (x.clienteId != null) lastIdCli = x.clienteId;
            }
        }

        Path out = Files.createTempFile("Reporte_Clientes_Operaciones_", ".xlsx");
        System.out.println("Archivo generado en: " + out.toAbsolutePath());
        try (OutputStream os = Files.newOutputStream(out)) { wb.write(os); }
        finally { wb.dispose(); wb.close(); }
        return out;
    }

    // =============== ARCHIVO SOLO OPERACIONES (segundo endpoint) ===============
    @Override
    public Path generarExcelOperaciones(Timestamp iniTs, Timestamp finTs) throws IOException {
        final LocalDate ini = iniTs.toLocalDateTime().toLocalDate();
        final LocalDate fin = finTs.toLocalDateTime().toLocalDate();

        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        wb.setCompressTempFiles(true);

        Sheet shOps = wb.createSheet("OPERACIONES_1");
        int row = writeOpsHeader(shOps);
        int sheet = 1;

        LocalDate lastF = ini.minusDays(1); // seek inicial
        long lastId = 0L;
        Pageable page = PageRequest.of(0, BATCH);

        while (true) {
            List<OperacionRowDTO> lote = repo.fetchOps(lastF, lastId, ini, fin, page);
            if (lote.isEmpty()) break;

            for (OperacionRowDTO x : lote) {
                if (row >= XLSX_MAX) {
                    shOps = wb.createSheet("OPERACIONES_" + (++sheet));
                    row = writeOpsHeader(shOps);
                }
                Row r = shOps.createRow(row++); int c=0;
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

                if (x.fecha != null) lastF = x.fecha;
                if (x.numTransaccion != null) lastId = x.numTransaccion;
            }
        }

        Path out = Files.createTempFile("Reporte_Operaciones_", ".xlsx");
        try (OutputStream os = Files.newOutputStream(out)) { wb.write(os); }
        finally { wb.dispose(); wb.close(); }
        return out;
    }

    // =================== Encabezados EXACTOS ===================
    private int writeOpsHeader(Sheet sh){
        String[] cols = {
            "ClienteID","Nombre cliente","TipoPersona","Fecha","monto","Destino_Beneficiario",
            "INSTRUMENTO","Tipo_Producto","Descripcion_Mov","ReferenciaMov","Naturaleza_movimiento",
            "Numtransaccion","No_Contrato"
        };
        Row h = sh.createRow(0);
        for (int i=0;i<cols.length;i++) h.createCell(i).setCellValue(cols[i]);
        return 1;
    }

    private int writeCliHeader(Sheet sh){
        String[] cols = {
            "TIPO","Estatus","Fecha alta","ClienteID","Fecha nacimiento","Nombre","Genero",
            "Entidad federativa de nacimiento","Pais nacimiento","Nacionalidad","Número de telefono",
            "Correo","Representante legal","Tipo persona","RFC","CURP","PEPs","Ocupación",
            "Actividad INEGI ID","Actividad INEGI","ActividadBMXID","ActividadBMX","ActividadSCIANID",
            "nivel_riesgo","Calle","# casa","# interior","Colonia","Municipio","Estado",
            "CodigoPostal","Tipo de ocupación","Lugar de trabajo","Actividad FRID","Actividad FR",
            "Sector economico ID","Sector economico","volumen_creditos"
        };
        Row h = sh.createRow(0);
        for (int i=0;i<cols.length;i++) h.createCell(i).setCellValue(cols[i]);
        return 1;
    }

    // =================== Helpers null-safe ===================
    private String s(String v){ return v==null? "" : v; }
    private String ld(LocalDate d){ return d==null? "" : d.toString(); }
    private double d(BigDecimal b){ return b==null? 0d : b.doubleValue(); }
    private long n(Long v){ return v==null? 0L : v; }
    private int  n(Integer v){ return v==null? 0  : v; }
} 