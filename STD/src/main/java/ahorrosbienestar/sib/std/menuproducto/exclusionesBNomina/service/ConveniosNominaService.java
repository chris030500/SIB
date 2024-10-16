package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.ConveniosNomina;
import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository.IConveniosNominaRepository;

@Service
public class ConveniosNominaService {
	
	@Autowired
	private IConveniosNominaRepository iConvenioNominaRepository;
	
	public List<Map<String, Object>> getConveniosNominaActivos(String descCovenio) {
		return iConvenioNominaRepository.getConveniosNominaActivos(descCovenio);
	}
	
	public List<Map<String, Object>> getConveniosNominaDepurar(String descConvenio) {
		return iConvenioNominaRepository.getConveniosNominaDepurar(descConvenio);
	}
	
    public ConveniosNomina actualizarIndicadorConveniosNomina(Integer idConvenio, Integer indicadorDepurar, Integer idUsuario) {
    	
        ConveniosNomina convenioNomina = iConvenioNominaRepository.findById(idConvenio).orElse(null);
        if (convenioNomina != null) {
        	LocalDateTime currentDateTime = LocalDateTime.now();
        	convenioNomina.setI_valor_anterior(convenioNomina.getI_indicador_depurar());
        	
            convenioNomina.setI_indicador_depurar(indicadorDepurar); // Actualizar el campo i_indicador_depurar
            convenioNomina.setDt_fecha_modifico(currentDateTime); // Actualizar el campo dt_fecha_modifico
            convenioNomina.setI_usuario_modifico(idUsuario); // Actualizar el campo i_usuario_modifico
            return iConvenioNominaRepository.save(convenioNomina); // Guardar el convenio actualizado en la base de datos
        }
        
        return null; 
    	
    }
}
