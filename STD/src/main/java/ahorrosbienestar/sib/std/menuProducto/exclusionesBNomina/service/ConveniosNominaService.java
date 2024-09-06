package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.ConveniosNomina;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IConveniosNominaRepository;

@Service
public class ConveniosNominaService {
	
	@Autowired
	private IConveniosNominaRepository iConvenioNominaRepository;
	
	public List<Map<String, Object>> getConveniosNominaActivos() {
		return iConvenioNominaRepository.getConveniosNominaActivos();
	}
	
	public List<Map<String, Object>> getConveniosNominaDepurar() {
		return iConvenioNominaRepository.getConveniosNominaDepurar();
	}
	
    public ConveniosNomina actualizarIndicadorConveniosNomina(Integer idConvenio, Integer indicadorDepurar, Integer idUsuario) {
    	
        ConveniosNomina convenioNomina = iConvenioNominaRepository.findById(idConvenio).orElse(null);
        if (convenioNomina != null) {
        	LocalDate today = LocalDate.now();
        	Date currentDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	
            convenioNomina.setI_indicador_depurar(indicadorDepurar); // Actualizar el campo i_indicador_depurar
            convenioNomina.setDt_fecha_modifico(currentDay); // Actualizar el campo dt_fecha_modifico
            convenioNomina.setI_usuario_modifico(idUsuario); // Actualizar el campo i_usuario_modifico
            return iConvenioNominaRepository.save(convenioNomina); // Guardar el convenio actualizado en la base de datos
        }
        
        return null; 
    	
    }
}
