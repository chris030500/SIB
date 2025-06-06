package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.apache.log4j.Logger;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;



@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class RptReporteClientesOpeController  {

	private static final Logger logger = Logger.getLogger(RptReporteClientesOpeController.class);
	
    //@Autowired
    //private ReporteClientesOpeService service;
    
    @Autowired
	private IUsuarioRepository iUsuarioRepository;
    
    @Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    
    @Autowired
	private IReporteRepository iReporteRepository;
    
    @Autowired
	private IConfiguracionRepository iConfiguracionRepository;
    
    @Autowired
	private OpcionesMenuService opcionesMenuService;

    @GetMapping("/reporteClientesOpe")
 
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			String rutaDireccion;
					
			if (validarAcceso() == 1) {
				
				rutaDireccion = Constantes.PLD_REPORTE_CLIENTES_OPE;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
    @RequestMapping(value = { "/generarRptReporteClientesOpe" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteOperacionesInusuales(@RequestParam("FechaInicio") String fechaInicio, 
			@RequestParam("FechaFin") String fechaFin) {
		

		List<String> respuesta = new ArrayList<>();
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);
			
			StringBuilder reporteURL = new StringBuilder();
			reporteURL.append(iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF))
			.append(datosReporte.getPk_id_reporte())
			.append("&parametros=?FechaInicio=")
			.append(fechaInicio)
			.append("~FechaFin=")
			.append(fechaFin);

			respuesta.add(reporteURL.toString());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}
    
    //public ResponseEntity<byte[]> generarReportePDF() {
        //try {
            //byte[] pdfBytes = service.generarReporteClientesOperacionesAuditor();

            //return ResponseEntity.ok()
              //      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_clientes_operaciones.pdf")
            //        .contentType(MediaType.APPLICATION_PDF)
          //          .body(pdfBytes);
        //} catch (Exception e) {
          //  e.printStackTrace();
        //    return ResponseEntity.status(500).body(null);
      //  }
    //}
    private int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			return iRelRolOpcionesRepository.validarAcceso(
				usuarios.get(0).getRol_idRol().getPk_id_rol(),
				opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_CLIENTES_OPE)
			);
		} catch (Exception e) {
			logger.error("Error en validación de acceso: " + e.getMessage());
			return 0;
		}
	}
}
