package ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.model.BitacoraZipModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.repository.BitacoraZipRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BitacoraZipService {

	@Autowired
	private BitacoraZipRepository bitacoraZipRepository;
	
	public BitacoraZipModel save(BitacoraZipModel model) {
		return bitacoraZipRepository.save(model);
	}
	
	public BitacoraZipModel findById(Integer id) {
		return bitacoraZipRepository.findById(id).orElse(null);
	}
	
}
