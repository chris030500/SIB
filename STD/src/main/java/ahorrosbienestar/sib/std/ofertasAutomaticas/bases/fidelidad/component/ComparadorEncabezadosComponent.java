/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

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

	public List<String> compararHeader(String archivoPath) throws IOException {
		System.out.println("compararHeader");
		String nombreHoja = configService.obtenerConfiguracion(70).getVc_valor_propiedad();
		String headersComparar = configService.obtenerConfiguracion(71).getVc_valor_propiedad();
		// String headersComparar = "\"Cliente ID\", \"Nombre Cliente\", \"Region\",
		// \"Sucursal\", \"Fecha Nacimiento\", \"Edad\", \"credito_id\", \"Nombre
		// Producto\", \"Monto Original\", \"Maximo Dias Mora\", \"Telefono Celular\",
		// \"Telefono Recados\", \"Telefono Empleado\", \"Telefono Referencia 1\",
		// \"Telefono Referencia 2\"";

		List<String> incorrectHeaders = new ArrayList<>();
		try (FileInputStream fileInputStream = new FileInputStream(new File(archivoPath))) {
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheet(nombreHoja);

			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				throw new IllegalArgumentException("Fila de encabezado no encontrado en la hoja.");
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
		} // El recurso FileInputStream se cerrará automáticamente aquí

		return incorrectHeaders;
	}

}
