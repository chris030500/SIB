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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.CatPuestosOcupacion;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.ConveniosNomina;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.MunicipiosRepub;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.CatPuestosOcupacionService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.ConveniosNominaService;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.EstadosRepubService;
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
	
    @PutMapping("/agregarMunicipioConCobertura/{idEstado}/{idMunicipio}")
    public ResponseEntity<?> actualizarIndicadorDepurar(
            @PathVariable Integer idEstado, 
            @PathVariable Integer idMunicipio,
            @RequestBody MunicipiosRepub updatedData) {
    	
        try {
        	
        	municipiosRepubService.updateIndicadorDepurar(idEstado, idMunicipio, updatedData.getI_indicador_depurar());
        	
            return ResponseEntity.ok("Actualización exitosa");
            
        } catch (RuntimeException e) {
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
	@RequestMapping(value = { "/getConveniosNominaActivos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getConveniosNominaActivos() {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> conveniosActivos = conveniosNominaService.getConveniosNominaActivos();
		
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
	public ResponseEntity<Map<String, Object>> getConveniosNominaDepurar() {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> conveniosDepurar = conveniosNominaService.getConveniosNominaDepurar();
		
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
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		Integer idUsuario = usuario.get(0).getPk_id_usuario();

		ConveniosNomina updatedConvenio = conveniosNominaService.actualizarIndicadorConveniosNomina(idConvenio, indicadorDepurar, idUsuario);
	
		if (updatedConvenio != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("convenio", updatedConvenio);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		Integer idUsuario = usuario.get(0).getPk_id_usuario();

		CatPuestosOcupacion updatedPuestos = puestosOcupacionService.actualizarIndicadorPuestosOcupacion(idPuesto, indicadorDepurar, idUsuario);
	
		if (updatedPuestos != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("puesto", updatedPuestos);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
