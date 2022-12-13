package ahorrosbienestar.sib.std.documentos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.documentos.repository.IDocumentoRepository;


@Service
public class DocumentoService {
	
	private IDocumentoRepository iDocumentoRepository;
	
	@Autowired
	public DocumentoService(IDocumentoRepository iDocumentoRepository) {
		this.iDocumentoRepository = iDocumentoRepository;
	}
	

		public Documento save(Documento usuario) {
			return iDocumentoRepository.save(usuario);
		}
		
		
		public Optional<Documento> findById(Integer id){
			return iDocumentoRepository.findById(id);
		}
		
		
		public List<Documento> findAll(){
			return iDocumentoRepository.findAll();
		}
		
		public Documento edit(Documento documento) {
			return iDocumentoRepository.save(documento);
		}
		
		public Documento findByIdDocumento(Integer id) {
			return iDocumentoRepository.findByIdDocumento(id);
		}

}
