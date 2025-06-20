package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class ExcelPEPReader {

    private static final DataFormatter FMT = new DataFormatter();

    public static List<ReporteClientesOpeModel> parse(MultipartFile file) throws Exception {
        List<ReporteClientesOpeModel> lista = new ArrayList<>();

        try (InputStream in = file.getInputStream();
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) return lista;

            // Mapeo de cabeceras
            Map<String, Integer> idx = new HashMap<>();
            Row header = sheet.getRow(0);
            for (Cell c : header) {
                idx.put(FMT.formatCellValue(c).trim().toLowerCase(), c.getColumnIndex());
            }

            // Lectura de datos
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Integer id = parseInt(row, idx, "cliente_id");
                String nombre = getText(row, idx, "nombre_completo");

                if (id == null || nombre.isEmpty()) continue;

                ReporteClientesOpeModel m = new ReporteClientesOpeModel();
                m.setClienteId(id);
                m.setNombreCompleto(nombre.trim().toUpperCase());

                lista.add(m);
            }
        }
        return lista;
    }

    private static String getText(Row row, Map<String, Integer> idx, String campo) {
        Integer i = idx.get(campo.toLowerCase());
        if (i == null) return "";
        Cell cell = row.getCell(i);
        return cell == null ? "" : FMT.formatCellValue(cell);
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
