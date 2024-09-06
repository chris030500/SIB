package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.MunicipiosRepub;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IMunicipiosRepubRepository;

@Service
public class MunicipiosRepubService {
	
	@Autowired
	private IMunicipiosRepubRepository iMunicipiosRepubRepository;
	
	public List<Map<String, Object>> getMunicipiosRepubById(Integer idEstado) {
		return iMunicipiosRepubRepository.getMunicipiosRepub(idEstado);
	}
	
	public List<Map<String, Object>> getMunicipiosConCobertura() {
		return iMunicipiosRepubRepository.getMunicipiosConCobertura();
	}
	
    public MunicipiosRepub updateIndicadorDepurar(Integer idEstado, Integer idMunicipio, Integer indicadorDepurar) {
    	
        // Buscar el municipio con los IDs proporcionados
        Optional<MunicipiosRepub> municipioOptional = iMunicipiosRepubRepository.findByIdEstadoAndIdMunicipio(idEstado, idMunicipio);
        
        if (municipioOptional.isPresent()) {
        	
            MunicipiosRepub municipio = municipioOptional.get();
            // Actualizar el campo i_indicador_depurar
            municipio.setI_indicador_depurar(indicadorDepurar);
            
            // Guardar los cambios en la base de datos
            MunicipiosRepub updatedMunicipio = iMunicipiosRepubRepository.save(municipio);
            return updatedMunicipio;
            
        } else {
            throw new RuntimeException("Municipio no encontrado con ID de Estado: " + idEstado + " y ID de Municipio: " + idMunicipio);
        }
    }
}
