package ahorrosbienestar.sib.std.credito.paramburocredito.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.paramburocredito.dto.ParametrosBCDTO;
import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCreditoModel;
import ahorrosbienestar.sib.std.credito.paramburocredito.service.ParamBuroCreditoService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class ParamBuroCreditoController {
		
	private static final Logger logger = Logger.getLogger(ParamBuroCreditoController.class);
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;

	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	
	@Autowired
	private ParamBuroCreditoService paramBuroCreditoService;
	

	List<Usuario> usuario = null;
	
	@GetMapping("/paramBuroCredito")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso(Constantes.PARAMS_BURO_CREDITO) == 1) {
				model.addAttribute("cat_riesgo_Maximo", iCatalogoRepository.generarOpciones(Constantes.CAT_TIPO_RIESGO));
				rutaDireccion = Constantes.PARAMS_BURO_CREDITO;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}	
	}
	
	
	
	public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}
	
	@GetMapping("/buscarCampaniasActivas")
	@ResponseBody
	public List<TiposCampaniasDTO> obtCampanias(@RequestParam String valorBusqueda) {
	    return tiposCampaniasService.buscarCampaniasActivas(valorBusqueda);
	}
	
	@GetMapping("/obtParamCampaniasBC")
	public ResponseEntity<ParamBuroCreditoModel> obtCampanias(@RequestParam int valorCampaniaID) {
	    ParamBuroCreditoModel parametros = paramBuroCreditoService.obtParamBuro(valorCampaniaID);

	    if (parametros == null) {
	        return ResponseEntity.noContent().build(); // Devuelve 204
	    }else {
		    return ResponseEntity.ok(parametros); // Devuelve JSON 200
	    }


	}
	
	@PostMapping("/registroParamBC")
	public ResponseEntity<String> guardarParamBC(@ModelAttribute ParametrosBCDTO paramBC) {
		
		try {
			System.out.println(""+paramBC.getIdRegistroParam());
			Optional<TiposCampaniasModel> campaniaConsulta = tiposCampaniasService.obtenerCampaniaxID(paramBC.getIdCampaniaSeleccionada());
			ParamBuroCreditoModel registroParamBC = new ParamBuroCreditoModel();
			if(paramBC.getIdRegistroParam() != null) {
				registroParamBC.setIdParametro(paramBC.getIdRegistroParam());
				registroParamBC.setUsuarioRegistra(obtUsuario());
			}
				registroParamBC.setBcsMinimo(paramBC.getBcsMinimo());
				registroParamBC.setIccMinimo(paramBC.getIccMinimo());
				registroParamBC.setMobMaximo(paramBC.getMopMaximo());
				registroParamBC.setRiesgoMaximo(paramBC.getRiesgoMaximo());
				registroParamBC.setTipoEvaluacion(paramBC.getTipoEvaluacion());
				registroParamBC.setUsoMaximo(paramBC.getUsoMaximo());
				registroParamBC.setIdCampania(campaniaConsulta.get());
				registroParamBC.setUsuarioRegistra(obtUsuario());
					
			if(paramBuroCreditoService.save(registroParamBC) !=null) {
				return ResponseEntity.ok("Buro de credito guardada correctamente");
			}else {
				return ResponseEntity.ok("Error al guardar parametros de buro de credito");
			}
			
			
		} catch (Exception e) {

			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
			
		}
		
	}
	
	public Usuario obtUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!(principal instanceof UserDetails)) {
			logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
		}
		
		UserDetails userDetails = (UserDetails) principal;
		usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		return usuario.get(0);
		
	}
	
}
