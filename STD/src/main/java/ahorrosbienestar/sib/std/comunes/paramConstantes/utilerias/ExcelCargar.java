package ahorrosbienestar.sib.std.comunes.paramConstantes.utilerias;

import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.ResultadoCargaExcel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

public class ExcelCargar {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(ExcelCargar.class);
			
		    public static ResultadoCargaExcel parseDetalleTemporales(MultipartFile file, Optional<TiposCampaniasModel>  campania, Usuario usuario) throws Exception {
		        
		    	 ResultadoCargaExcel resultado = new ResultadoCargaExcel();
		    	 
		        
		        
		        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
		            Sheet sheet = workbook.getSheetAt(0);
		 
		            for (Row row : sheet) {
		                if (row.getRowNum() == 0) continue; // salta encabezado
		 
		                DetalleCampaniasTemporalesModel detalleCmpTmp = new DetalleCampaniasTemporalesModel();
		 
		                try {
		                	StringBuilder errorFila = new StringBuilder();
		                	
		                	// clienteID
		                	if (row.getCell(0) == null || row.getCell(0).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("ClienteId inválido. ");
		                    } else {
		                    	  detalleCmpTmp.setClienteId((int) row.getCell(0).getNumericCellValue());
		                    }
		                	
		                	// solicitud de SolicitudCreditoIdOrigen
		                	if (row.getCell(1) == null || row.getCell(1).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("SolicitudCreditoIdOrigen inválido. ");
		                    } else {
			                	detalleCmpTmp.setSolicitudCreditoIdOrigen((int) row.getCell(1).getNumericCellValue());
		                    }
		                	// creditoID
		                	if (row.getCell(2) == null || row.getCell(2).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("Credito inválido. ");
		                    } else {
			                	detalleCmpTmp.setCreditoId((long) row.getCell(2).getNumericCellValue());
		                    }
		                	// Monto credito
		                	if (row.getCell(3) == null || row.getCell(3).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("Monto credito inválido. ");
		                    } else {
		                    	detalleCmpTmp.setMontoCreditoOrigen((double) row.getCell(3).getNumericCellValue());
		                    }
		                	
		                	//numero de cuotas
		                	if (row.getCell(4) == null || row.getCell(4).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("numero de cuotas inválido. ");
		                    } else {
		                    	detalleCmpTmp.setNumeroCuotas((int) row.getCell(4).getNumericCellValue());
		                    }
		                	
		                	//Frecuencia de oferta
		                	if(row.getCell(5) == null || row.getCell(5).getCellType() != CellType.STRING) {
		                		errorFila.append("Frecuencia de oferta inválido. ");
		                		
		                	}else {
		                		detalleCmpTmp.setFrecuenciaOferta(row.getCell(5).getStringCellValue());
		                	}
		                	
		                	//tipo de credito
		                	if(row.getCell(6) == null || row.getCell(6).getCellType() != CellType.STRING) {
		                		errorFila.append("TipoCredito inválido. ");
		                	}else {		                		
		                			detalleCmpTmp.setTipoCredito(row.getCell(6).getStringCellValue());
		                	}
		                	
		                	//Monto oferta
		                	if (row.getCell(7) == null || row.getCell(7).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("Monto oferta inválido. ");
		                    } else {
		                    	detalleCmpTmp.setMontoOferta((double) row.getCell(7).getNumericCellValue());
		                    }
		                
		                	//Tasa Ordinaria Ofertas
		                	if (row.getCell(8) == null || row.getCell(8).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("Monto oferta inválido. ");
		                    } else {
		                    	detalleCmpTmp.setTasaOrdinariaOferta((double) row.getCell(8).getNumericCellValue());
		                    }
		                	//ID PRODUCTO OFERTA
		                	if (row.getCell(9) == null || row.getCell(9).getCellType() != CellType.NUMERIC) {
		                          errorFila.append("id producto oferta inválido. ");
		                    } else {
		                    	detalleCmpTmp.setProductoIdOferta((int) row.getCell(9).getNumericCellValue());
		                    }
		                	
		                	// registrar idcampaña
		                	if(campania.get().getPk_id_tipo_campania() == 0) {
		                		errorFila.append("No existe campaña relacionada ");
		                	}else {
		                		detalleCmpTmp.setFkCampanias(campania.get());
		                	}
		                	// registrar usuario
		                	if(usuario.getPk_id_usuario() == 0) {
		                		errorFila.append("Usuario null ");
		                	}else {
		                		detalleCmpTmp.setUserReg(usuario);
		                	}
		                	
		                	
		                	// reglas validacion
		                	if(detalleCmpTmp.getTipoCredito().trim().equals("R") && (detalleCmpTmp.getCreditoId()== 0 || detalleCmpTmp.getCreditoId() == null)) {
		                		errorFila.append("No se ha informado el número de crédito. Para el tipo de crédito R, es obligatorio informar el número de crédito.");
		                	}
		                	
		                	
		                	// Si hay errores
		                    if (errorFila.length() > 0) {
		                        resultado.getIncidencias().add(
		                                "Fila " + (row.getRowNum() + 1) + ": " + errorFila.toString()
		                        );
		                    } else {
		                        resultado.getRegistros().add(detalleCmpTmp);
		                    }
		                	
		                    
		                } catch (Exception e) {
		                	logger.error("Fila inválida en Excel (fila " + row.getRowNum() + "), error: " + e.getMessage());
		                	resultado.getIncidencias().add(
		                            "Fila " + (row.getRowNum() + 1) + ": Error inesperado - " + e.getMessage()
		                    );
		                }
		            }
		            
		        }
		        resultado.setTieneIncidencias(!resultado.getIncidencias().isEmpty());

		        return resultado;
		    }
	   
	   
}
