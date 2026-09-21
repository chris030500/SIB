package ahorrosbienestar.sib.std.core_bancario.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.carga_documentos.service.CargaDocumentosService;
import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@RestController()
public class CoreBancarioRestController {

	@Autowired
	private CoreBancarioService coreBancarioService;

	@Autowired
	private CargaDocumentosService cargaDocumentosService;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(CoreBancarioRestController.class);

	@RequestMapping(value = { "/buscar_solicitud" }, method = RequestMethod.GET)
	public ResponseEntity<?> buscarSolicitud(@RequestParam("solicitud") Integer solicitud) {
		// System.out.println("Buscar Solicitud: " + solicitud);
		try {
			logger.info("Buscar Solicitud: " + solicitud);
			logger.info("Resultado de validaSolicitud: {}", coreBancarioService.validaSolicitud(solicitud));
			return ResponseEntity.ok(coreBancarioService.validaSolicitud(solicitud));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			logger.error("Entro en Exception");
			logger.error(e.getMessage());
			return ResponseEntity.ok(e.getMessage());
		}

	}

	@RequestMapping(value = { "/trae_participantes" }, method = RequestMethod.GET)
	public ResponseEntity<?> traeParticipantes(@RequestParam("solicitud") Integer solicitud) {
		// System.out.println("Solicitud en participante: " + solicitud);
		try {
			logger.info("Solicitud en participante: " + solicitud);
			logger.info("Resultado de traeParticipantes: {}",
			        coreBancarioService.traeParticipantes(solicitud));
			return ResponseEntity.ok(coreBancarioService.traeParticipantes(solicitud));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok(e.getMessage());
		}
	}

	@RequestMapping(value = { "/lista_productos" }, method = RequestMethod.GET)
	public ResponseEntity<?> listaProductos(@RequestParam("solicitud") Integer solicitud) {
		// System.out.println("Lista: " + solicitud);
		try {
			logger.info("Lista: " + solicitud);
			logger.info("Resultado de listaProductos: {}",
			        coreBancarioService.listaProductos(solicitud));
			return ResponseEntity.ok(coreBancarioService.listaProductos(solicitud));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok(e.getMessage());
		}

	}

	@RequestMapping(value = { "/buscar_catProducto" }, method = RequestMethod.GET)
	public int spBuscarCatProducto(@RequestParam("producto") Integer producto) {
		logger.info("spBuscarCatProducto: " + producto);
		logger.info("Resultado de spBuscarCatProducto: {}",
		        coreBancarioService.spBuscarCatProducto(producto));
		return coreBancarioService.spBuscarCatProducto(producto);
	}

	@RequestMapping(value = { "/buscar_documento" }, method = RequestMethod.GET)
	public ResponseEntity<?> spBuscarDocumento(@RequestParam("producto") Integer producto) {
		try {
			logger.info("spBuscarDocumento: " + producto);
			logger.info("Resultado de obtenerDoctAsociadoProd: {}",
			        cargaDocumentosService.obtenerDoctAsociadoProd(producto));
			return ResponseEntity.ok(cargaDocumentosService.obtenerDoctAsociadoProd(producto));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok(e.getMessage());
		}

	}

	@RequestMapping(value = { "/buscar_documento_clienteNuevo" }, method = RequestMethod.GET)
	public ResponseEntity<?> spBuscarDocumentoClienteNuevo(@RequestParam("producto") Integer producto) {
		try {
			logger.info("spBuscarDocumentoClienteNuevo: " + producto);
			logger.info("Resultado de obtenerDoctAsociadoProdClienteNuevo: {}",
			        cargaDocumentosService.obtenerDoctAsociadoProdClienteNuevo(producto));
			return ResponseEntity.ok(cargaDocumentosService.obtenerDoctAsociadoProdClienteNuevo(producto));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok(e.getMessage());
		}
	}

}

