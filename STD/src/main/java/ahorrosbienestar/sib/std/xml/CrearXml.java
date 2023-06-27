package ahorrosbienestar.sib.std.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearXml {
//	public static void main(String argv[]) {
//	    try {
//	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//	      //Elemento raíz
//	      Document doc = docBuilder.newDocument();
//	      Element rootElement = doc.createElement("requisition");
//	      doc.appendChild(rootElement);
//	      //Primer elemento
//	      Element contractName = doc.createElement("contractName");
//	      contractName.setTextContent("ContratoBancaP"); //Nombre del documento a firmar
//	      rootElement.appendChild(contractName);
//	      
//	      
//	      Element acceptanceLegend = doc.createElement("acceptanceLegend");
//	      acceptanceLegend.setTextContent("Yo NombreDelFirmante acepto firmar NombreDelDocumento, hoy\r\n"
//	      		+ "FechaDeFirma."); //Leyenda que se obtiene de base de datos.
//	      rootElement.appendChild(acceptanceLegend);
//	      
//	      Element validity = doc.createElement("validity");
//	      validity.setTextContent("5"); // Procedimiento almacenado para obtener los días de vigencia
//	      rootElement.appendChild(validity);
//	      
//	      Element contractType = doc.createElement("contractType");
//	      contractType.setTextContent("Contrato"); //Tipo de contrat, se obtine de bd
//	      rootElement.appendChild(contractType);
//	      
//	      Element certificate = doc.createElement("certificate");
//	      rootElement.appendChild(certificate);
//	      
//	      Element pageCertificate = doc.createElement("page");
//	      pageCertificate.setTextContent("1"); //Obtenemos la última posición del documento
//	      certificate.appendChild(pageCertificate);
//	      
//	      Element positionX1Certificate = doc.createElement("positionX1");
//	      positionX1Certificate.setTextContent("1"); //Obtener centro del documento bd
//	      certificate.appendChild(positionX1Certificate);
//	      
//	      Element positionX2Certificate = doc.createElement("positionX2");
//	      positionX2Certificate.setTextContent("1"); // Obtener centro del documento bd
//	      certificate.appendChild(positionX2Certificate);
//	      
//	      Element positionY1Certificate = doc.createElement("positionY1");
//	      positionY1Certificate.setTextContent("1"); //Obtenemos centro del documento bd
//	      certificate.appendChild(positionY1Certificate);
//	      
//	      Element positionY2Certificate = doc.createElement("positionY2");
//	      positionY2Certificate.setTextContent("1"); //Obtenemos centro del documento bd
//	      certificate.appendChild(positionY2Certificate);
//	      
//	      Element signers = doc.createElement("signers");
//	      rootElement .appendChild(signers);
//	      
//	      //Agregar foreach para traer los participantes.
//	      
//	      Element signerName = doc.createElement("signerName");
//	      signerName.setTextContent("Nombre cliente");
//	      signers.appendChild(signerName);
//	      
//	      Element mailSigner = doc.createElement("mail");
//	      mailSigner.setTextContent("test@gmail.com");
//	      signers.appendChild(mailSigner);
//	      
//	      Element phoneSigner = doc.createElement("phone");
//	      phoneSigner.setTextContent("+5215585919080");
//	      signers.appendChild(phoneSigner);
//	      
//	      Element signDevicePhoneSigner = doc.createElement("signDevicePhone");
//	      signDevicePhoneSigner.setTextContent("+5215585919080");
//	      signers.appendChild(signDevicePhoneSigner);
//	      
//	      Element orderSigner = doc.createElement("order");
//	      orderSigner.setTextContent("1");
//	      signers.appendChild(orderSigner);
//	      
//	      Element signaturesSigner = doc.createElement("signatures");
//	      orderSigner.appendChild(signaturesSigner);
//	      
//	      Element centerXSigner = doc.createElement("centerX");
//	      centerXSigner.setTextContent("66.89"); //Centro X de las coordenadas para el espació de firma.
//	      signaturesSigner.appendChild(centerXSigner);
//	      
//	      Element centerYSigner = doc.createElement("centerY");
//	      centerYSigner.setTextContent("66.89"); //Centro Yde las coordenadas para el espació de firma.
//	      signaturesSigner.appendChild(centerXSigner);
//	      
//	      Element pageSigner = doc.createElement("page");
//	      pageSigner.setTextContent("1"); //Página donde va a solicitar el espacio de firma.
//	      signaturesSigner.appendChild(pageSigner);
//	      
//	      Element positionX1 = doc.createElement("positionX1");
//	      positionX1.setTextContent("1"); //Posicion x1 del espacio de firma.
//	      signaturesSigner.appendChild(positionX1);
//	      
//	      Element positionX2 = doc.createElement("positionX2");	 
//	      positionX2.setTextContent("2"); //Posicion x2 del espacio de firma
//	      signaturesSigner.appendChild(positionX2);
//	      
//	      Element positionY1 = doc.createElement("positionY1");	 
//	      positionY1.setTextContent("3"); //Posicion Y1 del espacio de firma
//	      signaturesSigner.appendChild(positionY1);
//	      
//	      Element positionY2 = doc.createElement("positionY2");
//	      positionY2.setTextContent("4"); //Posicion Y2 del espacio de firma
//	      signaturesSigner.appendChild(positionY2);
//	      
//	      Element signerType = doc.createElement("signerType");
//	      signerType.setTextContent("Firmante"); //Obtenemos el valor desde BD
//	      
//	      
//	      Element inePictureFront = doc.createElement("inePictureFront");
//	      inePictureFront.setTextContent("true");
//	      signers.appendChild(inePictureFront);
//	      
//	      Element inePictureBack = doc.createElement("inePictureBack");
//	      inePictureBack.setTextContent("true");
//	      signers.appendChild(inePictureBack);
//	      
//	      Element compareIneSelfie = doc.createElement("compareIneSelfie");
//	      compareIneSelfie.setTextContent("true");
//	      signers.appendChild(compareIneSelfie);
//	      
//	      Element compareSelfieProof = doc.createElement("compareSelfieProof");
//	      compareSelfieProof.setTextContent("true");
//	      signers.appendChild(compareSelfieProof);
//	      
//	      Element idType = doc.createElement("idType");
//	      idType.setTextContent("2");
//	      signers.appendChild(idType);
//	      
//	      Element acceptanceVideoNotRequired = doc.createElement("acceptanceVideoNotRequired");
//	      acceptanceVideoNotRequired.setTextContent("false"); //Obtenemos el valor desde bd
//	      rootElement.appendChild(acceptanceVideoNotRequired);
//	      
//	      Element signOnWeb = doc.createElement("signOnWeb");
//	      signOnWeb.setTextContent("true"); //Obtenemos el valor desde bd
//	      rootElement.appendChild(signOnWeb);
//	      
//	      Element isShortPath = doc.createElement("isShortPath");
//	      isShortPath.setTextContent("false"); //Obtenemos el valor desde bd
//	      rootElement.appendChild(isShortPath);
//	      
//	      Element shortPad = doc.createElement("shortPad");
//	      shortPad.setTextContent("false"); //Obtenemos el valor desde bd
//	      rootElement.appendChild(shortPad);
//	      
//	      Element processId = doc.createElement("processId");
//	      processId.setTextContent("1234"); //Obtenemos el valor de la solicitud
//	      rootElement.appendChild(processId);
//	      
//	      Element processType = doc.createElement("processType");
//	      processType.setTextContent("1"); //Obtenemos el valor de la solicitud
//	      rootElement.appendChild(processType);
//	      
//	      Element mailResend = doc.createElement("mailResend");
//	      rootElement.appendChild(mailResend);
//	      
//	      Element emailResend = doc.createElement("email");
//	      emailResend.setTextContent("test@gmail.com"); //Correo del deudor
//	      mailResend.appendChild(emailResend);
//	      
//	      
//	      
//	      //Se escribe el contenido del XML en un archivo
//	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
//	      Transformer transformer = transformerFactory.newTransformer();
//	      DOMSource source = new DOMSource(doc);
//	      StreamResult result = new StreamResult(new File("C:/Users/osvaldo.escamilla/Documents/home/CoordFirma/documentoFinal.xml"));
//	      transformer.transform(source, result);
//	    } catch (ParserConfigurationException pce) {
//	      pce.printStackTrace();
//	    } catch (TransformerException tfe) {
//	      tfe.printStackTrace();
//	    }
//	  }

}
