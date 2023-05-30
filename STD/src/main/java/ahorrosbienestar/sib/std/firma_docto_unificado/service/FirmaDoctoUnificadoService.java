package ahorrosbienestar.sib.std.firma_docto_unificado.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.repository.IFirmaDoctoUnificadoRepository;

@Service
public class FirmaDoctoUnificadoService {

	@Autowired
	private IFirmaDoctoUnificadoRepository iFirmaDoctoUnificadoRepository;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(FirmaDoctoUnificadoService.class);

	@Autowired
	public FirmaDoctoUnificadoService(IFirmaDoctoUnificadoRepository iFirmaDoctoUnificadoRepository) {
		this.iFirmaDoctoUnificadoRepository = iFirmaDoctoUnificadoRepository;
	}

	// Metodo para guardar en TBL_FIRMA_DOCT_UNIFICADO
	public FirmaDoctoUnificado save(FirmaDoctoUnificado firmaDoctoUnificado) {
		return iFirmaDoctoUnificadoRepository.save(firmaDoctoUnificado);
	}
	
	// Metodo para obtener todas las firmas que se tienen contemplados en el archivo unificado
	public List<FirmaDoctoUnificado> obtenerCoordenadasFirma(Integer idDoctUnificado){
		return iFirmaDoctoUnificadoRepository.obtenerCoordenadasFirma(idDoctUnificado);
	}

}
