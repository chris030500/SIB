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
 
    public static List<DetallePEPsModel> parse(MultipartFile file){
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
                        logger.info("Fila inválida en Excel (fila " + row.getRowNum() + "), error: " + e.getMessage());
                    }
                }
            }
            catch (Exception e) {
    			logger.error("Error DetallePEPsModel" + e.getMessage());
    			return lista;
    		}
     
            return lista;
        
    }
}
