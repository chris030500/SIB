package ahorrosbienestar.sib.std.firma_docto_unificado.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.repository.IFirmaDoctoUnificadoRepository;

@Service
public class FirmaDoctoUnificadoService {

	@Autowired
	private IFirmaDoctoUnificadoRepository iFirmaDoctoUnificadoRepository;

	protected final Log logger = LogFactory.getLog(this.getClass());

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
