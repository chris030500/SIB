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
import ahorrosbienestar.sib.std.core_bancario.dto.ProductosDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.proceso_validacion.model.ProcesoValidacion;
import ahorrosbienestar.sib.std.proceso_validacion.repository.IProcesoValidacionRepository;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.repository.ISolicitudEnvio;
import ahorrosbienestar.sib.std.videoGrabacion.model.VideoGrabacionDatos;
import ahorrosbienestar.sib.std.videoGrabacion.service.VideoGrabacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private IProcesoValidacionRepository iProcesoValidacionRepository;
	
	private static final Logger logger =
	        LoggerFactory.getLogger(VideoGrabacionController.class);
	
	
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
			//logger.info("mensaje"+e.getMessage());
			logger.error("Ocurrió un error durante el proceso", e);
			//System.out.print("mensaje"+e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/{solicitud}")
	public  ResponseEntity<?> ObtenerDatos(@PathVariable (name ="solicitud") Integer solicitud) {
		try {
			List<SolicitudEnvio> busquedaSolicitud = iSolicitudEnvio.findBySolicitud(solicitud);
						
			return  ResponseEntity.ok(obtLista(busquedaSolicitud));
						
		} catch (Exception e) {
			//System.out.print("--"+e.getMessage());
			logger.error("Ocurrió un error durante el proceso", e);
		}
		return null;
	}
	
	@GetMapping("/{fechaInicio}/{fechaFin}")
	public  ResponseEntity<?> ObtenerDatos(@PathVariable (name ="fechaInicio") String fechaInicio,@PathVariable (name ="fechaFin") String fechaFin) {
		try {
			
			List<SolicitudEnvio> busquedaSolicitud = iSolicitudEnvio.findByRangoFechas(fechaInicio, fechaFin);	
			return  ResponseEntity.ok(obtLista(busquedaSolicitud));
						
		} catch (Exception e) {
			//System.out.print("--"+e.getMessage());
			logger.error("Ocurrió un error durante el proceso", e);
		}
		return null;
	}
	
	public String buscarEstatus(int idEstatus) {
		String descripcionEstatus="";
		
		List<ProcesoValidacion> listaEstatus =  iProcesoValidacionRepository.findAll();
	    for (ProcesoValidacion ProcesoValidacion : listaEstatus) { 
	         if(ProcesoValidacion.getPk_id_proc_validacion() == idEstatus) {
	        	 descripcionEstatus = ProcesoValidacion.getVc_descripcion();
	        	 break;
	         } 
	    }
		return descripcionEstatus; 
		
	}
	
	public ArrayList<VideoGrabacionDatos> obtLista(List<SolicitudEnvio> busquedaSolicitud){
		int colocacion = 1;
		int captacion = 2;
		List<ProductosDto> solicitudDatos = new ArrayList<ProductosDto>();
		ArrayList<VideoGrabacionDatos> listaDatos = new ArrayList<>();
		for(SolicitudEnvio dato :busquedaSolicitud) {
			VideoGrabacionDatos datos =new  VideoGrabacionDatos();
			
			
			 solicitudDatos = coreBancarioService.listaProductos(dato.getI_producto_id(),colocacion);
			 if(solicitudDatos.get(0).getP_no_producto() == 0) {
				 solicitudDatos = coreBancarioService.listaProductos(dato.getI_producto_id(),captacion);
				 datos.setProducto(solicitudDatos.get(0).getNombre_producto());
			 }else {
				 datos.setProducto(solicitudDatos.get(0).getNombre_producto());
			 }
			 
			
			//for(ValidaSolicitudDto datosSolicitud :solicitudDatos) {
				//datos.setClienteID(datosSolicitud.getClienteid());
				//datos.setProducto(datosSolicitud.getProductoCreditoID()+" "+datosSolicitud.getProducto());
			//}
		
			datos.setPk_id_solicitud_envio(dato.getPk_id_solicitud_envio());
			datos.setSolicitudID(dato.getI_no_solicitud_core());
			datos.setEstatus(buscarEstatus(dato.getI_estatus()));
			datos.setProductoid(dato.getI_producto_id());
			datos.setFechaRegistro(dato.getDt_fecha_registro()==null?"":dato.getDt_fecha_registro().toString());
			datos.setFechaAutorizacion(dato.getDt_fecha_autorizacion()==null?"":dato.getDt_fecha_autorizacion().toString());
			datos.setFechaCancelacion(dato.getDt_fecha_cancelacion()==null?"":dato.getDt_fecha_cancelacion().toString());
			datos.setFechaEnvio(dato.getDt_fecha_envio()==null?"":dato.getDt_fecha_envio().toString());
			
			
			listaDatos.add(datos);
		}
		
		return listaDatos;	
	}
}
