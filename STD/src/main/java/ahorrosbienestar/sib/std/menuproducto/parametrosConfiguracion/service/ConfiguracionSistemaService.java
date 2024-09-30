package ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.model.ConfiguracionSistema;
import ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.repository.ConfiguracionSistemaRepository;

@Service
public class ConfiguracionSistemaService {
	
	@Autowired
    private ConfiguracionSistemaRepository configuracionRepository;
	
	public List<ConfiguracionSistema> obtenerConfiguraciones() {
        return configuracionRepository.findAll();
    }
	
	public void guardarConfig(List<ConfiguracionSistema> configuracion) {
	      this.configuracionRepository.saveAll(configuracion);
	}

	public int buscarConfiguracion(String campoParametro, String usuario) {
		// TODO Auto-generated method stub
		return this.configuracionRepository.buscarConfiguracion(campoParametro,usuario);
	}
	
}
