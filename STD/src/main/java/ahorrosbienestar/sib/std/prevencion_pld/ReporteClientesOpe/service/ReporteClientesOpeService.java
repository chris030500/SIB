package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteClientesOpeService {

    private final ReporteClientesOpeRepository repo;

    @Autowired
    public ReporteClientesOpeService(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void procesarArchivoPEP(MultipartFile file) throws Exception {
        List<ReporteClientesOpeModel> registros = leerExcel(file.getInputStream());
        if (registros.isEmpty()) return;
        repo.saveAll(registros);
    }

    /* ---------- Lectura de Excel ---------- */
    private List<ReporteClientesOpeModel> leerExcel(InputStream in) throws Exception {
        List<ReporteClientesOpeModel> lista = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        try (Workbook wb = WorkbookFactory.create(in)) {
            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) return lista;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;  // cabecera

                ReporteClientesOpeModel m = new ReporteClientesOpeModel();
                int c = 0;
                m.setclienteId(parseInteger(fmt, row.getCell(c++)));
                m.setEstatusCliente (fmt.formatCellValue(row.getCell(c++)));
                m.setFechaAlta      (parseDate(fmt, row.getCell(c++)));
                m.setTipoCliente    (fmt.formatCellValue(row.getCell(c++)));
                m.setNumeroCliente  (parseInteger(fmt, row.getCell(c++)));
                m.setNombreCompleto (fmt.formatCellValue(row.getCell(c++)));
                m.setGenero         (fmt.formatCellValue(row.getCell(c++)));
                /* …sigue mapeando según tu layout… */
                m.setMontoOperacion (parseDecimal(row.getCell(c++)));

                lista.add(m);
            }
        }
        return lista;
    }

    /* ---------- Helpers ---------- */
    private Integer parseInteger(DataFormatter fmt, Cell c) {
        String s = fmt.formatCellValue(c);
        return s.isEmpty() ? null : Integer.valueOf(s);
    }

    private BigDecimal parseDecimal(Cell c) {
        return (c == null) ? null : BigDecimal.valueOf(c.getNumericCellValue());
    }

    private LocalDate parseDate(DataFormatter fmt, Cell c) {
        if (c == null) return null;
        if (c.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(c)) {
            return c.getLocalDateTimeCellValue().toLocalDate();
        }
        String s = fmt.formatCellValue(c);
        return s.isEmpty() ? null : LocalDate.parse(s);
    }
}

