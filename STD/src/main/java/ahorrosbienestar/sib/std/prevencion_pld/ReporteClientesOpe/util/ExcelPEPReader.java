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

    	    try {
    	        logger.info("Nombre del archivo: " + file.getOriginalFilename());
    	        logger.info("Tamaño del archivo: " + file.getSize());

    	        InputStream is = file.getInputStream();
    	        logger.info("InputStream abierto");

    	        Workbook workbook = WorkbookFactory.create(is);
    	        logger.info("Workbook creado");

    	        Sheet sheet = workbook.getSheetAt(0);
    	        logger.info("Hoja obtenida: " + sheet.getSheetName());

    	        if (sheet.getPhysicalNumberOfRows() < 2) {
    	            logger.warn("La hoja no contiene datos suficientes (menos de 2 filas)");
    	            return lista;
    	        }

    	        for (Row row : sheet) {
    	            if (row.getRowNum() == 0) continue;

    	            if (row.getCell(0) == null || row.getCell(1) == null) {
    	                logger.warn("Fila vacía o incompleta en fila " + row.getRowNum());
    	                continue;
    	            }

    	            if (row.getCell(0).getCellType() != org.apache.poi.ss.usermodel.CellType.NUMERIC ||
    	                row.getCell(1).getCellType() != org.apache.poi.ss.usermodel.CellType.STRING) {
    	                logger.warn("Tipo de dato inválido en fila " + row.getRowNum());
    	                continue;
    	            }

    	            DetallePEPsModel model = new DetallePEPsModel();
    	            model.setI_clienteid((int) row.getCell(0).getNumericCellValue());
    	            model.setVc_nombre_cliente(row.getCell(1).getStringCellValue());
    	            lista.add(model);
    	        }

    	        logger.info("Archivo leído. Total de filas: " + lista.size());

    	    } catch (Exception e) {
    	        logger.error("🚨 ERROR en ExcelPEPReader.parse(): " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
    	        throw new RuntimeException("Fallo en lectura del archivo Excel: " + e.getMessage(), e);
    	    }

    	    return lista;
    	}

}