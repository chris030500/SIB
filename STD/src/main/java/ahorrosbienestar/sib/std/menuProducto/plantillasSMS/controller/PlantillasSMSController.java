package ahorrosbienestar.sib.std.menuProducto.plantillasSMS.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.plantillasSMS.model.PlantillasSMS;
import ahorrosbienestar.sib.std.menuProducto.plantillasSMS.service.PlantillasSMSService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service.ClasificacionBasesService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("plantillas")
public class PlantillasSMSController {

	private static final Logger logger = Logger.getLogger(PlantillasSMSController.class);
	
	@Autowired
	private ClasificacionBasesService clasificacionBasesService;
	
	@Autowired
	private PlantillasSMSService plantillasSMSService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private BasesService basesService;
	
	private Usuario obtenerUsuarioAutenticado() {

	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (!(principal instanceof UserDetails)) {
	        return null;
	    }

	    UserDetails userDetails = (UserDetails) principal;
	    List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

	    if (usuario == null || usuario.isEmpty()) {
	        return null;
	    }

	    return usuario.get(0);
	}
	
	@GetMapping("/obtClasificacion")
	public ResponseEntity<?> obtClasificacion(@RequestParam("idBase") Integer idBase){
		try {
			logger.info("/obtClasificacion"+idBase);
			List<ClasificacionBases> listaClasificacion = clasificacionBasesService.clasificacionXBase(idBase);
			return ResponseEntity.ok((listaClasificacion != null)?listaClasificacion:0);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}
	
	}
	
	@GetMapping("/obtPlantillas")
	public ResponseEntity<List<PlantillasSMS>> obtPlantillas(Model model) {
		 logger.info("/obtPlantillas");
		try {
			return ResponseEntity.ok(plantillasSMSService.obtPlantillas());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	
	
	
	//Dar de alta una observacion metodo POST
	@PostMapping("/registrarPlantillas")
	public ResponseEntity<?> registrarPlantillas(
			@RequestParam(name = "nombrePlantilla") String nombrePlantilla, 
			@RequestParam(name = "slcCampania") Integer slcCampania,
			@RequestParam(name = "slcClasificacion") Integer slcClasificacion,
			@RequestParam(name = "txtContenido") String txtContenido,
			@RequestParam(name = "slcEstatus") Integer slcEstatus
			){
		logger.info("/registrarPlantillas"+nombrePlantilla);
		String mensaje = Constantes.REGISTRO_OK;
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		

		try {
			
			if(plantillasSMSService.existeNombre(nombrePlantilla) == 0) {
				if(plantillasSMSService.existeRegistro(slcCampania,slcClasificacion) == 0) {
					Usuario usuario = obtenerUsuarioAutenticado();

					if (usuario == null) {
					    respuesta.put(Constantes.TEXTO_MNS, "Usuario no autenticado.");
					    respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
					    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
					}
					
					LocalDate fecha = LocalDate.now();
				
					PlantillasSMS plantillasSMS = new PlantillasSMS();
					
					plantillasSMS.setVc_nombre_plantilla(nombrePlantilla);
					Bases base = basesService.findById(slcCampania).get();
					plantillasSMS.setFK_ID_BASE(base);
					ClasificacionBases clasBase = clasificacionBasesService.findById(slcClasificacion).get();
					plantillasSMS.setFK_ID_CLASIFICACION(clasBase);
					plantillasSMS.setVc_contenido(txtContenido);
					plantillasSMS.setI_activo(slcEstatus);
					plantillasSMS.setDt_fecha_registro(fecha);
					plantillasSMS.setI_usuario_registro(usuario);
				
					plantillasSMSService.save(plantillasSMS);
				}else {
					mensaje = Constantes.PLANTILLA_DUPLICADA;
					tipo = Constantes.MENSAJE_ERROR;
				}
				
			}else {
				mensaje = Constantes.NOMBRE_PLANTILLA_DUPLICADA;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,"Error interno al procesar la solicitud.");
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	
	@GetMapping("/obtPlantilla")
	public ResponseEntity<?> cargarPlantilla(@RequestParam(name = "idPlantilla") Integer idPlantilla) {
	    try {
	        Optional<PlantillasSMS> plantilla = plantillasSMSService.findById(idPlantilla);
	        if (plantilla.isPresent()) {
	            return ResponseEntity.ok(plantilla.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        logger.error("Error al cargar la plantilla con ID " + idPlantilla + ": " + e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error al cargar la plantilla con ID " + idPlantilla + ": ");
	    }
	}
	
	@PostMapping("/modPlantilla")
	public ResponseEntity<?> modPlantilla(
				@RequestParam(name = "idPlantilla") Integer idPlantilla, 
				@RequestParam(name = "nombrePlantilla") String nombrePlantilla, 
				@RequestParam(name = "slcCampania") Integer slcCampania,
				@RequestParam(name = "slcClasificacion") Integer slcClasificacion,
				@RequestParam(name = "txtContenido") String txtContenido,
				@RequestParam(name = "slcEstatus") Integer slcEstatus
				){
			logger.info("/modPlantilla"+idPlantilla);
			String mensaje = Constantes.ACTUALIZACION_OK;
			String tipo =Constantes.MENSAJE_OK;
			Map<String, Object> respuesta = new HashMap<>();
			
			try {
				if(plantillasSMSService.existeNombreMod(nombrePlantilla,idPlantilla) == 0) {
					if(plantillasSMSService.existeRegistroMod(slcCampania,slcClasificacion,idPlantilla) == 0) {
						Usuario usuario = obtenerUsuarioAutenticado();

						if (usuario == null) {
						    respuesta.put(Constantes.TEXTO_MNS, "Usuario no autenticado.");
						    respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
						    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
						}	
						LocalDate fecha = LocalDate.now();
						Optional<PlantillasSMS> plantillasSMS = plantillasSMSService.findById(idPlantilla);
					
						plantillasSMS.get().setPk_id_plantilla(idPlantilla);
						plantillasSMS.get().setVc_nombre_plantilla(nombrePlantilla);
					
						Bases base = basesService.findById(slcCampania).get();
						plantillasSMS.get().setFK_ID_BASE(base);
						
						ClasificacionBases clasBase = clasificacionBasesService.findById(slcClasificacion).get();
						plantillasSMS.get().setFK_ID_CLASIFICACION(clasBase);
						plantillasSMS.get().setVc_contenido(txtContenido);
						plantillasSMS.get().setI_activo(slcEstatus);
						plantillasSMS.get().setDt_fecha_mod(fecha);
						plantillasSMS.get().setI_usuario_mod(usuario);

						plantillasSMSService.save(plantillasSMS.get());
						
					}else {
						mensaje = Constantes.PLANTILLA_DUPLICADA;
						tipo = Constantes.MENSAJE_ERROR;
					}
					
				}else {
					mensaje = Constantes.NOMBRE_PLANTILLA_DUPLICADA;
					tipo = Constantes.MENSAJE_ERROR;
				}
				
				respuesta.put(Constantes.TEXTO_MNS,mensaje);
				respuesta.put(Constantes.TIPO_MNS,tipo);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
				respuesta.put(Constantes.TEXTO_MNS,"Error interno al procesar la solicitud.");
				respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
				return ResponseEntity.ok(respuesta);
			}
		}
	
	
}
