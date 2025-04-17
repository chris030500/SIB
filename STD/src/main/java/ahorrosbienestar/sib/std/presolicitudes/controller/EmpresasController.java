package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;
import ahorrosbienestar.sib.std.presolicitudes.model.Empresa;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.repository.ConfiguracionCorreoRepository;
import ahorrosbienestar.sib.std.presolicitudes.service.ConfiguracionCorreoService;
import ahorrosbienestar.sib.std.presolicitudes.service.EmpresaService;

@Controller
@RequestMapping("configuracionEmpresas")
public class EmpresasController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ConfiguracionCorreoService configuracionCorreoService;

	@Autowired
	private ConfiguracionCorreoRepository configuracionCorreoRepository;
	private static final Logger logger = Logger.getLogger(EmpresasController.class);

	@Autowired
	private TaskScheduler taskScheduler;
	
	private ScheduledFuture<?> currentScheduledTask = null;

	private String expresionCron;
	
	@PostConstruct
	public void init() {
		try {
			ConfiguracionCorreo configuracionCorreo = configuracionCorreoRepository.findFirstByOrderByPkIdConfAsc();
			actualizarExpresionCron(configuracionCorreo.getCronExpresion());
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		}
	}

	@GetMapping("/empresas")
	public String FiltradoQEQ() {
		return "presolicitudes_nomina/empresas";
	}

	@RequestMapping(value = { "/lista_empresas" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaGestiones() {
		Map<String, Object> respuesta = new HashMap<>();
		List<Empresa> listaCatalogos = new ArrayList<Empresa>();
		String mensajeError = null;
		try {
			listaCatalogos = empresaService.obtenerTodasLasEmpresas();
			logger.info("Se llenó la lista");
			respuesta.put("lista", listaCatalogos);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@PostMapping("/guardar_empresa")
	public ResponseEntity<Map<String, Object>> guardarEmpresa(@RequestBody EmpresaSIB empresa) {
		Map<String, Object> respuesta = new HashMap<>();
		try {

			// Validar si la empresa ya existe
			if (empresaService.empresaExiste(empresa.getNombre())) {
				respuesta.put("error", "La empresa ya está registrada.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			// Guardar la empresa en la BD
			EmpresaSIB empresaGuardada = empresaService.guardarEmpresaVobo(empresa);

			// Respuesta exitosa
			respuesta.put("mensaje", "Empresa guardada correctamente");
			respuesta.put("empresa", empresaGuardada);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// Imprimir el error en el log del servidor para depuración
			e.printStackTrace();

			respuesta.put("error", "No se pudo guardar la empresa: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@GetMapping("/tablaEmpresas")
	public ResponseEntity<List<EmpresaSIB>> obtenerEmpresas() {
		List<EmpresaSIB> empresas = empresaService.obtenerTodas();
		if (empresas.isEmpty()) {
			return ResponseEntity.noContent().build(); // Si no hay empresas, devolver 204 No Content
		}
		return ResponseEntity.ok(empresas); // Si hay empresas, devolverlas con un 200 OK
	}

	@PutMapping("/actualizar_empresa/{id}")
	public ResponseEntity<Map<String, Object>> actualizarEmpresa(@PathVariable int id,
			@RequestBody EmpresaSIB empresaActualizada) {

		Map<String, Object> respuesta = new HashMap<>();

		try {
			EmpresaSIB empresa = empresaService.obtenerPorId(id);
			if (empresa == null) {
				respuesta.put("error", "Empresa no encontrada.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			// Actualizar los datos
			empresa.setNombre(empresaActualizada.getNombre());
			empresa.setCorreoContacto(empresaActualizada.getCorreoContacto());
			empresa.setPersonaContacto(empresaActualizada.getPersonaContacto());
			empresa.setFrecuencia(empresaActualizada.getFrecuencia());

			// Guardar cambios
			empresaService.guardarEmpresaVobo(empresa);

			respuesta.put("mensaje", "Empresa actualizada correctamente");
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			respuesta.put("error", "Error al actualizar: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@RequestMapping(value = "/actualizar_estatus_empresa", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> actualizarEstatus(@RequestBody Map<String, Object> request) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			Integer empresaId = Integer.valueOf(request.get("id").toString());
			Integer estatus = Integer.valueOf(request.get("estatus").toString());

			// Actualizar el estatus de la empresa en la base de datos
			EmpresaSIB empresa = empresaService.obtenerPorId(empresaId);
			if (empresa != null) {
				empresa.setEstatus(estatus);
				empresaService.guardarEmpresaVobo(empresa); // Guardar los cambios
				respuesta.put("mensaje", "Estatus actualizado correctamente.");
				return ResponseEntity.ok(respuesta);
			} else {
				respuesta.put("error", "Empresa no encontrada.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
		} catch (Exception e) {
			respuesta.put("error", "Hubo un error al actualizar el estatus: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@GetMapping("/carga_configuracion-correo")
	public ResponseEntity<ConfiguracionCorreo> cargarConfiguracionCorreo() {
		// Obtener el primer registro de configuración de correo
		ConfiguracionCorreo configuracionCorreo = configuracionCorreoRepository.findFirstByOrderByPkIdConfAsc();

		if (configuracionCorreo != null) {
			return ResponseEntity.ok(configuracionCorreo);
		} else {
			return ResponseEntity.noContent().build(); // Si no hay registros, devolver 204 No Content
		}
	}

	// Endpoint para actualizar la configuración de correo
	@PutMapping("/actualizar")
	public ResponseEntity<Map<String, String>> actualizarCorreo(@RequestBody ConfiguracionCorreo configuracionCorreo) {
		Map<String, String> response = new HashMap<>();

		try {
			configuracionCorreoService.actualizarCorreo(configuracionCorreo);
			actualizarExpresionCron(configuracionCorreo.getCronExpresion());
			response.put("message", "Configuración de correo actualizada con éxito.");
			return ResponseEntity.ok(response); // ✅ Retorna un JSON válido
		} catch (Exception e) {
			response.put("message", "Hubo un error al actualizar la configuración.");
			return ResponseEntity.status(500).body(response);
		}
	}

	public void actualizarExpresionCron(String cron) {
	    try {
	        logger.info("Proceso de Envio de correos con solicitudes de nomina a empresas para Vo.Bo.");
	        expresionCron = cron;
	        
	        // Si ya hay una tarea programada, cancelarla
	        if (currentScheduledTask != null && !currentScheduledTask.isCancelled()) {
	            currentScheduledTask.cancel(false);
	            logger.info("Tarea cron anterior cancelada.");
	            System.out.print("Tarea cron anterior cancelada.");
	        }

	        // Crea un nuevo trigger con la nueva expresión cron
	        CronTrigger trigger = new CronTrigger(expresionCron, ZoneId.of("America/Mexico_City"));
	        
	        // Programa la nueva tarea y guarda la referencia de la tarea programada
	        currentScheduledTask = taskScheduler.schedule(() -> procesarCron(), trigger);
	        
	        logger.info("Se actualizó cron de Envio de correos");
	        System.out.print("Se actualizó cron de Envio de correos");

	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	    }
	}


	public void procesarCron() {
		try {
			// Metodo envio de correo y adjunto
			System.out.print("Inicia prepara correo");
			empresaService.preparCorreoAdjuntoVobo();

		} catch (Exception e) {
			logger.info(Constantes.MENSAJE_ERROR + e.getMessage());
			System.out.print("Error prepara correo: " + Constantes.MENSAJE_ERROR + e.getMessage());
		}
	}
	
	@PostMapping("/envioCorreo/{idEmpresa}")
	public ResponseEntity<Map<String, String>> envioCorreo(@PathVariable Integer idEmpresa) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        String resultado = empresaService.prepararCorreoPorEmpresaV(idEmpresa);
	        response.put("mensaje", resultado);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("mensaje", "Error interno: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


}
