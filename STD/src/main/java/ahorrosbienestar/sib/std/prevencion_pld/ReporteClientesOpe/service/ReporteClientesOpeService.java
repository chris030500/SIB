package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;

@Service
public class ReporteClientesOpeService {

    @Autowired
    private ReporteClientesOpeRepository reporteClientesOpeRepository;

    /**
     * Lee un archivo Excel (.xlsx) desde una ruta en disco y lo convierte en una lista de modelos.
     */
    public List<ReporteClientesOpeModel> leerArchivoExcel(String filePath) {
        List<ReporteClientesOpeModel> datosExcel = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Primera hoja
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar encabezado

                ReporteClientesOpeModel registro = new ReporteClientesOpeModel();

                // Asegúrate de validar celdas nulas antes de acceder
                

                if (row.getCell(1) != null)
                    registro.setnombre(row.getCell(1).getStringCellValue());


                // Agrega más campos según necesidad...

                datosExcel.add(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datosExcel;
    }

    /**
     * Procesa y compara los datos recibidos con la base de datos.
     * Inserta o actualiza registros si corresponde.
     */
    @Transactional
    public void procesarYComparar(List<ReporteClientesOpeModel> lista, String idFolio) {
        if (lista == null || lista.isEmpty()) return;

        for (ReporteClientesOpeModel registro : lista) {
            if (registro.getclienteId() == null || registro.getclienteId().isEmpty()) continue;

            Optional<ReporteClientesOpeModel> registroExistenteOpt =
                reporteClientesOpeRepository.findByClienteId(registro.getclienteId());

            ReporteClientesOpeModel entidad;
            if (registroExistenteOpt.isPresent()) {
                entidad = registroExistenteOpt.get();
                entidad.setnombre(registro.getnombre());
                entidad.setfechaOperacion(registro.getfechaOperacion());
                // Otros campos...
            } else {
                entidad = registro;
            }

           // entidad.setIdFolio(idFolio);
            reporteClientesOpeRepository.save(entidad);
        }
    }
}
