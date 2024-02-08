package ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Arrays;

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
        List<String> valoresParams = new ArrayList<>(params.values());

        // Actualizar el atributo específico para cada configuración
        int i = 0;
        for (ConfiguracionSistema configuracion : configuraciones) {
        	configuracion.setConfiguracion(valoresParams.get(i));
            i++;
            
            this.configuracionService.guardarConfig(Arrays.asList(configuracion));
        }


        // Redirige a la página de éxito o carga nuevamente la página del formulario
        return "redirect:/parametros/configuracion";
    }
}
