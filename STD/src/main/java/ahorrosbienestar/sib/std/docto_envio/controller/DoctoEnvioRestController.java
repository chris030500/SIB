package ahorrosbienestar.sib.std.docto_envio.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.carga_documentos.service.CargaDocumentosService;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.core_bancario.dto.ObtenerDoctoAsociadoProdDto;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.docto_envio.service.DoctoEnvioJDBCService;
import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.documentos.service.DocumentoService;
import ahorrosbienestar.sib.std.fichero.controller.obtenerCoordenadasRestController;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@RestController
@RequestMapping("doctEnvio")
public class DoctoEnvioRestController {

	private DoctEnvioService doctEnvioService;

	@Autowired
	public DoctoEnvioRestController(DoctEnvioService doctEnvioService) {
		this.doctEnvioService = doctEnvioService;
	}

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private DocumentoService documentoService;

	@Autowired
	private CargaDocumentosService cargaDocumentosService;

	@Autowired
	private DoctoEnvioJDBCService doctoEnvioJDBCService;

	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	obtenerCoordenadasRestController obtenerCoordenadasRestController;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@PostMapping
	public ResponseEntity<?> guardarDoctoEnvio(@RequestParam(name = "solicitud") Integer solicitud,
			@RequestParam(name = "documento") Integer documento,
			@RequestParam(name = "nombreDocumento") String nombreDocumento,
			@RequestParam(name = "fCancelacion") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fCancelacion,
			@RequestParam(name= "productoNomina") String productoNomina,
			@RequestParam(name = "clienteId") String clienteId,
			@RequestParam(name = "idEnvio") String idEnvio
			) throws IOException {
		logger.info("Ejecutando guardarDoctoEnvio");
		
		// System.out.println("Obtenemos el valor de la solicitud:" + solicitud);
		// Obtenemos el usuario autentificado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Obtenemos el nombre del usuario autentificado
		Usuario usu = usuarioService.findByNombre(auth.getName());
		// Establecemos el formato de la fecha
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Obtenemos la fecha en String
		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		// Obtenemos la fecha actual con el formato yyyy-MM-dd
		LocalDate localDateHoy = LocalDate.parse(fechaActual);

		// Obtenemos el id del documento asociado
		ObtenerDoctoAsociadoProdDto idDocumento = cargaDocumentosService.obtenerIdDocumentoDoctAsociado(documento)
				.get(0);

		// Obtenemos la ultima solicitud registrada
		Optional<SolicitudEnvio> sEnvio;
		sEnvio = solicitudEnvioService.findById(Integer.parseInt(idEnvio));
		//sEnvio = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitud).get(0);

		//System.out.println("Solicitud de Envio" + sEnvio.getPk_id_solicitud_envio());

		// Obtenemos los datos del documento
		Documento doct = new Documento();
		//System.out.println("V Documento: " + idDocumento.getIdDocumento());
		doct = documentoService.findByPkIdDocumento(idDocumento.getIdDocumento()).get(0);
		// System.out.println("Doct:" + doct.getPk_id_documento());
		// System.out.println("documentos" + documentos.getPk_id_documento());
		List<Configuracion> url = iConfiguracionRepository.obtenerUrlPdfFirma();
		//File directorio = new File(url.get(0).getVc_valor_propiedad() +clienteId + "/"+ clienteId+"-"+solicitud);
		File directorio2 = new File(url.get(0).getVc_valor_propiedad() +clienteId);
		System.out.println("directorio2: " + directorio2);
        String[] filesNames = directorio2.list();
        int seguimiento = 0;
        if (filesNames == null) {
        	seguimiento = 1;
        } else {
        	seguimiento = filesNames.length;
        }
        String ubicacionArchivo = (url.get(0).getVc_valor_propiedad() +clienteId+"/"+clienteId+"-"+solicitud+"-"+seguimiento+"/"+nombreDocumento).toString();
		//String ubicacionArchivo = url.get(0).getVc_valor_propiedad() + nombreDocumento;
		// System.out.println("Url: " + url.get(0).getVc_valor_propiedad());
        System.out.println("Ejecutando guardarDoctoEnvio");
        logger.info("Ubicacion archivo: "+ubicacionArchivo);
		System.out.println("Ubicacion archivo: " + ubicacionArchivo);
		// System.out.println("UbicacionArchivo: " + ubicacionArchivo);

		DoctEnvio dEnvio = new DoctEnvio();
		dEnvio.setFk_solicitud_envio(sEnvio.get());
		dEnvio.setFk_cat_documento(doct);
		dEnvio.setVc_ruta_docto(ubicacionArchivo);
		dEnvio.setDt_fecha_registro(localDateHoy);
		dEnvio.setPk_usr_registra(usu);
		dEnvio.setDt_fecha_cancelacion(fCancelacion);
		// dEnvio.setI_estatus(1);
		dEnvio.setI_tp_docto_envio(1);

		System.out.println("Solicitud envio: " + dEnvio.getFk_solicitud_envio());
		System.out.println("Catalogo Documento: " + dEnvio.getFk_cat_documento());
		System.out.println("Ubicación Archivo: " + dEnvio.getVc_ruta_docto());
		dEnvio = doctEnvioService.save(dEnvio);
		if (nombreDocumento.contains("AnexosContrato")) {
			logger.info("Entro en anexosContrato");
			//System.out.println("Entro en anexosContrato");
			if(productoNomina.equals("S")) {
				//System.out.println("Entro en if de S");
				logger.info("Entro en si es nomina");
				obtenerCoordenadasRestController.obtenerCoordenadasAnexosContrato(ubicacionArchivo, dEnvio);
				obtenerCoordenadasRestController.obtenerCoordenadasAnexosContratoNominal(ubicacionArchivo, dEnvio);
			} else if (productoNomina.equals("N")){
				//System.out.println("Entro en if de N");
				logger.info("Entro en no es nomina");
				obtenerCoordenadasRestController.obtenerCoordenadasAnexosContrato(ubicacionArchivo, dEnvio);
			}
			
		} else if (nombreDocumento.contains("CONTRATO")) {
			//System.out.println("Entro en contraro");
			logger.info("Entro en contraro");
			obtenerCoordenadasRestController.obtenerCoordenadasContrato(ubicacionArchivo, dEnvio);
		} else if (nombreDocumento.contains("Pagare")) {
			//System.out.println("Entro en pagare");
			logger.info("Entro en pagare");
			obtenerCoordenadasRestController.obtenerCoordenadasPagare(ubicacionArchivo, dEnvio);
		} else if (nombreDocumento.contains("PlandePagos")) {
			//System.out.println("Entro en Plan de pagos");
			logger.info("Entro en Plan de pagos");
			obtenerCoordenadasRestController.obtenerCoordenadasPlanPagos(ubicacionArchivo, dEnvio);
		}
		//return ResponseEntity.ok(dEnvio);
		return ResponseEntity.ok("Ok");
	}

	@RequestMapping(value = { "/obtener_archivos" }, method = RequestMethod.GET)
	public ResponseEntity<?> obtenerArchivosSolicitud(@RequestParam("solicitud") Integer solicitud) {
		//System.out.println("Obtener archivos de la solicitud: " + solicitud);
		logger.info("Obtener archivos de la solicitud: " + solicitud);
		return ResponseEntity.ok(doctoEnvioJDBCService.obtenerArchivosSolicitud(solicitud));
	}

	@RequestMapping(value = {"/obtener_doctEnvio/{idDoctoEnvio}"}, method = RequestMethod.GET)
	public ResponseEntity<?> obtenerDoctEnvio(@PathVariable("idDoctoEnvio") Integer idDoctoEnvio){
		//System.out.println("Entro en obtenerDoctEnvio");
		logger.info("Entro en obtenerDoctEnvio");
		DoctEnvio dEnvio = new DoctEnvio();
		dEnvio = doctEnvioService.obtenerDoctoEnvio(idDoctoEnvio);
		if(dEnvio != null) {
			return ResponseEntity.ok(dEnvio);
		} else if (dEnvio == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@RequestMapping(value= {"/validar_doctEnvio"}, method = RequestMethod.POST)
	public ResponseEntity<?> validarDoctEnvio(@RequestParam("estatus") Integer estatus,
				@RequestParam("idDoctoEnvio") Integer idDoctoEnvio
			){
		logger.info("Entro en validarDoctEnvio");
		return ResponseEntity.ok(doctEnvioService.validarDoctEnvio(estatus, idDoctoEnvio));
	}
}
