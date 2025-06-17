package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReporteClientesOpeService {

    private final ReporteClientesOpeRepository repository;

    @Autowired
    public ReporteClientesOpeService(ReporteClientesOpeRepository repository) {
        this.repository = repository;
    }

    public void procesarArchivoPEP(MultipartFile file) throws Exception {
        // Llamada al lector de Excel para procesar el archivo
        List<ReporteClientesOpeModel> lista = ExcelPEPReader.parse(file);

        // Guardar los datos procesados en la base de datos
        repository.saveAll(lista);
    }
}