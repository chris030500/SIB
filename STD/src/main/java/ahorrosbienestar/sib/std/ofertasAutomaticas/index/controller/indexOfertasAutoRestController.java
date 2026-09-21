package ahorrosbienestar.sib.std.ofertasAutomaticas.index.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service.ClasificacionBasesService;

@RestController
@RequestMapping("/ofertasAutomaticas")
public class indexOfertasAutoRestController {

	private static final Logger logger =
	        LoggerFactory.getLogger(indexOfertasAutoRestController.class);

	@Autowired
	ClasificacionBasesService clasificacionBasesService;

	@GetMapping("/validarClasificacion")
	public ResponseEntity<?> validarClasificacion(@RequestParam("base") Integer base,
			@RequestParam("nomClasificacion") String nomClasificacion,
			@RequestParam("codigoClasificacion") String codigoClasificacion,
			@RequestParam(value = "clasificacionId", required = false) Integer clasificacionId) {
		logger.info("Ingreso en validarClasificacion");
		String codigoClas = codigoClasificacion.toUpperCase();
		Optional<ClasificacionBases> existeClasificacionBase = clasificacionBasesService.validarExistClasiBase(base, nomClasificacion, codigoClas);
		logger.info("base: " + base);
		logger.info("nomClasificacion: " + nomClasificacion);
		logger.info("codigoClasificacion: " + codigoClas);
		
		if (clasificacionId != null && existeClasificacionBase.isPresent()) {
	        ClasificacionBases existingClasificacion = existeClasificacionBase.get();
	        if (existingClasificacion.getPk_id_clasificacion() == clasificacionId) {
	            // Si el ID del registro existente es igual al ID del registro que se está editando, entonces el registro es el mismo y no hay problema
	            logger.info("Registro encontrado, pero es el mismo que se está editando.");
	            return ResponseEntity.ok("continuar");
	        }
	    }
		
		if(existeClasificacionBase.isPresent()) {
			
			logger.info("Ya existe un registro similar en la clasificacion de bases");
			return ResponseEntity.ok("Ya existe un registro similar en la clasificacion de bases");
		} else {
			logger.info("No existe un registro similar en la clasificacion de bases");
			logger.info("No existe un registro similar en la clasificacion de bases");
			return ResponseEntity.ok("continuar");
			
		}

	}
	
	@GetMapping("/cambiarEstatusClasificacion")
	public ResponseEntity<?> cambiarEstatusClasificacion(@RequestParam("id") Integer id,
			@RequestParam("estatus") Integer estatus) {
		try {
			logger.info("Ingreso en cambiarEstatusClasificacion");
			ClasificacionBases clasificacion = clasificacionBasesService.findById(id).get();
			if (clasificacion == null) {
	            return ResponseEntity.notFound().build();
	        }
			
			if (clasificacion.getI_estatus_clasificacion() == estatus) {
	            return ResponseEntity.badRequest().body("El nuevo estatus es igual al estatus actual.");
	        }
			//clasificacionBasesService.cambiarEstatusClasificacion(id, estatus);
			 //return ResponseEntity.ok("ok");
			return ResponseEntity.ok(clasificacionBasesService.cambiarEstatusClasificacion(id, estatus) );
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@GetMapping("/obtenerDetallesClasificacion")
	public ResponseEntity<?> obtenerDetallesClasificacion(@RequestParam("id") Integer id) {
		logger.info("Ingreso en obtenerDetallesClasificacion");
		
		Optional<ClasificacionBases> clasificacion = clasificacionBasesService.findById(id);
        if (clasificacion.isPresent()) {
            ClasificacionBases clasificacionBases = clasificacion.get();
            // Construye un objeto con los detalles que quieres enviar al frontend
            Map<String, Object> detalles = new HashMap<>();
            detalles.put("baseId", clasificacionBases.getFk_id_base());
            detalles.put("nombreClasificacion", clasificacionBases.getVc_nombre_clasificacion());
            detalles.put("abreviacionClasificacion", clasificacionBases.getVc_abreviacion_clasificacion());
            detalles.put("codigoClasificacion", clasificacionBases.getVc_codigo_clasificacion());
            detalles.put("vigenciaClasificacion", clasificacionBases.getI_vigencia_clasificacion());
            detalles.put("requiereConfiguracionMensaje", clasificacionBases.getI_configuracion_mensaje());
            detalles.put("mensajeClasificacion", clasificacionBases.getVc_mensaje_clasificacion());
            detalles.put("idClasificacion", clasificacionBases.getPk_id_clasificacion());
            return ResponseEntity.ok(detalles);
        } else {
            return ResponseEntity.notFound().build();
        }

	}

}
