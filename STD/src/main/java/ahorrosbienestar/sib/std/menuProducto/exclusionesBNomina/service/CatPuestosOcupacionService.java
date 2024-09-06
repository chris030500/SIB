package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.CatPuestosOcupacion;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.ICatPuestosOcupacionRepository;

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
    	
        CatPuestosOcupacion puestos = iCatPuestosOcupacionRepository.findById(idPuesto).orElse(null);
        if (puestos != null) {
        	
        	LocalDate today = LocalDate.now();
        	Date currentDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	
            puestos.setI_indicador_depurar(indicadorDepurar); // Actualizar el campo i_indicador_depurar
            puestos.setDt_fecha_modifico(currentDay); // Actualizar el campo dt_fecha_modifico
            puestos.setI_usuario_modifico(idUsuario); // Actualizar el campo i_usuario_modifico
            return iCatPuestosOcupacionRepository.save(puestos); // Guardar el convenio actualizado en la base de datos
        }
        
        return null; 
    	
    }

}
