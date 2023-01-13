package ahorrosbienestar.sib.std.xml.controller;

import java.io.File;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.xml.service.ValidityXmlService;

@RestController
public class CrearXmlController {

	@Autowired
	private ConfigService configService;

	@Autowired
	private ValidityXmlService validityXmlService;

	@GetMapping("crearArchivo/xml")
	public ResponseEntity<?> crearArchivo() {

		// Le asignamos los parametros que se requieren
		int solicitudEnvio = 91;
		String nombre = "JOSE ARTURO DE LA ROSA FUENTES";
		String monto = "158101.56";
		String plazo = "38 MESES";
		String tasa = "9.0000";

		int totalPersonasFirma = 2;

		// Obtenemos la leyenda del firmante.
		String mensaje = configService.findAll().get(21).getVc_valor_propiedad().toString();

		// System.out.println("entro en if variable1");
		// String[] words = mensaje.split("variable");

		// Asignamos los nuevos parametros.
		String nuevoMensaje = mensaje.replace("$variable1", nombre).replace("$variable2", "$" + monto)
				.replace("$variable3", plazo).replace("$variable4", tasa + "%");

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// Elemento raíz
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("requisition");
			doc.appendChild(rootElement);
			// Primer elemento
			Element contractName = doc.createElement(configService.findAll().get(34).getVc_nom_propiedad().toString());
			contractName.setTextContent(configService.findAll().get(34).getVc_valor_propiedad().toString()); // Nombre
																												// del
																												// documento
																												// a
																												// firmar
			rootElement.appendChild(contractName);

			Element acceptanceLegend = doc
					.createElement(configService.findAll().get(21).getVc_nom_propiedad().toString());
			acceptanceLegend.setTextContent(nuevoMensaje); // Leyenda que se obtiene de base de datos.
			rootElement.appendChild(acceptanceLegend);

			Element validity = doc.createElement("validity");
			validity.setTextContent(validityXmlService.spValidityNat(solicitudEnvio)); // Procedimiento almacenado para
																						// obtener los días de vigencia
			rootElement.appendChild(validity);

			Element contractType = doc.createElement(configService.findAll().get(19).getVc_nom_propiedad().toString());
			contractType.setTextContent(configService.findAll().get(19).getVc_valor_propiedad().toString()); // Tipo de
																												// contrat,
																												// se
																												// obtine
																												// de bd
			rootElement.appendChild(contractType);

			Element certificate = doc.createElement("certificate");
			rootElement.appendChild(certificate);

			Element pageCertificate = doc.createElement("page");
			pageCertificate.setTextContent("1"); // Obtenemos la última posición del documento
			certificate.appendChild(pageCertificate);

			Element positionX1Certificate = doc.createElement("positionX1");
			positionX1Certificate.setTextContent("1"); // Obtener centro del documento bd
			certificate.appendChild(positionX1Certificate);

			Element positionX2Certificate = doc.createElement("positionX2");
			positionX2Certificate.setTextContent("1"); // Obtener centro del documento bd
			certificate.appendChild(positionX2Certificate);

			Element positionY1Certificate = doc.createElement("positionY1");
			positionY1Certificate.setTextContent("1"); // Obtenemos centro del documento bd
			certificate.appendChild(positionY1Certificate);

			Element positionY2Certificate = doc.createElement("positionY2");
			positionY2Certificate.setTextContent("1"); // Obtenemos centro del documento bd
			certificate.appendChild(positionY2Certificate);

			/*
			 * 	Queda pendiente obtener los parametros de los firmantes hasta lograr lo del archivo PDF.
			 * */
			
			// Agregar foreach para traer los participantes.
			Element signers = doc.createElement("signers");
			rootElement.appendChild(signers);

			Element signerName = doc.createElement("signerName");
			signerName.setTextContent("Nombre cliente");
			signers.appendChild(signerName);

			Element mailSigner = doc.createElement("mail");
			mailSigner.setTextContent("test@gmail.com");
			signers.appendChild(mailSigner);

			Element phoneSigner = doc.createElement("phone");
			phoneSigner.setTextContent("+5215585919080");
			signers.appendChild(phoneSigner);

			Element signDevicePhoneSigner = doc.createElement("signDevicePhone");
			signDevicePhoneSigner.setTextContent("+5215585919080");
			signers.appendChild(signDevicePhoneSigner);

			Element orderSigner = doc.createElement("order");
			orderSigner.setTextContent("1");
			signers.appendChild(orderSigner);

			Element signaturesSigner = doc.createElement("signatures");
			signers.appendChild(signaturesSigner);

			Element centerXSigner = doc.createElement("centerX");
			centerXSigner.setTextContent("66.89"); // Centro X de las coordenadas para el espació de firma.
			signaturesSigner.appendChild(centerXSigner);

			Element centerYSigner = doc.createElement("centerY");
			centerYSigner.setTextContent("66.89"); // Centro Yde las coordenadas para el espació de firma.
			signaturesSigner.appendChild(centerXSigner);

			Element pageSigner = doc.createElement("page");
			pageSigner.setTextContent("1"); // Página donde va a solicitar el espacio de firma.
			signaturesSigner.appendChild(pageSigner);

			Element positionX1 = doc.createElement("positionX1");
			positionX1.setTextContent("1"); // Posicion x1 del espacio de firma.
			signaturesSigner.appendChild(positionX1);

			Element positionX2 = doc.createElement("positionX2");
			positionX2.setTextContent("2"); // Posicion x2 del espacio de firma
			signaturesSigner.appendChild(positionX2);

			Element positionY1 = doc.createElement("positionY1");
			positionY1.setTextContent("3"); // Posicion Y1 del espacio de firma
			signaturesSigner.appendChild(positionY1);

			Element positionY2 = doc.createElement("positionY2");
			positionY2.setTextContent("4"); // Posicion Y2 del espacio de firma
			signaturesSigner.appendChild(positionY2);

			Element signerType = doc.createElement("signerType");
			signerType.setTextContent("Firmante"); // Obtenemos el valor desde BD

			Element inePictureFront = doc.createElement("inePictureFront");
			inePictureFront.setTextContent("true");
			signers.appendChild(inePictureFront);

			Element inePictureBack = doc.createElement("inePictureBack");
			inePictureBack.setTextContent("true");
			signers.appendChild(inePictureBack);

			Element compareIneSelfie = doc.createElement("compareIneSelfie");
			compareIneSelfie.setTextContent("true");
			signers.appendChild(compareIneSelfie);

			Element compareSelfieProof = doc.createElement("compareSelfieProof");
			compareSelfieProof.setTextContent("true");
			signers.appendChild(compareSelfieProof);

			Element idType = doc.createElement("idType");
			idType.setTextContent("2");
			signers.appendChild(idType);

			// acceptanceVideoNotRequired
			Element acceptanceVideoNotRequired = doc
					.createElement(configService.findAll().get(22).getVc_nom_propiedad().toString());
			acceptanceVideoNotRequired.setTextContent(configService.findAll().get(22).getVc_valor_propiedad()); // Obtenemos
																												// el
																												// valor
																												// desde
																												// bd
			rootElement.appendChild(acceptanceVideoNotRequired);

			// signOnWeb
			Element signOnWeb = doc.createElement(configService.findAll().get(29).getVc_nom_propiedad().toString());
			signOnWeb.setTextContent(configService.findAll().get(29).getVc_valor_propiedad()); // Obtenemos el valor
																								// desde bd
			rootElement.appendChild(signOnWeb);

			// isShortPath
			Element isShortPath = doc.createElement(configService.findAll().get(30).getVc_nom_propiedad().toString());
			isShortPath.setTextContent(configService.findAll().get(30).getVc_valor_propiedad().toString()); // Obtenemos
																											// el valor
																											// desde bd
			rootElement.appendChild(isShortPath);

			// shortPad
			Element shortPad = doc.createElement(configService.findAll().get(31).getVc_nom_propiedad().toString());
			shortPad.setTextContent(configService.findAll().get(31).getVc_valor_propiedad().toString()); // Obtenemos el
																											// valor
																											// desde bd
			rootElement.appendChild(shortPad);

			// processId
			Element processId = doc.createElement("processId");
			processId.setTextContent("1234"); // Obtenemos el valor de la solicitud
			rootElement.appendChild(processId);

			// processType
			Element processType = doc.createElement(configService.findAll().get(32).getVc_nom_propiedad().toString());
			processType.setTextContent(configService.findAll().get(32).getVc_valor_propiedad().toString()); // Obtenemos
																											// el valor
																											// de la
																											// solicitud
			rootElement.appendChild(processType);

			Element mailResend = doc.createElement("mailResend");
			rootElement.appendChild(mailResend);

			Element emailResend = doc.createElement("email");
			emailResend.setTextContent("test@gmail.com"); // Correo del deudor
			mailResend.appendChild(emailResend);

			Element extraInfo = doc.createElement("extraInfo");
			rootElement.appendChild(extraInfo);

			Element extraInfoProperty = doc
					.createElement(configService.findAll().get(33).getVc_nom_propiedad().toString());
			extraInfoProperty.setTextContent(configService.findAll().get(33).getVc_valor_propiedad().toString());
			extraInfo.appendChild(extraInfoProperty);

			doc.setXmlStandalone(true);
			// Se escribe el contenido del XML en un archivo
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File("C:/Users/osvaldo.escamilla/Documents/home/CoordFirma/documentoFinal.xml"));
			transformer.transform(source, result);
			return ResponseEntity.ok("1");
		} catch (ParserConfigurationException pce) {
			// pce.printStackTrace();
			return ResponseEntity.ok(pce);
		} catch (TransformerException tfe) {
			// tfe.printStackTrace();
			return ResponseEntity.ok(tfe);
		}

		// return ResponseEntity.notFound().build();
	}

}
