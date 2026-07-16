//ExcelPEPReader
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;

public class ExcelPEPReader {

    private static final Logger logger =
            Logger.getLogger(ExcelPEPReader.class);

    private ExcelPEPReader() {}

    public static List<DetallePEPsModel> parse(MultipartFile file) {

        List<DetallePEPsModel> lista = new ArrayList<>();

        try (InputStream is = file.getInputStream()) {

            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // ============================
            // VALIDAR ENCABEZADOS
            // ============================
            Row header = sheet.getRow(0);
            if (header == null) {
                throw new RuntimeException("El archivo no contiene encabezados.");
            }

            String col0 = formatter.formatCellValue(header.getCell(0))
                    .trim().toLowerCase().replace(" ", "");

            String col1 = formatter.formatCellValue(header.getCell(1))
                    .trim().toLowerCase().replace(" ", "");

            if (!"clienteid".equals(col0) || !"nombre_completo".equals(col1)) {
                throw new RuntimeException(
                    "Encabezados inválidos. Se esperan: ClienteID | nombre_completo"
                );
            }

            // ============================
            // LEER FILAS
            // ============================
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String clienteId = formatter.formatCellValue(row.getCell(0)).trim();
                String nombre    = formatter.formatCellValue(row.getCell(1)).trim();

                // Saltar filas completamente vacías
                if (clienteId.isEmpty() && nombre.isEmpty()) {
                    continue;
                }

                if (clienteId.isEmpty()) {
                    throw new RuntimeException(
                        "ClienteID vacío en fila " + (i + 1)
                    );
                }

                DetallePEPsModel model = new DetallePEPsModel();

                try {
                    model.setIClienteId(Integer.parseInt(clienteId));
                } catch (NumberFormatException ex) {
                    throw new RuntimeException(
                        "ClienteID inválido en fila " + (i + 1) + ": [" + clienteId + "]"
                    );
                }

                model.setVcNombreCliente(nombre);
                lista.add(model);
            }

        } catch (Exception e) {
            logger.error("Error leyendo Excel PEP", e);
            throw new RuntimeException(
                e.getMessage() != null
                    ? e.getMessage()
                    : "Error al leer el archivo Excel PEP"
            );
        }

        return lista;
    }
}
