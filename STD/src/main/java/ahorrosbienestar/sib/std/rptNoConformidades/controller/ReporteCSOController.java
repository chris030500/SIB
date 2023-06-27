package ahorrosbienestar.sib.std.rptNoConformidades.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import ahorrosbienestar.sib.std.rptNoConformidades.model.BusquedaCSO;
import ahorrosbienestar.sib.std.rptNoConformidades.repository.IReporteCSORepository;
import ahorrosbienestar.sib.std.rptNoConformidades.service.ReporteCSOService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("reporteFolio")
public class ReporteCSOController {

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IReporteCSORepository iReporteCSORepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ReporteCSOService reporteCSOService;
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	
	@Autowired
	public ReporteCSOController(ReporteCSOService reporteCSOService) {
		this.reporteCSOService = reporteCSOService;
	} 
	
	@GetMapping("/reporteCSO")
	public String inicioFolio(Model model) {
		try {
			model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
			model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			return "reporteFolio/reporteCSO";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
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
			,@RequestParam(name = "iTipo") Integer iTipo){
		
		String mensaje= "Registro Correctamente";
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
			
			return ResponseEntity.ok(iReporteCSORepository.save(altaBusqueda));
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
}
