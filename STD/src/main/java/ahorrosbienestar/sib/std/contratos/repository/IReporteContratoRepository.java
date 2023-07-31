package ahorrosbienestar.sib.std.contratos.repository;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;


public interface IReporteContratoRepository {
	
	ResponseEntity<Resource> export(int id, int solicitud);
	
}
