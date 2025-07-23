package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;
 
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
 
public class ExcelPEPReader {
    
    private static final Logger logger = Logger.getLogger(ExcelPEPReader.class);
    
     private ExcelPEPReader() {
          }
 
     public static List<DetallePEPsModel> parse(MultipartFile file) {
    	    List<DetallePEPsModel> lista = new ArrayList<>();

    	    try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
    	        Sheet sheet = workbook.getSheetAt(0);

    	        for (Row row : sheet) {
    	            if (row.getRowNum() == 0) continue; // Saltar encabezado

    	            DetallePEPsModel model = new DetallePEPsModel();

    	            try {
    	                // Validar que las celdas existan
    	                if (row.getCell(0) == null || row.getCell(1) == null) {
    	                    logger.warn("Fila incompleta en fila " + row.getRowNum() + ". Se requiere ClienteID y Nombre.");
    	                    continue;
    	                }

    	                // Validar tipo de dato para columna A: ClienteID
    	                if (row.getCell(0).getCellType() != org.apache.poi.ss.usermodel.CellType.NUMERIC) {
    	                    logger.warn("ClienteID no es numérico en fila " + row.getRowNum());
    	                    continue;
    	                }

    	                // Validar tipo de dato para columna B: Nombre del cliente
    	                if (row.getCell(1).getCellType() != org.apache.poi.ss.usermodel.CellType.STRING) {
    	                    logger.warn("Nombre del cliente no es texto en fila " + row.getRowNum());
    	                    continue;
    	                }

    	                model.setI_clienteid((int) row.getCell(0).getNumericCellValue());
    	                model.setVc_nombre_cliente(row.getCell(1).getStringCellValue());
    	                lista.add(model);

    	            } catch (Exception e) {
    	                logger.warn("Error leyendo fila " + row.getRowNum() + ": " + e.getMessage());
    	            }
    	        }

    	    } catch (Exception e) {
    	        logger.error("Error procesando Excel: " + e.getMessage(), e);
    	    }

    	    return lista;
    	}
}