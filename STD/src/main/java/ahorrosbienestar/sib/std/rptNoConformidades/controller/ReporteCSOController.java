package ahorrosbienestar.sib.std.rptNoConformidades.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.nomina.configuracion.NominaTareaConfiguracion;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.rptNoConformidades.model.BusquedaCSO;
import ahorrosbienestar.sib.std.rptNoConformidades.repository.IReporteCSORepository;
import ahorrosbienestar.sib.std.rptNoConformidades.service.ReporteCSOService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("reporteFolio")
public class ReporteCSOController {

	private static final Logger logger = Logger.getLogger(ReporteCSOController.class);
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IReporteCSORepository iReporteCSORepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ReporteCSOService reporteCSOService;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired IReporteRepository iReporteRepository;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	
	private static final String nombreReporte = "Reporte_CSO";
	private static final String controllerReporte = "generarReporte";
	private static final String nombrePantalla = "Reportes";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	@Autowired
	public ReporteCSOController(ReporteCSOService reporteCSOService) {
		this.reporteCSOService = reporteCSOService;
	} 
	
	@GetMapping("/reporteCSO")
	public String inicioFolio(Model model) {
		try {
		
			if(validarAcceso() == 1) {
				model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
				model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
				model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
				model.addAttribute("cat_lista_oficios", catalogosCEService.obtFolios());
				rutaDireccion = "reporteFolio/reporteCSO";
			}else {
				rutaDireccion = rutaError;
			}

		return rutaDireccion;
		} catch (Exception e) {
			logger.error(""+e.getMessage());
		}
		return null;
	}
	
	@PostMapping("/altaBusqueda")
	public ResponseEntity<?> altaBusqueda(
			@RequestParam(name = "iTodos") Integer iTodos
			,@RequestParam(name = "dtFechaInicio") String dtFechaInicio
			,@RequestParam(name = "dtFechaFin") String dtFechaFin
			,@RequestParam(name = "iEstatus") Integer iEstatus
			,@RequestParam(name = "iEmite") Integer iEmite
			,@RequestParam(name = "iTipo") Integer iTipo
			,@RequestParam(name = "idOficio") Integer idOficio){
		
		
		ArrayList<String> respuesta = new ArrayList<String>();
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			BusquedaCSO altaBusqueda = new BusquedaCSO();
			LocalDate fecha = LocalDate.now();
			
			altaBusqueda.setI_todos(iTodos);
			altaBusqueda.setDt_fecha_inicio(LocalDate.parse(dtFechaInicio));
			altaBusqueda.setDt_fecha_fin(LocalDate.parse(dtFechaFin));
			altaBusqueda.setDt_fecha_registro(fecha);
			altaBusqueda.setI_estatus(iEstatus);
			altaBusqueda.setI_organo_emite(iEmite);
			altaBusqueda.setI_tipo_oficio(iTipo);
			altaBusqueda.setI_usuario_registro(usuario.get(0).getPk_id_usuario());
			altaBusqueda.setI_folio(idOficio);
			
			iReporteCSORepository.save(altaBusqueda);
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?ID_BUSQUEDA="
			+altaBusqueda.getPk_id_busqueda()+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc="+altaBusqueda.getDt_fecha_registro();
			
			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
	
	public int validarAcceso() {
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		
		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(nombrePantalla));
		
		
	}
}
