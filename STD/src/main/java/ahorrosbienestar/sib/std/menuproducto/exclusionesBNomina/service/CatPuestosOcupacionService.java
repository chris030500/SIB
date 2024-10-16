package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.CatPuestosOcupacion;
import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository.ICatPuestosOcupacionRepository;

@Service
public class CatPuestosOcupacionService {
	
	@Autowired
	private ICatPuestosOcupacionRepository iCatPuestosOcupacionRepository;
	
	public List<Map<String, Object>> getPuestosOcupacionActivos() {
		return iCatPuestosOcupacionRepository.getPuestosOcupacionActivos();
	}
	
	public List<Map<String, Object>> getPuestosOcupacionDepurar() {
		return iCatPuestosOcupacionRepository.getPuestosOcupacionDepurar();
	}
	
    public CatPuestosOcupacion actualizarIndicadorPuestosOcupacion(Integer idPuesto, Integer indicadorDepurar, Integer idUsuario) {
    	
        CatPuestosOcupacion puesto = iCatPuestosOcupacionRepository.findById(idPuesto).orElse(null);
        if (puesto != null) {
        	
        	LocalDateTime currentDateTime = LocalDateTime.now();
        	puesto.setI_valor_anterior(puesto.getI_indicador_depurar());
        	
            puesto.setI_indicador_depurar(indicadorDepurar); // Actualizar el campo i_indicador_depurar
            puesto.setDt_fecha_modifico(currentDateTime); // Actualizar el campo dt_fecha_modifico
            puesto.setI_usuario_modifico(idUsuario); // Actualizar el campo i_usuario_modifico
            return iCatPuestosOcupacionRepository.save(puesto); // Guardar el convenio actualizado en la base de datos
        }
        
        return null; 
    	
    }

}
