package ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
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
	
	public int cambiarEstatusClasificacion(Integer idClasificacion, Integer estatus) {
		return iClasificacionBasesRepository.cambiarEstatusClasificacion(idClasificacion, estatus);
	}

	public int existeClasificacion(Integer idBase, String nomClasificacion, String abrevacion) {
	
		return iClasificacionBasesRepository.existeClasificacion(idBase,nomClasificacion,abrevacion);
	}

	public int updateClasificacion(Integer idClass, Integer idBase, String nomClasificacion, String abrevacion,
			String codigoClasificacion, Integer vigenciaClasificacion, String mensajeClasificacion) {
	
		return iClasificacionBasesRepository.updateClasificacion(idClass,idBase,nomClasificacion,abrevacion,codigoClasificacion,vigenciaClasificacion,mensajeClasificacion);
	}

	public int activarClasificacion(Integer idClass) {
		
		return iClasificacionBasesRepository.activarClasificacion(idClass);
	}

	public int bajaClasificacion(Integer idClass) {
		
		return iClasificacionBasesRepository.bajaClasificacion(idClass);
	}

	public int existeCodigo(String codigoClasificacion) {
		return iClasificacionBasesRepository.existeCodigo(codigoClasificacion);
	}

	public int existeCodigoMod(String codigoClasificacion, Integer idClass) {
		return iClasificacionBasesRepository.existeCodigoMod(codigoClasificacion,idClass);
	}

}
