package ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.repository.IClasificacionBasesRepository;

@Service
public class ClasificacionBasesService {

	private IClasificacionBasesRepository iClasificacionBasesRepository;

	@Autowired
	public ClasificacionBasesService(IClasificacionBasesRepository iClasificacionBasesRepository) {
		this.iClasificacionBasesRepository = iClasificacionBasesRepository;
	}

	public ClasificacionBases save(ClasificacionBases clasificacionBases) {
		return iClasificacionBasesRepository.save(clasificacionBases);
	}

	public Optional<ClasificacionBases> findById(Integer id) {
		return iClasificacionBasesRepository.findById(id);
	}

	public List<ClasificacionBases> findAll() {
		return iClasificacionBasesRepository.findAll();
	}

	public Optional<ClasificacionBases> validarExistClasiBase(Integer base, String nomClasificacion,
			String codClasificacion) {
		return iClasificacionBasesRepository.validarExistClasiBase(base, nomClasificacion, codClasificacion);
	}

	public ClasificacionBases actualizarClasificacion(ClasificacionBases clasificacionBases) {
		if(iClasificacionBasesRepository.existsById(clasificacionBases.getPk_id_clasificacion())) {
			return iClasificacionBasesRepository.save(clasificacionBases);
		} else {
			return null;
		}
	}

}
