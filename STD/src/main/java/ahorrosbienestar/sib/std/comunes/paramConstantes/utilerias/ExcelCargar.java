package ahorrosbienestar.sib.std.comunes.paramConstantes.utilerias;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;

public class ExcelCargar {
	
		    public static List<DetalleCampaniasTemporalesModel> parseDetalleTemporales(MultipartFile file) throws Exception {
		        List<DetalleCampaniasTemporalesModel> lista = new ArrayList<>();
		 
		        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
		            Sheet sheet = workbook.getSheetAt(0);
		 
		            for (Row row : sheet) {
		                if (row.getRowNum() == 0) continue; // salta encabezado
		 
		                DetalleCampaniasTemporalesModel detalleCmpTmp = new DetalleCampaniasTemporalesModel();
		 
		                try {
		                	detalleCmpTmp.setClienteId((int) row.getCell(0).getNumericCellValue());
		                	detalleCmpTmp.setSolicitudCreditoIdOrigen((int) row.getCell(1).getNumericCellValue());
		                	detalleCmpTmp.setCreditoId((long) row.getCell(2).getNumericCellValue());
		                	detalleCmpTmp.setMontoCreditoOrigen((double) row.getCell(3).getNumericCellValue());
		                	detalleCmpTmp.setPlazoOfertaId((int) row.getCell(4).getNumericCellValue());
		                	detalleCmpTmp.setNumeroCuotas((int) row.getCell(5).getNumericCellValue());
		                	detalleCmpTmp.setFrecuenciaOferta(row.getCell(6).getStringCellValue());
		                	detalleCmpTmp.setTipoCredito(row.getCell(7).getStringCellValue());
		                	detalleCmpTmp.setPorcentajeIncrementoOferta((double) row.getCell(8).getNumericCellValue());
		                	detalleCmpTmp.setMontoOferta((double) row.getCell(9).getNumericCellValue());
		                	detalleCmpTmp.setTasaOrdinariaOferta((double) row.getCell(10).getNumericCellValue());
		                	detalleCmpTmp.setCuotaOferta((double) row.getCell(11).getNumericCellValue());
		                	detalleCmpTmp.setProductoIdOferta((int) row.getCell(5).getNumericCellValue());
		                	
		        
		                    lista.add(detalleCmpTmp);
		                } catch (Exception e) {
		                    System.out.println("Fila inválida en Excel (fila " + row.getRowNum() + "), error: " + e.getMessage());
		                }
		            }
		        }
		 
		        return lista;
		    }
	   
	   
}
