package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RptOperacionesInusualesController {
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	private static final Logger logger = Logger.getLogger(RptOperacionesInusualesController.class);
	
	@RequestMapping(value = { "/reporteOperacionesInusuales" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteOperacionesInusuales(@RequestParam("fechaInicio") String fechaInicio, 
			@RequestParam("fechaFin") String fechaFin,
			@RequestParam("noCliente") Integer noCliente,
			@RequestParam("idProducto") Integer idProducto,
			@RequestParam("isCliente") Boolean isCliente,
			@RequestParam("isProducto") Boolean isProducto) {

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_OPERACIONES_INUSUALES_X_TIPO_PRODUCTO);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + fechaInicio + "~FechaFin="
					+ fechaFin + "~NoCliente=" + noCliente + "~ProductoID=" + idProducto + "~FiltroXnoCliente=" 
					+ isCliente + "~FiltroXproductoID=" + isProducto + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=PLD";

			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}

}
