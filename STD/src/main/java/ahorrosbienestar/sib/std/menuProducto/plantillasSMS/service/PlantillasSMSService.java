package ahorrosbienestar.sib.std.menuproducto.plantillasSMS.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.plantillasSMS.model.PlantillasSMS;
import ahorrosbienestar.sib.std.menuproducto.plantillasSMS.repository.IPlantillasSMSRespository;

@Service
public class PlantillasSMSService {
	private static final Logger logger = Logger.getLogger(PlantillasSMSService.class);
	private IPlantillasSMSRespository iPlantillasSMSRespository;
	
	@Autowired
	public PlantillasSMSService(IPlantillasSMSRespository iPlantillasSMSRespository) {
		this.iPlantillasSMSRespository = iPlantillasSMSRespository;
	}

	public PlantillasSMS save(PlantillasSMS plantillasSMS) {
		try {
			return iPlantillasSMSRespository.save(plantillasSMS);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
		
	}

	public List<PlantillasSMS> obtPlantillas() {
		return iPlantillasSMSRespository.obtPlantillas();
	}

	public Optional<PlantillasSMS> findById(Integer idPlantilla) {
		return iPlantillasSMSRespository.findById(idPlantilla);
	}

	public int existeRegistro(Integer slcCampania, Integer slcClasificacion) {
		return iPlantillasSMSRespository.existeRegistro(slcCampania,slcClasificacion);
	}
	
	public int existeRegistroMod(Integer slcCampania, Integer slcClasificacion,Integer idPlantilla) {
		return iPlantillasSMSRespository.existeRegistroMod(slcCampania,slcClasificacion,idPlantilla);
	}

	public int existeNombre(String nombrePlantilla) {
	
		return iPlantillasSMSRespository.existeNombre(nombrePlantilla);
	}

	public int existeNombreMod(String nombrePlantilla, Integer idPlantilla) {
		return iPlantillasSMSRespository.existeNombreMod(nombrePlantilla,idPlantilla);
	}
}
