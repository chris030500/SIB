package ahorrosbienestar.sib.std.coord_doc.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.coord_doc.model.CoordenadaDocumento;
import ahorrosbienestar.sib.std.coord_doc.repository.ICoordenadaDocumentoRepository;

@Service
public class CoordenadaDocumentoService {

	private ICoordenadaDocumentoRepository iCoordenadaDocumentoRepository;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public CoordenadaDocumentoService(ICoordenadaDocumentoRepository iCoordenadaDocumentoRepository) {
		this.iCoordenadaDocumentoRepository = iCoordenadaDocumentoRepository;
	}
	
	//Metodo para mostrar lista de las coordenadas de firma
	
	public List<CoordenadaDocumento> obtenerCoordenadaDocto(Integer idDocumento){
		return iCoordenadaDocumentoRepository.obtenerCoordenadaDocto(idDocumento);
	}
}
