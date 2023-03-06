package ahorrosbienestar.sib.std.xml.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;
import ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.model.ObtenerCoordenadas;
import ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.service.ObtenerCoordenadasJDBCService;
import ahorrosbienestar.sib.std.docto_unificado.service.DoctoUnificadoService;
import ahorrosbienestar.sib.std.fichero.service.UnirArchivosService;
import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.service.FirmaDoctoUnificadoService;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.service.ProductoService;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.xml.service.ValidityXmlService;

@RestController
public class CrearXmlController {

	@Autowired
	private ConfigService configService;

	@Autowired
	private ValidityXmlService validityXmlService;

	@Autowired
	private ObtenerCoordenadasJDBCService obtenerCoordenadasJDBCService;

	@Autowired
	private UnirArchivosService unirArchivosService;

	@Autowired
	private FirmaDoctoUnificadoService firmaDoctoUnificadoService;

	@Autowired
	private DoctoUnificadoService doctoUnificadoService;

	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private SolicitudEnvioService solicitudEnvioService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping("crearArchivo/xml")
	public ResponseEntity<?> crearArchivo(@RequestParam("solicitudEnvio") Integer solicitudEnvio,
			@RequestParam("solicitudCore") Integer solicitudCore) {

		System.out.println("CORE: " + solicitudCore);
		logger.info("Crear archivo XML");
		logger.info("Solicitud Envio: " + solicitudEnvio);
		logger.info("Solicitud Core bancario: " + solicitudCore);
		// Obtenemos los datos de los participantes:
		List<ParticipantesDto> titular = coreBancarioService.traeParticipantes(solicitudCore);
		String nombre = titular.get(0).getNombreCompleto();
		String direccion = titular.get(0).getDireccionCompleta();
		String telefonoCelular = titular.get(0).getTelefonoCelular();
		String correoParticipanteTitular = titular.get(0).getCorreoElectronico();
		System.out.println("Dirección: " + direccion);
		List<ValidaSolicitudDto> datosSolicitud = coreBancarioService.validaSolicitud(solicitudCore);
		String monto = datosSolicitud.get(0).getMontoAutorizado();
		String plazo = datosSolicitud.get(0).getPlazo();
		String tasa = datosSolicitud.get(0).getTasaFija();
		String tasaMoratoria = datosSolicitud.get(0).getTasaMoratoria();
		
		//Obtenemos el I_PRODUCTO_ID
		int idProducto = solicitudEnvioService.findById(solicitudEnvio).get().getI_producto_id();
		
		Producto infoProducto = productoService.findByNoProducto(idProducto);
		String nombreProducto = infoProducto.getVc_nombre_producto().toString();
		String kyc = infoProducto.getVc_identificador_kyc().toString();
		String descripcionProducto = infoProducto.getVc_descripcion_producto().toString();
		// Le asignamos los parametros que se requieren
		// int solicitudEnvio = 91;
		// String nombre = "JOSE ARTURO DE LA ROSA FUENTES";
//		String monto = "158101.56";
//		String plazo = "38 MESES";
//		String tasa = "9.0000";

		int totalPersonasFirma = titular.size();

		List<ObtenerCoordenadas> documentos = obtenerCoordenadasJDBCService.obtenerInfoCoordenadas(solicitudEnvio, 0);
		String ruta = documentos.get(0).getVc_ruta_docto();

		String rutaArchivoFinal = unirArchivosService.eliminarUltimaPalabra(ruta);
		
		List<Configuracion> configuracion = configService.findAll();

		System.out.println("RutaFinal: " + rutaArchivoFinal);
		String rutaBase = configService.findAll().get(13).getVc_valor_propiedad().toString();
		String nombreArchivoXML = configService.findAll().get(44).getVc_valor_propiedad().toString();

		// Obtenemos la leyenda del firmante.
		String mensaje = configService.findAll().get(21).getVc_valor_propiedad().toString();

		// System.out.println("entro en if variable1");
		// String[] words = mensaje.split("variable");

		// Asignamos los nuevos parametros.
		String nuevoMensaje = mensaje.replace("$variable1", direccion).replace("$variable2", nombre)
				.replace("$variable3", "$" + monto).replace("$variable4", plazo).replace("$variable5", tasa + "%")
				.replace("$variable6", tasaMoratoria + "%").replace("$variable7", plazo);

		try {
			logger.info("Creando archivo XML");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// Elemento raíz
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("requisition");
			doc.appendChild(rootElement);

			// Primer elemento
			// localizationNotRequired
			Element localizationNotRequired = doc
					.createElement(configuracion.get(45).getVc_nom_propiedad().toString());
			localizationNotRequired.setTextContent(configuracion.get(45).getVc_valor_propiedad().toString());
			rootElement.appendChild(localizationNotRequired);

			Element contractName = doc.createElement(configuracion.get(34).getVc_nom_propiedad().toString());
			// Nombre del documento a firmar
			contractName.setTextContent(configuracion.get(34).getVc_valor_propiedad().toString());
			rootElement.appendChild(contractName);

			Element acceptanceLegend = doc
					.createElement(configuracion.get(21).getVc_nom_propiedad().toString());
			acceptanceLegend.setTextContent(nuevoMensaje); // Leyenda que se obtiene de base de datos.
			rootElement.appendChild(acceptanceLegend);

			Element validity = doc.createElement("validity");
			// Procedimiento almacenado para obtener los días de vigencia
			validity.setTextContent(validityXmlService.spValidityNat(solicitudEnvio));
			rootElement.appendChild(validity);

			// Tipo de contrat, se obtine de bd
			Element contractType = doc.createElement(configuracion.get(19).getVc_nom_propiedad().toString());
			contractType.setTextContent(configuracion.get(19).getVc_valor_propiedad().toString());
			rootElement.appendChild(contractType);

			// useWhatsAppTemplate
			Element useWhatsAppTemplate = doc
					.createElement(configuracion.get(46).getVc_nom_propiedad().toString());
			useWhatsAppTemplate.setTextContent(configuracion.get(46).getVc_valor_propiedad().toString());
			rootElement.appendChild(useWhatsAppTemplate);

			// Obtenemos el id del documento unificado para buscar las coordenadas
			Optional<DoctoUnificado> doctoUnificado = doctoUnificadoService.findByFkIdSolicituEnvio(solicitudEnvio);
			int idDoctUnificado = doctoUnificado.get().getPk_id_docto_unificado();
			System.out.println("FirmaDoctUni: " + idDoctUnificado);
			// Obtenemos la información de la ultima pagina para el certificado.

			List<FirmaDoctoUnificado> fDoctoUnificado = firmaDoctoUnificadoService
					.obtenerCoordenadasFirma(idDoctUnificado);
			int tamanoList = firmaDoctoUnificadoService.obtenerCoordenadasFirma(idDoctUnificado).size();

			System.out.println("Tamaño list: " + tamanoList);

			System.out.println("Obtener los datos de la ultima pagina: "
					+ fDoctoUnificado.get(tamanoList - 1).getPk_id_firma_docto_unificado());

			int noPage = fDoctoUnificado.get(tamanoList - 1).getI_no_hoja();
			float x1Certificate = fDoctoUnificado.get(tamanoList - 1).getF_coord_firma_x1();
			float x2Certificate = fDoctoUnificado.get(tamanoList - 1).getF_coord_firma_x2();
			float y1Certificate = fDoctoUnificado.get(tamanoList - 1).getF_coord_firma_y1();
			float y2Certificate = fDoctoUnificado.get(tamanoList - 1).getF_coord_firma_y2();
//			float xCentralCertificate = fDoctoUnificado.get(tamañoList-1).getF_coord_firma_xCentral();
//			float yCentralCertificate = fDoctoUnificado.get(tamañoList-1).getF_coord_firma_yCentral();

			Element certificate = doc.createElement("certificate");
			rootElement.appendChild(certificate);

			Element pageCertificate = doc.createElement("page");
			pageCertificate.setTextContent(String.valueOf(noPage)); // Obtenemos la última posición del documento
			certificate.appendChild(pageCertificate);

			Element positionX1Certificate = doc.createElement("positionX1");
			positionX1Certificate.setTextContent(String.valueOf(x1Certificate)); // Obtener centro del documento bd
			certificate.appendChild(positionX1Certificate);

			Element positionX2Certificate = doc.createElement("positionX2");
			positionX2Certificate.setTextContent(String.valueOf(x2Certificate)); // Obtener centro del documento bd
			certificate.appendChild(positionX2Certificate);

			// Obtenemos centro del documento bd
			Element positionY1Certificate = doc.createElement("positionY1");
			positionY1Certificate.setTextContent(String.valueOf(y1Certificate)); 
			certificate.appendChild(positionY1Certificate);

			// Obtenemos centro del documento bd
			Element positionY2Certificate = doc.createElement("positionY2");
			positionY2Certificate.setTextContent(String.valueOf(y2Certificate)); 
			certificate.appendChild(positionY2Certificate);

			// System.out.println("fDoctoUnificado: " +
			// fDoctoUnificado.get(i).getPk_id_firma_docto_unificado());
			// Agregar foreach para traer los participantes.
			Element signers = doc.createElement("signers");
			rootElement.appendChild(signers);

			Element signerName = doc.createElement("signerName");
			//signerName.setTextContent(nombre);
			signerName.setTextContent("OSVALDO ESCAMILLA");
			signers.appendChild(signerName);

			Element mailSigner = doc.createElement("mail");
			// mailSigner.setTextContent(correoParticipanteTitular);
			mailSigner.setTextContent("oescamilla@ahorrosbienestar.com");
			signers.appendChild(mailSigner);

			Element phoneSigner = doc.createElement("phone");
			//phoneSigner.setTextContent("+52" + telefonoCelular);
			phoneSigner.setTextContent("+524428971930");
			signers.appendChild(phoneSigner);

			Element signDevicePhoneSigner = doc.createElement("signDevicePhone");
			//signDevicePhoneSigner.setTextContent("+52" + telefonoCelular);
			signDevicePhoneSigner.setTextContent("+524428971930");
			signers.appendChild(signDevicePhoneSigner);
			
			Element authenticationType = doc.createElement("authenticationType");
			authenticationType.setTextContent("Código de seguridad");
			signers.appendChild(authenticationType);
			
			Element authenticationData = doc.createElement("authenticationData");
			//authenticationData.setTextContent(codigoSeguridad(telefonoCelular, 4));
			authenticationData.setTextContent("1930");
			signers.appendChild(authenticationData);

			Element orderSigner = doc.createElement("order");
			orderSigner.setTextContent(String.valueOf(1));
			signers.appendChild(orderSigner);

			for (int i = 0; i < tamanoList; i++) {

				if ((tamanoList - 1) != i) {

					Element signaturesSigner = doc.createElement("signatures");
					signers.appendChild(signaturesSigner);

					// Centro X de las coordenadas para el espació de firma.
					Element centerXSigner = doc.createElement("centerX");
					centerXSigner.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_xCentral()));
					signaturesSigner.appendChild(centerXSigner);

					// Centro Y de las coordenadas para el espació de firma.
					Element centerYSigner = doc.createElement("centerY");
					centerYSigner.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_yCentral()));
					signaturesSigner.appendChild(centerYSigner);

					// Página donde va a solicitar el espacio de firma.
					Element pageSigner = doc.createElement("page");
					pageSigner.setTextContent(String.valueOf(fDoctoUnificado.get(i).getI_no_hoja()));
					signaturesSigner.appendChild(pageSigner);

					// Posicion x1 del espacio de firma.
					Element positionX1 = doc.createElement("positionX1");
					positionX1.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_x1()));
					signaturesSigner.appendChild(positionX1);

					// Posicion x2 del espacio de firma
					Element positionX2 = doc.createElement("positionX2");
					positionX2.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_x2()));
					signaturesSigner.appendChild(positionX2);

					// Posicion Y1 del espacio de firma
					Element positionY1 = doc.createElement("positionY1");
					positionY1.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_y1()));
					signaturesSigner.appendChild(positionY1);

					// Posicion Y2 del espacio de firma
					Element positionY2 = doc.createElement("positionY2");
					positionY2.setTextContent(String.valueOf(fDoctoUnificado.get(i).getF_coord_firma_y2()));
					signaturesSigner.appendChild(positionY2);

					// Obtenemos el valor desde BD
					Element signerType = doc.createElement(configuracion.get(20).getVc_nom_propiedad().toString());
					signerType.setTextContent(configuracion.get(20).getVc_valor_propiedad().toString());
					signaturesSigner.appendChild(signerType);
				}
			}

			Element inePictureFront = doc
					.createElement(configuracion.get(23).getVc_nom_propiedad().toString());
			inePictureFront.setTextContent(configuracion.get(23).getVc_valor_propiedad().toString());
			signers.appendChild(inePictureFront);

			Element inePictureBack = doc
					.createElement(configuracion.get(24).getVc_nom_propiedad().toString());
			inePictureBack.setTextContent(configuracion.get(24).getVc_valor_propiedad().toString());
			signers.appendChild(inePictureBack);

			//compareIneSelfie
			Element compareIneSelfie = doc
					.createElement(configuracion.get(25).getVc_nom_propiedad().toString());
			compareIneSelfie.setTextContent(configService.findAll().get(25).getVc_valor_propiedad().toString());
			signers.appendChild(compareIneSelfie);
			
			//minCompareDocSelfie
			Element minCompareDocSelfie = doc.createElement(configuracion.get(47).getVc_nom_propiedad().toString());
			minCompareDocSelfie.setTextContent(configuracion.get(47).getVc_valor_propiedad().toString());
			signers.appendChild(minCompareDocSelfie);

			Element compareSelfieProof = doc
					.createElement(configuracion.get(26).getVc_nom_propiedad().toString());
			compareSelfieProof.setTextContent(configuracion.get(26).getVc_valor_propiedad().toString());
			signers.appendChild(compareSelfieProof);

//			Element urlSignerRedirect = doc
//					.createElement(configService.findAll().get(27).getVc_nom_propiedad().toString());
//			urlSignerRedirect.setTextContent(configService.findAll().get(27).getVc_valor_propiedad().toString());
//			signers.appendChild(urlSignerRedirect);

			Element idType = doc.createElement(configuracion.get(28).getVc_nom_propiedad().toString());
			idType.setTextContent(configuracion.get(28).getVc_valor_propiedad().toString());
			signers.appendChild(idType);

			// acceptanceVideoNotRequired
			Element acceptanceVideoNotRequired = doc
					.createElement(configuracion.get(22).getVc_nom_propiedad().toString());
			acceptanceVideoNotRequired.setTextContent(configuracion.get(22).getVc_valor_propiedad());
			rootElement.appendChild(acceptanceVideoNotRequired);

			// signOnWeb
//			Element signOnWeb = doc.createElement(configService.findAll().get(29).getVc_nom_propiedad().toString());
//			signOnWeb.setTextContent(configService.findAll().get(29).getVc_valor_propiedad());
//			rootElement.appendChild(signOnWeb);

			// isShortPath
			Element isShortPath = doc.createElement(configuracion.get(30).getVc_nom_propiedad().toString());
			isShortPath.setTextContent(configuracion.get(30).getVc_valor_propiedad().toString());
			rootElement.appendChild(isShortPath);

			// shortPad
			Element shortPad = doc.createElement(configuracion.get(31).getVc_nom_propiedad().toString());
			shortPad.setTextContent(configuracion.get(31).getVc_valor_propiedad().toString());
			rootElement.appendChild(shortPad);

			// processId
			Element processId = doc.createElement("processId");
			// Obtenemos el valor de la solicitud
			processId.setTextContent(solicitudEnvio.toString()); 
			rootElement.appendChild(processId);

			// processType
			Element processType = doc.createElement(configuracion.get(32).getVc_nom_propiedad().toString());
			processType.setTextContent(configuracion.get(32).getVc_valor_propiedad().toString());
			rootElement.appendChild(processType);

			Element mailResend = doc.createElement("mailResend");
			rootElement.appendChild(mailResend);

			Element emailResend = doc.createElement("email");
			// Correo del deudor
			emailResend.setTextContent("oescamilla@ahorrosbienestar.com"); 
			mailResend.appendChild(emailResend);

			Element extraInfo = doc.createElement("extraInfo");
			rootElement.appendChild(extraInfo);

			//KYC
			Element extraInfoProperty = doc
					.createElement(configuracion.get(33).getVc_nom_propiedad().toString());
			//extraInfoProperty.setTextContent("bf50f041-ef6e-406b-9121-bc23f5176c17");
			extraInfoProperty.setTextContent(kyc);
			extraInfo.appendChild(extraInfoProperty);

			Attr attr = doc.createAttribute("name");
			attr.setValue("surveysProductId");
			extraInfoProperty.setAttributeNode(attr);
			
			//Nombre del producto
			Element extraInfoProperty2 = doc
					.createElement(configuracion.get(33).getVc_nom_propiedad().toString());
			extraInfoProperty2.setTextContent("Pensionados digital");
			//extraInfoProperty2.setTextContent(nombreProducto);
			extraInfo.appendChild(extraInfoProperty2);
			
			Attr attr2 = doc.createAttribute("name");
			attr2.setValue("productName");
			extraInfoProperty2.setAttributeNode(attr2);
			
			//Descripción del producto
			Element extraInfoProperty3 = doc
					.createElement(configuracion.get(33).getVc_nom_propiedad().toString());
			extraInfoProperty3.setTextContent("Descripcion corta del producto");
			//extraInfoProperty3.setTextContent(descripcionProducto);
			extraInfo.appendChild(extraInfoProperty3);
			
			Attr attr3 = doc.createAttribute("name");
			attr3.setValue("productDescription");
			extraInfoProperty3.setAttributeNode(attr3);

			doc.setXmlStandalone(true);
			// Se escribe el contenido del XML en un archivo
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(
//					new File("C:/Users/osvaldo.escamilla/Documents/home/CoordFirma/documentoFinal.xml"));
			System.out.println("URL XML: " + (rutaBase + rutaArchivoFinal + "/" + nombreArchivoXML));
			StreamResult result = new StreamResult(new File(rutaBase + rutaArchivoFinal + "/" + nombreArchivoXML));
			transformer.transform(source, result);
			return ResponseEntity.ok("1");
		} catch (ParserConfigurationException pce) {
			// pce.printStackTrace();
			logger.info(pce.getMessage());
			return ResponseEntity.ok(pce.getMessage());
		} catch (TransformerException tfe) {
			// tfe.printStackTrace();
			logger.info(tfe.getMessage());
			return ResponseEntity.ok(tfe.getMessage());
		}

		// return ResponseEntity.notFound().build();
	}

	public static String codigoSeguridad(String telefono, int cantidadQuitarNum) {
		if (telefono == null || cantidadQuitarNum > telefono.length()) {
			return telefono;
		}
		return telefono.substring(telefono.length() - cantidadQuitarNum);
	}

}
