package ahorrosbienestar.sib.std.filesCSO.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.repository.IArchivosFoliosRepository;

@Service
public class ArchivosFoliosService {

	@Autowired
	private IArchivosFoliosRepository iArchivosFoliosRepository;
	
	@Autowired
	public ArchivosFoliosService(IArchivosFoliosRepository iArchivosFoliosRepository) {
		this.iArchivosFoliosRepository = iArchivosFoliosRepository;
	}

	public ArchivosFolios registrar(ArchivosFolios alta) {
		return iArchivosFoliosRepository.save(alta);
	}

	public List<ArchivosFolios> obtRegistros(Integer idFolio) {
		return iArchivosFoliosRepository.obtArchivos(idFolio);
	}

	public Optional<ArchivosFolios> porID(Integer idArchivo) {
		
		return iArchivosFoliosRepository.findById(idArchivo);
	}

	public int actualizar(Integer idArchivo) {
		return iArchivosFoliosRepository.actualizar(idArchivo);
	}

	public int existeNombre(String name, String idFolio) {
		return iArchivosFoliosRepository.existeNombre(name,idFolio);
	}

}
