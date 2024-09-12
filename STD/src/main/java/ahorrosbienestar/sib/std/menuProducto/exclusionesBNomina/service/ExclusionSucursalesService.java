package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.ExclusionSucursales;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository.IExclusionSucursalesRepository;

@Service
public class ExclusionSucursalesService {
	
	@Autowired
	private IExclusionSucursalesRepository iExclusionSucursalesRepository;
	
	public List<Map<String, Object>> getSucursalesActivos() {
		return iExclusionSucursalesRepository.getSucursalesActivos();
	}
	
	public List<Map<String, Object>> getSucursalesDepurar() {
		return iExclusionSucursalesRepository.getSucursalesDepurar();
	}
	
    public ExclusionSucursales actualizarIndicadorSucursal(Integer idSucursal, Integer indicadorDepurar, Integer idUsuario) {
    	
        ExclusionSucursales sucursal = iExclusionSucursalesRepository.findById(idSucursal).orElse(null);
        if (sucursal != null) {
        	LocalDateTime currentDateTime = LocalDateTime.now();
        	
            sucursal.setI_indicador_depurar(indicadorDepurar); // Actualizar el campo i_indicador_depurar
            sucursal.setDt_fecha_modifico(currentDateTime); // Actualizar el campo dt_fecha_modifico
            sucursal.setI_usuario_modifico(idUsuario); // Actualizar el campo i_usuario_modifico
            return iExclusionSucursalesRepository.save(sucursal); // Guardar el convenio actualizado en la base de datos
        }
        
        return null; 
    	
    }

}
