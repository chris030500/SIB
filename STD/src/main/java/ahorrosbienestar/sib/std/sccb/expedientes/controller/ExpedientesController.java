package ahorrosbienestar.sib.std.sccb.expedientes.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.conf.controller.ParametrosConfigController;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.sccb.expedientes.model.ExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.expedientes.model.ExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.expedientes.model.ImagenesExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.expedientes.service.ExpedienteAfiliacionService;
import ahorrosbienestar.sib.std.sccb.expedientes.service.ExpedienteCreditoService;
import ahorrosbienestar.sib.std.sccb.expedientes.service.ImagenesExpedienteAfiliacionService;
import ahorrosbienestar.sib.std.sccb.expedientes.service.ImagenesExpedienteCreditoService;
import ahorrosbienestar.sib.std.sccb.expedientes.service.SFTPService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("sccb")
public class ExpedientesController {
	
	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ExpedienteCreditoService expedienteCreditoService; 
	
	@Autowired
	private ExpedienteAfiliacionService expedienteAfiliacionService;
	
	@Autowired
	private ImagenesExpedienteCreditoService imagenesExpCreditoService;
	
	@Autowired
	private ImagenesExpedienteAfiliacionService imagenesExpAfiliacionService;
	
	@Autowired
	private SFTPService sftpService;
	
	@GetMapping("/expedientes")
	public String paramsConfiguracion(Model model) throws IOException {
		
		String rutaDireccion = "";
		
		try {
					
			if (validarAcceso() == 1) {
				rutaDireccion = Constantes.SCCB_CONSULTA_EXPEDIENTES;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/getExpedienteCreditoByIdCredito" }, method = RequestMethod.GET)
	public ResponseEntity<?> getExpedienteCreditoById(@RequestParam("idCredito") Integer idCredito) {
		
		String mensajeError = null;
		
		try {
			
			Optional<ExpedienteCredito> expedienteCredito = expedienteCreditoService.searchExpedienteCreditoByIdCredito(idCredito);
			
			if (expedienteCredito.isPresent()) {
				return ResponseEntity.ok(expedienteCredito);
			}
			else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
			
		}
	}
	
	
	@RequestMapping(value = { "/getNamesExpedienteCredito" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroExpCreditoByName(@RequestParam("nombre") String nombre) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> nombresCredito = expedienteCreditoService.searcNamesExpedienteCredito(nombre);
			
			if (nombresCredito != null && !nombresCredito.isEmpty()) {
				res.put("nombresCredito", nombresCredito);
				return ResponseEntity.ok(res);
			} else {
				res.put("nombresCredito", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getNamesExpedienteAfiliacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroExpAfiliacionByName(@RequestParam("nombre") String nombre) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> nombresAfiliacion = expedienteAfiliacionService.searchByNameExpedienteAfiliacion(nombre);
			
			if (nombresAfiliacion != null && !nombresAfiliacion.isEmpty()) {
				res.put("nombresAfiliacion", nombresAfiliacion);
				return ResponseEntity.ok(res);
			} else {
				res.put("nombresAfiliacion", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getExpedienteAfiliacionByName" }, method = RequestMethod.GET)
	public ResponseEntity<?> getExpedienteAfiliacionByName(@RequestParam("rowGuid") Integer rowGuid) {	
		
		String mensajeError = null;
		
		try {
			
			Optional<ExpedienteAfiliacion> expedienteAfiliacion = expedienteAfiliacionService.searchExpedienteAfiliacionByNomCliente(rowGuid);
			
			if (expedienteAfiliacion.isPresent()) {
				return ResponseEntity.ok(expedienteAfiliacion);
			}
			else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
		}
		
	}
	
	
	@RequestMapping(value = { "/getDetalleExpedienteCredito" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroIdExpedienteCredito(@RequestParam("idCredito") Integer idCredito) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		Optional<ExpedienteCredito> expedienteCredito = expedienteCreditoService.searchExpedienteCreditoByIdCredito(idCredito);
		int idExpediente = expedienteCredito.get().getI_expediente_id();
		
		List<Map<String, Object>> imagenesCredito = imagenesExpCreditoService.searchDetalleCredito(idExpediente);
		
		try {
			if (imagenesCredito != null && !imagenesCredito.isEmpty()) {
				res.put("imagenesCredito", imagenesCredito);
				return ResponseEntity.ok(res);
			} else {
				res.put("imagenesCredito", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getDetalleExpedienteAfiliacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroIdExpedienteAfiliacion(@RequestParam("rowGuid") Integer rowGuid) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		Optional<ExpedienteAfiliacion> expedienteAfiliacion = expedienteAfiliacionService.searchExpedienteAfiliacionByNomCliente(rowGuid);
		int idExpediente = expedienteAfiliacion.get().getI_expediente_id();
		
		List<ImagenesExpedienteAfiliacion> imagenesAfiliacion = imagenesExpAfiliacionService.searchDetalleExpdienteAfiliacion(idExpediente);
		
		try {
			if (imagenesAfiliacion != null && !imagenesAfiliacion.isEmpty()) {
				res.put("imagenesAfiliacion", imagenesAfiliacion);
				return ResponseEntity.ok(res);
			} else {
				res.put("imagenesAfiliacion", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
    @GetMapping("/getImage")
    public ResponseEntity<byte[]> showImageFiles(@RequestParam("pathImage") String pathImage) {
    	
    	String newPathImage = pathImage.replace("\\", "/");
    	String baseRemoteDirectory = "/Imagenes_SCCB_2024";
    	String[] directories = {"Imagenes_SCCB", "Imagenes_SCCB_2"};
    	
        try {
        	
        	byte[] fileContent = sftpService.findImageFiles(baseRemoteDirectory, directories, newPathImage);
        	
            if (fileContent != null) {
            	
            	return ResponseEntity.ok()
            			.contentType(MediaType.IMAGE_JPEG)
            			.body(fileContent);
            } else {
            	
            	 return ResponseEntity.notFound().build();
            }
        	
        	
        } catch (Exception e) {
        	 return ResponseEntity.notFound().build(); 
        }
        
    }
	
	public int validarAcceso() {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
					userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.SCCB_CONSULTA_EXPEDIENTES));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}
			
			
	}

}
