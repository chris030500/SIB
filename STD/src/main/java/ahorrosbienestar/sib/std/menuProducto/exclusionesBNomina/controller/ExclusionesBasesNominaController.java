package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.CatPuestosOcupacion;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.ConveniosNomina;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.ExclusionSucursales;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.MunicipiosRepub;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.CatPuestosOcupacionService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.ConveniosNominaService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.EstadosRepubService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.ExclusionSucursalesService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.MunicipiosRepubService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;


@Controller
@RequestMapping("exclusiones")
public class ExclusionesBasesNominaController {
	
	private static final Logger logger = Logger.getLogger(ExclusionesBasesNominaController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private EstadosRepubService estadosRepubService;
	
	@Autowired
	private MunicipiosRepubService municipiosRepubService;
	
	@Autowired
	private ConveniosNominaService conveniosNominaService;
	
	@Autowired
	private CatPuestosOcupacionService puestosOcupacionService;
	
	@Autowired
	private ExclusionSucursalesService sucursalesService;
	
	private Integer obtenerIdUsuarioAutenticado() {

	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (!(principal instanceof UserDetails)) {
	        return null;
	    }

	    UserDetails userDetails = (UserDetails) principal;
	    List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

	    if (usuario == null || usuario.isEmpty()) {
	        return null;
	    }

	    return usuario.get(0).getPk_id_usuario();
	}
	
	List<Usuario> usuario = null;
	
	@RequestMapping(value = { "/getEstados" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getEstadosRepub() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> estados = estadosRepubService.getAllEstadosRepub();
		
		try {
			
			if (estados != null && !estados.isEmpty()) {
				
				res.put("estados", estados);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("estados", Collections.emptyList());
				
				return ResponseEntity.ok(res);
				
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getMunicipios" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMunicipiosRepub(@RequestParam("idEstado") Integer idEstado) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> municipios = municipiosRepubService.getMunicipiosRepubById(idEstado);
		
		try {
			
			if (municipios != null && !municipios.isEmpty()) {
				
				res.put("municipios", municipios);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("municipios", Collections.emptyList());
				
				return ResponseEntity.ok(res);
				
			}
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getMunicipiosConCobertura" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMunicipiosConCobertura() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> municipios = municipiosRepubService.getMunicipiosConCobertura();
		
		try {
			
			if (municipios != null && !municipios.isEmpty()) {

				res.put("municipios", municipios);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("municipios", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
			
		}
	}
	
	@RequestMapping(value = "/actualizarIndicadorMunicipios", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> actualizarIndicadorMunicipios(
			@RequestParam("idEstado") Integer idEstado,
			@RequestParam("idMunicipio") Integer idMunicipio,
			@RequestParam("indicadorDepurar") Integer indicadorDepurar) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Integer idUsuario = obtenerIdUsuarioAutenticado();

			if (idUsuario == null) {
			    response.put(Constantes.MENSAJE_ERROR, "Usuario no autenticado o no encontrado.");
			    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			MunicipiosRepub updatedMunicipio = municipiosRepubService.actualizarIndicadorMunicipiosRepub(idEstado, idMunicipio, indicadorDepurar, idUsuario);
			
			if (updatedMunicipio != null) {
				
				response.put("municipio", updatedMunicipio);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
	        response.put(Constantes.MENSAJE_DE_ERROR, e.getMessage());
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
	@RequestMapping(value = { "/getConveniosNominaActivos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getConveniosNominaActivos(@RequestParam("descConvenio") String descConvenio) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> conveniosActivos = conveniosNominaService.getConveniosNominaActivos(descConvenio);
		
		try {
			
			if (conveniosActivos != null && !conveniosActivos.isEmpty()) {
				
				res.put("conveniosActivos", conveniosActivos);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("conveniosActivos", Collections.emptyList());
				
				return ResponseEntity.ok(res);
				
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
			
		}
	}
	
	@RequestMapping(value = { "/getConveniosNominaDepurar" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getConveniosNominaDepurar(@RequestParam("descConvenio") String descConvenio) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> conveniosDepurar = conveniosNominaService.getConveniosNominaDepurar(descConvenio);
		
		try {
			
			if (conveniosDepurar != null && !conveniosDepurar.isEmpty()) {
				
				res.put("conveniosDepurar", conveniosDepurar);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("conveniosDepurar", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
			
		}
	}
	
	@RequestMapping(value = "/actualizarIndicadorConveniosNomina", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> actualizarIndicadorConveniosNomina(@RequestParam("idConvenio") Integer idConvenio,
			@RequestParam("indicadorDepurar") Integer indicadorDepurar) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Integer idUsuario = obtenerIdUsuarioAutenticado();

			if (idUsuario == null) {
			    response.put(Constantes.MENSAJE_ERROR, "Usuario no autenticado o no encontrado.");
			    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			ConveniosNomina updatedConvenio = conveniosNominaService.actualizarIndicadorConveniosNomina(idConvenio, indicadorDepurar, idUsuario);
		
			if (updatedConvenio != null) {
				
				response.put("convenio", updatedConvenio);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
			}
			
		} catch(Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
	        response.put(Constantes.MENSAJE_DE_ERROR, e.getMessage());
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = { "/getPuestosOcupacionActivos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getPuestosOcupacionActivos() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> puestosActivos = puestosOcupacionService.getPuestosOcupacionActivos();
		
		try {
			
			if (puestosActivos != null && !puestosActivos.isEmpty()) {
				
				res.put("puestosActivos", puestosActivos);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("puestosActivos", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getPuestosOcupacionDepurar" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getPuestosOcupacionDepurar() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> puestosDepurar = puestosOcupacionService.getPuestosOcupacionDepurar();
		
		try {
			
			if (puestosDepurar != null && !puestosDepurar.isEmpty()) {
				
				res.put("puestosDepurar", puestosDepurar);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("puestosDepurar", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = "/actualizarIndicadorPuestos", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> actualizarIndicadorPuestos(@RequestParam("idPuesto") Integer idPuesto,
			@RequestParam("indicadorDepurar") Integer indicadorDepurar) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Integer idUsuario = obtenerIdUsuarioAutenticado();

			if (idUsuario == null) {
			    response.put(Constantes.MENSAJE_ERROR, "Usuario no autenticado o no encontrado.");
			    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			CatPuestosOcupacion updatedPuesto = puestosOcupacionService.actualizarIndicadorPuestosOcupacion(idPuesto, indicadorDepurar, idUsuario);
		
			if (updatedPuesto != null) {
				
				response.put("puesto", updatedPuesto);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
	        response.put(Constantes.MENSAJE_DE_ERROR, e.getMessage());
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = { "/getSucursalesActivos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSucursalesActivos() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> sucursalesActivos = sucursalesService.getSucursalesActivos();
		
		try {
			if (sucursalesActivos != null && !sucursalesActivos.isEmpty()) {
				
				res.put("sucursalesActivos", sucursalesActivos);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("sucursalesActivos", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getSucursalesDepurar" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSucursalesDepurar() {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> sucursalesDepurar = sucursalesService.getSucursalesDepurar();
		
		try {
			
			if (sucursalesDepurar != null && !sucursalesDepurar.isEmpty()) {
				
				res.put("sucursalesDepurar", sucursalesDepurar);
				
				return ResponseEntity.ok(res);
				
			} else {
				
				res.put("sucursalesDepurar", Collections.emptyList());
				
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = "/actualizarIndicadorSucursales", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> actualizarIndicadorSucursales(@RequestParam("idSucursal") Integer idSucursal,
			@RequestParam("indicadorDepurar") Integer indicadorDepurar) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Integer idUsuario = obtenerIdUsuarioAutenticado();

			if (idUsuario == null) {
			    response.put(Constantes.MENSAJE_ERROR, "Usuario no autenticado o no encontrado.");
			    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			ExclusionSucursales updatedSucursal = sucursalesService.actualizarIndicadorSucursal(idSucursal, indicadorDepurar, idUsuario);
		
			if (updatedSucursal != null) {
				
				response.put("sucursal", updatedSucursal);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
	        response.put(Constantes.MENSAJE_DE_ERROR, e.getMessage());
	        
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
