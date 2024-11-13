package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RptOperacionesInusualesController {
	
	private static final Logger logger = Logger.getLogger(RptOperacionesInusualesController.class);
	
	@RequestMapping(value = { "/reporteOperacionesInusuales" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteOperacionesInusuales(@RequestParam("idReporte") Integer idReporte,
			@RequestParam("dtFechaInicio") String dtFechaInicio, @RequestParam("dtFechaFin") String dtFechaFin,
			@RequestParam("nombreReporteSelect") Integer nombreReporteSelect,
			@RequestParam("extension") Integer extension,
			@RequestParam("noCliente") Integer noCliente) {

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			LocalDate fecha = LocalDate.now();

			if (nombreReporteSelect == 1) {
				
				if (noCliente > 0) {
					
					nombreReporte = "rpt_historico_operaciones_inusuales_clientes";
					reporteCliente = "~NoCliente="+noCliente;
					
				}else {
					
					nombreReporte = "rpt_historico_operaciones_inusuales";
				}

			} else if (nombreReporteSelect == 2) {

				if (noCliente > 0) {
					
					nombreReporte = "rpt_historico_operaciones_relevantes_clientes";
					reporteCliente = "~NoCliente="+noCliente;
					
				}else {
					
					nombreReporte = "rpt_historico_operaciones_relevantes";
				}
			}

			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);

			if (extension == 2) {

				extensionReporte = ".pdf";

			} else if (extension == 1) {

				extensionReporte = ".xls";
			}
			
			//agregar un caso para ver si trae algo en noCliente

			// String reporteURL =
			// iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?FechaInicio="
			// +dtFechaInicio+"~FechaFin="+dtFechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OA";

			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
					+ dtFechaFin + reporteCliente + "&tipoDocumento=" + extensionReporte + "&identificadorDoc=OA";

			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}

}
