package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
//import sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;

import java.io.InputStream;
import java.util.*;

public class ExcelPEPReader {

    private static final DataFormatter FMT = new DataFormatter();

    public static List<DetallePEPsModel> parse(MultipartFile file) throws Exception {
        List<DetallePEPsModel> lista = new ArrayList<>();

        try (InputStream in = file.getInputStream();
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) return lista;

            // 1. Mapeo de cabeceras
            Map<String, Integer> idx = new HashMap<>();
            Row header = sheet.getRow(0);
            if (header == null) return lista;

            for (Cell c : header) {
                String nombreCol = FMT.formatCellValue(c).trim().toLowerCase();
                idx.put(nombreCol, c.getColumnIndex());
            }

            // 2. Lectura de filas (desde la fila 1, salta encabezado)
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Integer clienteId = parseInt(row, idx, "cliente_id");
                String nombre = getText(row, idx, "nombre_completo");

                if (clienteId == null || nombre.isEmpty()) continue;

                DetallePEPsModel model = new DetallePEPsModel();
                model.setI_clienteid(clienteId);
                model.setVc_nombre_cliente(nombre);

                lista.add(model);
            }
        }

        return lista;
    }

    private static String getText(Row row, Map<String, Integer> idx, String campo) {
        Integer i = idx.get(campo.toLowerCase());
        if (i == null) return "";
        Cell cell = row.getCell(i);
        return cell == null ? "" : FMT.formatCellValue(cell).trim();
    }

    private static Integer parseInt(Row row, Map<String, Integer> idx, String campo) {
        String val = getText(row, idx, campo);
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
