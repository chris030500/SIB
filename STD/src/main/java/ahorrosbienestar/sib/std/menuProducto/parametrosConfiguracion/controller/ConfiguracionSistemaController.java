package ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;


import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.model.ConfiguracionSistema;
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.service.ConfiguracionSistemaService;

@Controller
@RequestMapping("/configuraciones")
public class ConfiguracionSistemaController {
	
	@Autowired
	private ConfiguracionSistemaService configuracionService;
	
	@PostMapping("/guardar-configuracion")
    public String guardarConfiguracion(@RequestParam Map<String, String> params) {
		// Recuperar todas las configuraciones
        List<ConfiguracionSistema> configuraciones = configuracionService.obtenerConfiguraciones();
        // Lista para almacenar configuraciones actualizadas
        List<ConfiguracionSistema> datosActualizar = new ArrayList<>();
       
        
        // Iterar sobre las entradas del Map
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Buscar la configuración correspondiente por nombre de campo
            ConfiguracionSistema configuracion = configuraciones.stream()
                    .filter(c -> c.getVc_nombre_campo().equalsIgnoreCase(key))
                    .findFirst()
                    .orElse(null);

            // Actualizar la configuración si se encuentra
            if (configuracion != null) {
                configuracion.setConfiguracion(value);
                datosActualizar.add(configuracion);
            }
        }
        
        // Guardar las configuraciones actualizadas
        this.configuracionService.guardarConfig(datosActualizar);
        

        // Redirige a la página de éxito o carga nuevamente la página del formulario
        return "redirect:/parametros/configuracion";
    }
}
