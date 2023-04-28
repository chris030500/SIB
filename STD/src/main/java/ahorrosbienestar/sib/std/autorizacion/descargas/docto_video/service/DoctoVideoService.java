package ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model.DoctoVideoModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.repository.DoctoVideoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctoVideoService {
	
	@Autowired
	private DoctoVideoRepository doctoVideoRepository;
	
	public DoctoVideoModel save(DoctoVideoModel model) {
		return doctoVideoRepository.save(model);
	}
	
	public DoctoVideoModel findById(Integer id) {
		return doctoVideoRepository.findById(id).orElse(null);
	}

}
