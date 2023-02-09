package ahorrosbienestar.sib.std.docto_envio.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.graphics.MoveTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@PostMapping
	public ResponseEntity<?> guardarDoctoEnvio(@RequestParam(name = "solicitud") Integer solicitud,
			@RequestParam(name = "documento") Integer documento,
			@RequestParam(name = "nombreDocumento") String nombreDocumento) throws IOException {
		System.out.println("solicitud: " + solicitud);
		System.out.println("documento: " + documento);
		System.out.println("nombreDocumento " + nombreDocumento);

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
		SolicitudEnvio sEnvio = new SolicitudEnvio();
		sEnvio = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitud, 0);

		System.out.println("Solicitud de Envio" + sEnvio.getPk_id_solicitud_envio());

		// Obtenemos los datos del documento
		Documento doct = new Documento();
		System.out.println("V Documento: " + idDocumento.getIdDocumento());
		doct = documentoService.findByPkIdDocumento(idDocumento.getIdDocumento()).get(0);
		// System.out.println("Doct:" + doct.getPk_id_documento());
		// System.out.println("documentos" + documentos.getPk_id_documento());
		List<Configuracion> url = iConfiguracionRepository.obtenerUrlPdfFirma();
		String ubicacionArchivo = url.get(0).getVc_valor_propiedad() + nombreDocumento;
		// System.out.println("Url: " + url.get(0).getVc_valor_propiedad());
		System.out.println("Ubicacion archivo: " + ubicacionArchivo);
		// System.out.println("UbicacionArchivo: " + ubicacionArchivo);

		DoctEnvio dEnvio = new DoctEnvio();
		dEnvio.setFk_solicitud_envio(sEnvio);
		dEnvio.setFk_cat_documento(doct);
		dEnvio.setVc_ruta_docto(ubicacionArchivo);
		sEnvio.setDt_fecha_registro(localDateHoy);
		sEnvio.setFk_usr_registra(usu);
		// dEnvio.setI_estatus(1);
		dEnvio.setI_tp_docto_envio(1);

		System.out.println("Solicitud envio: " + dEnvio.getFk_solicitud_envio());
		System.out.println("Catalogo Documento: " + dEnvio.getFk_cat_documento());
		System.out.println("Ubicación Archivo: " + dEnvio.getVc_ruta_docto());
		dEnvio = doctEnvioService.save(dEnvio);
		if (nombreDocumento.contains("AnexosContrato")) {
			System.out.println("Entro en anexosContrato");
			obtenerCoordenadasRestController.obtenerCoordenadasAnexosContrato(ubicacionArchivo, dEnvio);
		} else if (nombreDocumento.contains("CONTRATO")) {
			System.out.println("Entro en contraro");
			obtenerCoordenadasRestController.obtenerCoordenadasContrato(ubicacionArchivo, dEnvio);
		} else if (nombreDocumento.contains("Pagare")) {
			System.out.println("Entro en pagare");
			obtenerCoordenadasRestController.obtenerCoordenadasPagare(ubicacionArchivo, dEnvio);
		} else if (nombreDocumento.contains("PlandePagos")) {
			System.out.println("Entro en Plan de pagos");
			obtenerCoordenadasRestController.obtenerCoordenadasPlanPagos(ubicacionArchivo, dEnvio);
		}
		return ResponseEntity.ok(dEnvio);
		// return ResponseEntity.ok("Ok");
	}

	@RequestMapping(value = { "/obtener_archivos" }, method = RequestMethod.GET)
	public ResponseEntity<?> obtenerArchivosSolicitud(@RequestParam("solicitud") Integer solicitud) {
		System.out.println("Obtener archivos de la solicitud: " + solicitud);

		return ResponseEntity.ok(doctoEnvioJDBCService.obtenerArchivosSolicitud(solicitud));
	}

	@RequestMapping(value = { "/obtener_doctEnvio/{idDoctoEnvio}" }, method = RequestMethod.GET)
	public ResponseEntity<?> obtenerDoctEnvio(@PathVariable("idDoctoEnvio") Integer idDoctoEnvio) {
		System.out.println("Entro en obtenerDoctEnvio");
		DoctEnvio dEnvio = new DoctEnvio();
		dEnvio = doctEnvioService.obtenerDoctoEnvio(idDoctoEnvio);
		if (dEnvio != null) {
			return ResponseEntity.ok(dEnvio);
		} else if (dEnvio == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

}
