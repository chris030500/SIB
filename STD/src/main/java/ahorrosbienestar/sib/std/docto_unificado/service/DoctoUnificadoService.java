package ahorrosbienestar.sib.std.docto_unificado.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;
import ahorrosbienestar.sib.std.docto_unificado.repository.IDoctoUnificadoRepository;

@Service
public class DoctoUnificadoService {
	
	@Autowired
	private IDoctoUnificadoRepository iDoctoUnificadoRepository;
	
	//protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final Logger logger =
	        LoggerFactory.getLogger(DoctoUnificadoService.class);
	
	@Autowired
	public DoctoUnificadoService(IDoctoUnificadoRepository iDoctoUnificadoRepository) {
		this.iDoctoUnificadoRepository = iDoctoUnificadoRepository;
	}
	
	//Metodo para guardar el doctoUnificado
	public DoctoUnificado save(DoctoUnificado doctoUnificado) {
		return iDoctoUnificadoRepository.save(doctoUnificado);
	}
	
	//Metodo para buscar por ID un Documento Unificado
	public Optional<DoctoUnificado> findById(Integer idDoctoUnificado){
		return iDoctoUnificadoRepository.findById(idDoctoUnificado);
	}
	
	//Metodo para buscar por solicitud de envio en TBL_DOCTO_UNIFICADO
	public Optional<DoctoUnificado> findByFkIdSolicituEnvio(Integer idSolicitudEnvio){
		return iDoctoUnificadoRepository.findByFkIdSolicituEnvio(idSolicitudEnvio);
	}
	
	public List<DoctoUnificado> findPdfXml(Integer solicitudEnvio){
		return iDoctoUnificadoRepository.findPdfXml(solicitudEnvio);
	}
	
	
	
	
}
