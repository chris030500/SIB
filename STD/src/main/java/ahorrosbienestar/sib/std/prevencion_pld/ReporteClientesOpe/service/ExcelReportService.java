package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;

public interface ExcelReportService {
	Path generarExcelClientesYOperaciones(Timestamp ini, Timestamp fin) throws IOException;
	Path generarExcelOperaciones(Timestamp ini, Timestamp fin) throws IOException;
}
