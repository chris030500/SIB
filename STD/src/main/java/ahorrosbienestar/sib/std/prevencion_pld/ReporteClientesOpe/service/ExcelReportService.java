package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;

public interface ExcelReportService {

    /**
     * Genera un Excel con los datos de Clientes y Operaciones
     * y lo escribe directamente en el OutputStream recibido.
     *
     * @param inicio Fecha inicial como Timestamp
     * @param fin Fecha final como Timestamp
     * @param out OutputStream donde se escribirá el Excel
     * @throws IOException en caso de error al escribir el archivo
     */
	void generarExcelClientesYOperaciones(LocalDate inicio, LocalDate fin, OutputStream out) throws IOException;
}

