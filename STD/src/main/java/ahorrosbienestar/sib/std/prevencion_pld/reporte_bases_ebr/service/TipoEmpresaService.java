package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.TipoEmpresa;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.ITipoEmpresaRepository;

@Service
public class TipoEmpresaService {
	
	private static final Logger logger = Logger.getLogger(TipoEmpresaService.class);
	
	@Autowired
	private ITipoEmpresaRepository tipoEmpresaRepository;
	
	@Transactional
	public boolean registrarTipoEmpresas(List<TipoEmpresa> empresas) {
		try {
			tipoEmpresaRepository.saveAll(empresas);
			logger.info("Se registraron " + empresas.size() + " empresas correctamente.");
			return true;
			
		} catch(Exception e) {
			logger.error("Error al registrar empresas: " + e.getMessage());
			return false;
		}
	}
	
    public void eliminarTodos() {
    	tipoEmpresaRepository.deleteAll();
    }
}
