package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReporteClientesOpeService {

    private final ReporteClientesOpeRepository repo;

    public ReporteClientesOpeService(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void procesarArchivoPEP(MultipartFile file) throws Exception {
        /*List<ReporteClientesOpeModel> filas = ExcelPEPReader.parse(file);
        if (filas.isEmpty()) return;

        AtomicInteger insertados = new AtomicInteger(0);
        AtomicInteger actualizados = new AtomicInteger(0);

        for (ReporteClientesOpeModel fila : filas) {
            repo.findById(fila.getClienteId()).ifPresentOrElse(existing -> {
                if (!Objects.equals(existing.getNombreCompleto(), fila.getNombreCompleto())) {
                    existing.setNombreCompleto(fila.getNombreCompleto());
                    repo.save(existing);
                    actualizados.incrementAndGet();
                }
            }, () -> {
                repo.save(fila);
                insertados.incrementAndGet();
            });
        }

        System.out.println("Insertados: " + insertados.get() + ", Actualizados: " + actualizados.get());*/
    }
}
