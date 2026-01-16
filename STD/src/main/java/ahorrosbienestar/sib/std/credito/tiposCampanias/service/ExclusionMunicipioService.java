package ahorrosbienestar.sib.std.credito.tiposCampanias.service;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.repository.ClientesActivosRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.exclusionesCampaniasController;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclusionMunicipioModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.repository.ExclusionMunicipioRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;

@Service
public class ExclusionMunicipioService {
	
	private static final Logger logger = Logger.getLogger(ExclusionMunicipioService.class);

	@Autowired
	private ExclusionMunicipioRepository exclusionMunicipioRepository;
	
	@Autowired
	private ClientesActivosRepository clientesActivosRepository;

	public void procesarExcel(MultipartFile archivo) throws Exception {
		
		logger.info("Entra a procesarExcel");
		
		List<ExclusionMunicipioModel> listaExclusiones = new ArrayList<>();

		//try (InputStream input = archivo.getInputStream(); Workbook workbook = new XSSFWorkbook(input)) {
		byte[] bytes = archivo.getBytes();
		try (InputStream input = new ByteArrayInputStream(bytes);
		     Workbook workbook = new XSSFWorkbook(input)) {

			logger.info("Workbook creado correctamente");
			
			Sheet hoja = workbook.getSheetAt(0);
			
			logger.info("Hojas: " + workbook.getNumberOfSheets());
	        logger.info("Última fila: " + hoja.getLastRowNum());

			for (int i = 1; i <= hoja.getLastRowNum(); i++) {
				Row fila = hoja.getRow(i);
				if (fila == null)
					continue;

				ExclusionMunicipioModel excl = new ExclusionMunicipioModel();
				excl.setCodigo((int) getNumericValue(fila, 0));
				excl.setAsentamiento(getStringValue(fila, 1));
				excl.setTipoAsentamiento(getStringValue(fila, 2));
				excl.setMunicipio(getStringValue(fila, 3));
				excl.setEstado(getStringValue(fila, 4));
				excl.setCiudad(getStringValue(fila, 5));
				excl.setCodigoPostal((int) getNumericValue(fila, 6));
				excl.setCodigoEstado((int) getNumericValue(fila, 7));
				excl.setCobertura(getStringValue(fila, 8));

				listaExclusiones.add(excl);
			}

			exclusionMunicipioRepository.saveAll(listaExclusiones);
		}
	}

	private double getNumericValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			try {
				return Double.parseDouble(cell.getStringCellValue().trim());
			} catch (NumberFormatException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	private String getStringValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue());
		} else {
			return "";
		}
	}

	public List<ExclusionMunicipioModel> obtenerTodos() {
        return exclusionMunicipioRepository.findAll();
    }
	
	public List<ClientesActivosModel> obtenerClientes() {
        return clientesActivosRepository.findAll();
    }
}
