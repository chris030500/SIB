package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
 
@Service
public class ReporteClientesOpeService {
 
    private final ReporteClientesOpeRepository repo;
 
    public ReporteClientesOpeService(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }
 
    @Transactional
    public void procesarArchivoPEP(MultipartFile file) throws Exception {
    }
}

