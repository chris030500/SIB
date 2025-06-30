package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ReporteClientesOpeService {

    private final ReporteClientesOpeRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    public ReporteClientesOpeService(ReporteClientesOpeRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void procesarArchivoPEP(MultipartFile file) throws Exception {
        // Tu lógica para procesar el archivo PEP
    }

    @Transactional
    public void ejecutarSPActualizaPeps() {
        entityManager.createNativeQuery("CALL SIB.SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES()").executeUpdate();
    }
}

