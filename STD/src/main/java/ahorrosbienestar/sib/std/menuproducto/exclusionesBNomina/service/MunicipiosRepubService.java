package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.MunicipiosRepub;
import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository.IMunicipiosRepubRepository;

@Service
public class MunicipiosRepubService {
	
	private static final Logger logger = Logger.getLogger(MunicipiosRepubService.class);
	
	@Autowired
	private IMunicipiosRepubRepository iMunicipiosRepubRepository;
	
	public List<Map<String, Object>> getMunicipiosRepubById(Integer idEstado) {
		return iMunicipiosRepubRepository.getMunicipiosRepub(idEstado);
	}
	
	public List<Map<String, Object>> getMunicipiosConCobertura() {
		return iMunicipiosRepubRepository.getMunicipiosConCobertura();
	}
	
    public MunicipiosRepub actualizarIndicadorMunicipiosRepub(Integer idEstado, Integer idMunicipio, Integer indicadorDepurar, Integer idUsuario) {
    	try {
    		
    	       MunicipiosRepub municipio = iMunicipiosRepubRepository.findByIdEstadoAndIdMunicipio(idEstado, idMunicipio).orElse(null);
    	       
    	       
    	        if (municipio != null) {
    	        	
    	        	LocalDateTime currentDateTime = LocalDateTime.now();
    	        	municipio.setI_valor_anterior(municipio.getI_indicador_depurar());
    	        	
    	        	municipio.setI_indicador_depurar(indicadorDepurar);
    	        	municipio.setDt_fecha_modifico(currentDateTime);
    	        	municipio.setI_usuario_modifico(idUsuario);
    	        	
    	        
    	            return iMunicipiosRepubRepository.save(municipio);
    	        }
    		
    	} catch (Exception e) {
    		
    		logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			
		}
    	
    	return null;
    } 
}
