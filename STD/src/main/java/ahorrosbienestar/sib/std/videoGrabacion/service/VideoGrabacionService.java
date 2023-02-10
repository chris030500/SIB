package ahorrosbienestar.sib.std.videoGrabacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.videoGrabacion.repository.IVideoGrabacionRepository;

@Service
public class VideoGrabacionService {

	private IVideoGrabacionRepository iVideoGrabacionRepository;
	
	
	@Autowired
	public VideoGrabacionService(IVideoGrabacionRepository iVideoGrabacionRepository) {
		this.iVideoGrabacionRepository = iVideoGrabacionRepository;
	}

}
