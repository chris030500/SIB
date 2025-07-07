package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.springframework.stereotype.Service;
 
@Service
public class ReporteClientesOpeService {
 
    private final ReporteClientesOpeRepository repo;
 
    public ReporteClientesOpeService(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }
}

