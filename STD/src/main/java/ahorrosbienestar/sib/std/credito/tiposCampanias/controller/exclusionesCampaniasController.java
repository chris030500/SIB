package ahorrosbienestar.sib.std.credito.tiposCampanias.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclisionesClientesModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclusionMunicipioModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.HisTiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.repository.ExclusionClienteRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.ExclusionClienteService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.ExclusionMunicipioService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.HisTiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/credito")
public class exclusionesCampaniasController {

    @Autowired
    private ExclusionMunicipioService exclusionMunicipioService;
    
    @Autowired
    private ExclusionClienteService exclusionClienteService;
    
    @Autowired
	private IUsuarioRepository iUsuarioRepository;
    
    @Autowired
    private ExclusionClienteRepository exclusionClienteRepository;
    
    @Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    
    @Autowired
	private OpcionesMenuService opcionesMenuService;
    
    @Autowired
	private HisTiposCampaniasService serviceHis;
    
    List<Usuario> usuario = null;

    @GetMapping("/exclusiones")
    public String mostrarExclusiones(Model model) {
        //System.out.print("Si entra");
        //return "credito/exclusiones"; // Renderiza la vista Thymeleaf
        try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CAMPANIAS_EXCLUSIONES) == 1) {
				
				/////
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				UserDetails userDetails = (UserDetails) principal;
				usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				Integer idUsuario = usuario.get(0).getPk_id_usuario();
				//System.out.print("idUsuario:" + idUsuario);
				model.addAttribute("idUsuario", idUsuario);
				
				// Obtener la fecha actual sin tiempo
		        LocalDate fechaActual = LocalDate.now();
		        model.addAttribute("idFecha", fechaActual);
				
				////
				
				rutaDireccion = Constantes.CAMPANIAS_EXCLUSIONES;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
    }

    @PostMapping("/cargarMunicipios")
    public ResponseEntity<String> cargarExcel(@RequestParam("archivo") MultipartFile archivo) {
        try {
            exclusionMunicipioService.procesarExcel(archivo);
            return ResponseEntity.ok("Archivo procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/listarMunicipios")
    @ResponseBody
    public List<ExclusionMunicipioModel> listarMunicipios() {
        return exclusionMunicipioService.obtenerTodos();
    }
    
    @GetMapping("/lista_clientes")
	public ResponseEntity<List<ClientesActivosModel>> obtenerClientesPorEmpresaEmpleado() {
		List<ClientesActivosModel> clientes = exclusionMunicipioService.obtenerClientes();
		return ResponseEntity.ok(clientes);
	}
    
    

    @PostMapping("/guardarCliente")
    public String guardarCliente(
        @RequestParam("clienteId") Integer idCliente,
        @RequestParam("buscarEmpleado") String nombreCliente,
        @RequestParam("motivo") String motivo,
        @RequestParam("observaciones") String observaciones,
        RedirectAttributes redirectAttributes
    ) {
    	ExclisionesClientesModel model = new ExclisionesClientesModel();
        model.setIdCliente(idCliente);
        model.setNombreCliente(nombreCliente);
        model.setMotivoExclusion(motivo);
        model.setObservacionesExclusion(observaciones);
        model.setEstatusCliente(1); // ejemplo

        exclusionClienteService.guardarExclusion(model);

        redirectAttributes.addFlashAttribute("mensaje", "Cliente excluido correctamente.");
        return "redirect:/credito/exclusiones";
    }
    
    @GetMapping("/api/countClienteEliminar")
    @ResponseBody
    public int contarProcesosExtraccion(@RequestParam("idCliente") Integer idCliente) {
        return exclusionClienteRepository.existeClienteExclusion(idCliente);
    }
    
    @PostMapping("/api/actualizarExcCliente")
	public ResponseEntity<String> actualizar(@RequestBody ExclisionesClientesModel cliente) {
	    try {
	        boolean actualizado = exclusionClienteService.actualizarExcCliente(cliente);
	        if (actualizado) {
	            return ResponseEntity.ok("Actualizado correctamente");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado o sin cambios");
	        }
	    } catch (Exception e) {
	        // Log the error here to get more information about what went wrong
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar cliente: " + e.getMessage());
	    }
	}
    
    @PostMapping("/api/crearHisExcCliente")
	public ResponseEntity<String> crearHis(@RequestBody HisTiposCampaniasModel campania) {
	    try {
	        boolean creada = serviceHis.crearHisCampania(campania);
	        if (creada) {
	            return ResponseEntity.ok("Campaña creada correctamente");
	        } else {
	        	
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el historico de exclusion cliente");
	        }
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear el historico de exclusion cliente: " + e.getMessage());
	    }
	}
    
    public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				//logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            //logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}

}
