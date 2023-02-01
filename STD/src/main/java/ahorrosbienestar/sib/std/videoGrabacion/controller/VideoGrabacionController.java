package ahorrosbienestar.sib.std.videoGrabacion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.repository.ISolicitudEnvio;
import ahorrosbienestar.sib.std.videoGrabacion.model.VideoGrabacionDatos;
import ahorrosbienestar.sib.std.videoGrabacion.service.VideoGrabacionService;

@Controller
@RequestMapping("/videoGrabacion")
public class VideoGrabacionController {
	

	@Autowired
	private VideoGrabacionService videoGrabacionService;
		
	@Autowired
	private ISolicitudEnvio iSolicitudEnvio;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	
	@Autowired
	public VideoGrabacionController(VideoGrabacionService videoGrabacionService) {
		this.videoGrabacionService = videoGrabacionService;
	}

	@GetMapping("/consulta_documentos")
	public String  InicioVideograbacion(Model model) {
		
		try {
			int idGroup=1;
			model.addAttribute("opcionesBusqueda",iCatalogoRepository.generarOpciones(idGroup));
			
			return "videoGrabacion/videograbacionConsulta";
		} catch (Exception e) {
			System.out.print("mensaje"+e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/{solicitud}")
	public  ResponseEntity<?> ObtenerDatos(@PathVariable (name ="solicitud") Integer solicitud) {
		try {
			
			List<ValidaSolicitudDto> solicitudDatos = coreBancarioService.validaSolicitud(solicitud);
			List<SolicitudEnvio> busquedaSolicitud = iSolicitudEnvio.findBySolicitud(solicitud);
			
			ArrayList<VideoGrabacionDatos> listaDatos = new ArrayList<>();
			for(SolicitudEnvio dato :busquedaSolicitud) {
				VideoGrabacionDatos datos =new  VideoGrabacionDatos();
				
				datos.setPk_id_solicitud_envio(dato.getPk_id_solicitud_envio());
				datos.setClienteID(solicitudDatos.get(0).getClienteid());
				datos.setSolicitudID(dato.getI_no_solicitud_core());
				datos.setEstatus(""+dato.getI_estatus());
				datos.setProducto(solicitudDatos.get(0).getProductoCreditoID()+"--"+solicitudDatos.get(0).getProducto());
				datos.setFechaRegistro(""+dato.getDt_fecha_registro());
				datos.setFechaAutorizacion(""+dato.getDt_fecha_autorizacion());
				datos.setFechaCancelacion(""+dato.getDt_fecha_cancelacion());
				datos.setFechaEnvio(""+dato.getDt_fecha_envio());
				
				listaDatos.add(datos);
			}
			return  ResponseEntity.ok(listaDatos);
						
		} catch (Exception e) {
			System.out.print("--"+e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/{fechaInicio}/{fechaFin}")
	public  ResponseEntity<?> ObtenerDatos(@PathVariable (name ="fechaInicio") String fechaInicio,@PathVariable (name ="fechaFin") String fechaFin) {
		try {
			
			List<SolicitudEnvio> busquedaSolicitud = iSolicitudEnvio.findByRangoFechas(fechaInicio, fechaFin);	
			
			ArrayList<VideoGrabacionDatos> listaDatos = new ArrayList<>();
			for(SolicitudEnvio dato :busquedaSolicitud) {
				List<ValidaSolicitudDto> solicitudDatos = coreBancarioService.validaSolicitud(dato.getI_no_solicitud_core());
				VideoGrabacionDatos datos =new  VideoGrabacionDatos();
				
				for(ValidaSolicitudDto datosSolicitud :solicitudDatos) {
					datos.setClienteID(datosSolicitud.getClienteid());
					datos.setProducto(datosSolicitud.getProductoCreditoID()+" "+datosSolicitud.getProducto());
				}
				
				datos.setPk_id_solicitud_envio(dato.getPk_id_solicitud_envio());
				datos.setSolicitudID(dato.getI_no_solicitud_core());
				datos.setEstatus(""+dato.getI_estatus());
				datos.setFechaRegistro(""+dato.getDt_fecha_registro());
				datos.setFechaAutorizacion(""+dato.getDt_fecha_autorizacion());
				datos.setFechaCancelacion(""+dato.getDt_fecha_cancelacion());
				datos.setFechaEnvio(""+dato.getDt_fecha_envio());
				
				
				listaDatos.add(datos);
			}	
			return  ResponseEntity.ok(listaDatos);
						
		} catch (Exception e) {
			System.out.print("--"+e.getMessage());
		}
		return null;
	}	
}
