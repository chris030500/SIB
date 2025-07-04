package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
 
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
 
public class ExcelPEPReader {
 
    public static List<DetallePEPsModel> parse(MultipartFile file) throws Exception {
        List<DetallePEPsModel> lista = new ArrayList<>();
 
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
 
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // salta encabezado
 
                DetallePEPsModel model = new DetallePEPsModel();
 
                try {
                    model.setI_clienteid((int) row.getCell(0).getNumericCellValue()); // Columna A
                    model.setVc_nombre_cliente(row.getCell(1).getStringCellValue());  // Columna B
                    lista.add(model);
                } catch (Exception e) {
                    System.out.println("Fila inválida en Excel (fila " + row.getRowNum() + "), error: " + e.getMessage());
                }
            }
        }
 
        return lista;
    }
 
 
 
   /* private static String getText(Row row, Map<String, Integer> idx, String campo) {
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
    }*/
}
