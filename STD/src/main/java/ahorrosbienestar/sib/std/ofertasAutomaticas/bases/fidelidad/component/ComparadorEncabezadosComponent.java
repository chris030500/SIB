/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author osvaldo.escamilla
 *
 */
@Component
public class ComparadorEncabezadosComponent {

    @Autowired
    ConfigService configService;

    private static final Logger logger =
            LoggerFactory.getLogger(ComparadorEncabezadosComponent.class);

    private String propiedadNombreHoja = "hojaFidelidad";
    private String propiedadEncabezados = "encabezadosFidelidad";

    public List<String> compararHeader(String archivoPath) {
        logger.info("compararHeader");
        String nombreHoja = configService.valorPropiedad(propiedadNombreHoja).getVc_valor_propiedad();
        String headersComparar = configService.valorPropiedad(propiedadEncabezados).getVc_valor_propiedad();

        List<String> incorrectHeaders = new ArrayList<>();
        FileInputStream fileInputStream = null;
        
        try {
            fileInputStream = new FileInputStream(new File(archivoPath));
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheet(nombreHoja);

            if (sheet == null) {
                throw new IllegalArgumentException("Hoja '" + nombreHoja + "' no encontrada en el archivo.");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Fila de encabezado no encontrada en la hoja.");
            }

            String[] headersArray = headersComparar.replaceAll("\"", "").split(",\\s*");
            String[] expectedHeaders = new String[headersArray.length];

            for (int i = 0; i < headersArray.length; i++) {
                expectedHeaders[i] = headersArray[i].trim();
            }

            for (int i = 0; i < expectedHeaders.length; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null || !cell.getStringCellValue().equals(expectedHeaders[i])) {
                    incorrectHeaders.add(expectedHeaders[i]);
                }
            }

            logger.info("Comparación de encabezados completada");
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Error al comparar encabezados: " + e.getMessage());
            // Puedes agregar manejo adicional de la excepción aquí
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("Error al cerrar FileInputStream: " + e.getMessage());
                }
            }
        }


        return incorrectHeaders;
    }
}