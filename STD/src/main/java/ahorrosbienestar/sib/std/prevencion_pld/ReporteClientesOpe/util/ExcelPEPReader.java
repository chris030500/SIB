package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelPEPReader {

    public static List<ReporteClientesOpeModel> parse(MultipartFile file) throws Exception {
        List<ReporteClientesOpeModel> lista = new ArrayList<>();
        try (InputStream in = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(in);  // Crea el objeto Workbook a partir del archivo Excel
            Sheet sheet = workbook.getSheetAt(0);  // Obtiene la primera hoja del archivo Excel

            // Iteramos sobre las filas de la hoja, comenzando desde la fila 1 (saltando la cabecera)
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;  // Saltar la fila de encabezados

                ReporteClientesOpeModel cliente = new ReporteClientesOpeModel();

                // Asignar valores a cada campo del modelo a partir de las celdas del Excel
                cliente.setClienteId((int) row.getCell(0).getNumericCellValue());  // Cliente ID
                cliente.setEstatusCliente(row.getCell(1).getStringCellValue());  // Estatus Cliente
                //cliente.setFechaAlta(row.getCell(2).getDateCellValue());  // Fecha Alta
                cliente.setTipoCliente(row.getCell(3).getStringCellValue());  // Tipo Cliente
                cliente.setNumeroCliente((int) row.getCell(4).getNumericCellValue());  // Número Cliente
                cliente.setNombreCompleto(row.getCell(5).getStringCellValue());  // Nombre Completo
                cliente.setGenero(row.getCell(6).getStringCellValue());  // Género
                //cliente.setMontoOperacion(row.getCell(7).getNumericCellValue());  // Monto Operación
                //cliente.setFechaOperacion(row.getCell(8).getDateCellValue());  // Fecha Operación

                lista.add(cliente);  // Agregar el cliente a la lista
            }
        }
        return lista;  // Retornar la lista de objetos ReporteClientesOpeModel
    }
}
