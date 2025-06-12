package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import lombok.RequiredArgsConstructor;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.IReporteClientesOpeRepository;
import ch.qos.logback.classic.Logger;
import org.apache.poi.sl.usermodel.Sheet;

@Service
@RequiredArgsConstructor
public class ReporteClientesOpeServiceImpl implements IReporteClientesOpeService {

    private static final Logger logger = Logger.getLogger(ReporteClientesOpeServiceImpl.class);

    private final IReporteClientesOpeRepository iReporteClientesOpeRepository;

    @Override
    public Map<String, String> procesarArchivoPeps(MultipartFile archivo) {
        List<ReporteClientesOpeModel> registros = new ArrayList<>();
        try (InputStream is = archivo.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet hoja = workbook.getSheetAt(0);
            boolean encabezado = true;

            for (Row fila : hoja) {
                if (encabezado) {
                    encabezado = false;
                    continue;
                }

                ReporteClientesOpeModel modelo = new ReporteClientesOpeModel();
                modelo.setCampo1(obtenerValorCelda(fila.getCell(0)));
                modelo.setCampo2(obtenerValorCelda(fila.getCell(1)));
                modelo.setCampo3(obtenerValorCelda(fila.getCell(2)));

                registros.add(modelo);
            }

            iReporteClientesOpeRepository.saveAll(registros);
            return Map.of("message", "Archivo cargado correctamente.");
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage(), e);
            return Map.of("message", "Error al cargar archivo: " + e.getMessage());
        }
    }

    private String obtenerValorCelda(Cell celda) {
        if (celda == null) return null;
        return switch (celda.getCellType()) {
            case STRING -> celda.getStringCellValue();
            case NUMERIC -> String.valueOf(celda.getNumericCellValue());
            case BOOLEAN -> String.valueOf(celda.getBooleanCellValue());
            default -> null;
        };
    }
}
