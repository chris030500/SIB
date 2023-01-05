package ahorrosbienestar.sib.std.fichero.controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.docto_envio.controller.DoctoEnvioRestController;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.fichero.service.PdfBoxKeyWordPosition;
import ahorrosbienestar.sib.std.firma_doct.model.FirmaDocto;
import ahorrosbienestar.sib.std.firma_doct.service.FirmaDoctoService;

@RestController
@RequestMapping("obtenerCoordenadasPDF")
public class obtenerCoordenadasRestController {

	@Autowired
	private FirmaDoctoService firmaDoctoService;
	
	@Autowired 
	private DoctEnvioService doctEnvioService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	// Obtener las coordenadas del archivo Pagare
	@GetMapping()
	public ResponseEntity<?> obtenerCoordenadasPagare(String url, DoctEnvio dEnvio) throws IOException {

		// PDDocument doc1 = PDDocument.load(file);
		
		//System.out.println("RutaAbsoluta: " + rutaAbsoluta);
		logger.info("Ejecutando obtenerCoordenadasPagare");
		String keyWords = "FIRMA";
		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
		String pdfPath = url;
		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
		List<float[]> list = kwp.getCoordinate();
		// float[] c = list.get(0);
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
		float arr [][]; 
		for (int i = 0; i < list.size(); i++) {
			float[] c = list.get(i);

			logger.info("Página" + c[2]);

			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);

			// Retrieving a page of the PDF Document
			PDPage page = document.getPage((int) c[2] - 1);

			float ph = page.getMediaBox().getUpperRightY();
			float acomodoX = 40.00f;
			float acomodoY = 45.00f;
			float widht = 110.00f;
			float height = 30.00f;

			// Instantiating the PDPageContentStream class
			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);

			logger.info("Coordenadas del espacio de firma: ");
			float x1 = c[1] - acomodoX;
			float y1 = (ph - c[0]) - acomodoY;
			float x2 = x1 + widht;
			float y2 = y1 + height;
			
			logger.info("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
			logger.info("Coordenadas para pintar el rectangulo -   x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: "
					+ height);

			// Drawing a rectangle
			contentStream.addRect(c[1] - acomodoX, (ph - c[0]) - acomodoY, widht, height);
			//contentStream.addLine(x1, y1, x2, y2);
			//contentStream.addLine(x1, y1, 500, 500);
			//contentStream.lineTo(x2, y2);
			contentStream.setLineWidth(2);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			// Drawing a rectangle
			contentStream.fill();

			logger.info("Se han agregado correctamente los espacios de firma");


			// Closing the ContentStream object
			contentStream.close();

		}
		// Saving the document
		File file1 = new File(url);
		document.save(file1);

		// Closing the document
		document.close();

		return ResponseEntity.ok("Se obtuvieron las coordenadas");

	}

	// Obtener la coordenadas del archivo PlandePagos
	@GetMapping("/planPagos")
	public ResponseEntity<?> obtenerCoordenadasPlanPagos(String url, DoctEnvio dEnvio) throws IOException {

		// PDDocument doc1 = PDDocument.load(file);
		logger.info("Ejecutando obtenerCoordenadasPlanPagos");
		String keyWords = "FIRMA";
		String pdfPath = url;
		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
		List<float[]> list = kwp.getCoordinate();


		for (int i = 0; i < list.size(); i++) {
			float[] c = list.get(i);

			logger.info("Página" + c[2]);
			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);

			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
			
			// Retrieving a page of the PDF Document
			PDPage page = document.getPage((int) c[2] - 1);
			float acomodoX = 82.00f;


			float ph = page.getMediaBox().getUpperRightY();
			float widht = 140.00f;
			float height = 30.00f;

			logger.info("Coordenadas del espacio de firma: ");
			float x1 = c[1] - acomodoX;
			float y1 = (ph - c[0]);
			float x2 = x1 + widht;
			float y2 = y1 + height;

			// Instantiating the PDPageContentStream class
			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);

			logger.info("Coordenadas del espacio de firma: ");
			logger.info("x: " + (c[1] - acomodoX) + " y: " + (ph - c[0]) + " widht: " + widht + " height: " + height);
			// Drawing a rectangle
			contentStream.addRect(c[1] - acomodoX, (ph - c[0]), widht, height);
			contentStream.setLineWidth(1);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			// Drawing a rectangle
			contentStream.fill();


			logger.info("Se han agregado correctamente los espacios de firma");
			// Closing the ContentStream object
			contentStream.close();

			// Saving the document
			File file1 = new File(url);
			document.save(file1);

			// Closing the document
			document.close();
		}

		return ResponseEntity.ok("Se obtuvieron las coordenadas");

	}

	@GetMapping("/anexosContrato")
	public ResponseEntity<?> obtenerCoordenadasAnexosContrato(String url, DoctEnvio dEnvio) throws IOException {

		// PDDocument doc1 = PDDocument.load(file);
		logger.info("Ejecutando obtenerCoordenadasAnexosContrato");
		String keyWords = "FIRMA";
		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
		String pdfPath = url;
		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
		List<float[]> list = kwp.getCoordinate();
		// float[] c = list.get(0);
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		System.out.println("Total de hojas anexo contrato: " + document.getNumberOfPages());
		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
		for (int i = 0; i < list.size(); i++) {
			float[] c = list.get(i);

			logger.info("Página" + c[2]);
			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);

			// Retrieving a page of the PDF Document
			PDPage page = document.getPage((int) c[2] - 1);


			float ph = page.getMediaBox().getUpperRightY();
			float acomodoX = 0.0f;
			float acomodoY = 0.0f;
			float widht = 0.00f;
			float height = 0.00f;
			
			// Instantiating the PDPageContentStream class
			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);

			// Sentencia que obtiene las coordenadas de la hoja 1
			if (c[2] == 1.0) {
				
				
				acomodoX = 15.00f;
				acomodoY = 12.00f;
				widht = 150.00f;
				height = 25.00f;
				logger.info("Coordenadas del espacio de firma: ");
				float x1 = c[1] ;
				float y1 = (ph - c[0]) + acomodoY;
				float x2 = x1 + widht;
				float y2 = y1 + height;
				FirmaDocto firmaDocto = new FirmaDocto();
				firmaDocto.setF_coord_firma_x1(x1);
				firmaDocto.setF_coord_firma_x2(x2);
				firmaDocto.setF_coord_firma_y1(y1);
				firmaDocto.setF_coord_firma_y2(y2);
				firmaDocto.setFk_id_docto_envio(dEnvio);
				firmaDocto = firmaDoctoService.save(firmaDocto);
				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
				logger.info("Coordenadas del espacio de firma: ");
				logger.info(
						"x: " + c[1] + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: " + height);
				// Drawing a rectangle
				contentStream.addRect(c[1], (ph - c[0]) + acomodoY, widht, height);
			}
			// Sentencia que obtine las coordenadas de la hoja 2
			if (c[2] == 2.0) {
				acomodoX = 60.00f;
				acomodoY = 15.00f;
				widht = 165.00f;
				height = 50.00f;
				logger.info("Coordenadas del espacio de firma: ");
				float x1 = c[1] - acomodoX;
				float y1 = (ph - c[0]) + acomodoY;
				float x2 = x1 + widht;
				float y2 = y1 + height;
				FirmaDocto firmaDocto = new FirmaDocto();
				firmaDocto.setF_coord_firma_x1(x1);
				firmaDocto.setF_coord_firma_x2(x2);
				firmaDocto.setF_coord_firma_y1(y1);
				firmaDocto.setF_coord_firma_y2(y2);
				firmaDocto.setFk_id_docto_envio(dEnvio);
				firmaDocto = firmaDoctoService.save(firmaDocto);
				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
				logger.info("Coordenadas del espacio de firma: ");
				logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht
						+ " height: " + height);

				contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
			}
			// Aquí obtenemos la última posición del tamaño de la lista
			float[] ultimo = list.get(list.size() - 1);

			// Sentecia que obtiene las coordenadas de la última posición de la lista (Hoja
			// 3)
			if (c[2] == 3.0 && c == list.get(list.size() - 1)) {
				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
				// float acomodoX3 = 5.00f;
				acomodoX = 18.00f;
				acomodoY = 12.00f;
				widht = 115.00f;
				height = 25.00f;
				logger.info("Coordenadas del espacio de firma: ");
				float x1 = ultimo[1] - acomodoX;
				float y1 = (ph - ultimo[0]) - acomodoY;
				float x2 = x1 + widht;
				float y2 = y1 + height;
				FirmaDocto firmaDocto = new FirmaDocto();
				firmaDocto.setF_coord_firma_x1(x1);
				firmaDocto.setF_coord_firma_x2(x2);
				firmaDocto.setF_coord_firma_y1(y1);
				firmaDocto.setF_coord_firma_y2(y2);
				firmaDocto.setFk_id_docto_envio(dEnvio);
				firmaDocto = firmaDoctoService.save(firmaDocto);
				logger.info("Obteniendo último elemento de la lista : " + list.get(list.size() - 1));
				logger.info("Coordenadas del espacio de firma: ");
				logger.info("x: " + (ultimo[1] - acomodoX) + " y: " + ((ph - ultimo[0]) + acomodoY) + " widht: " + widht
						+ " height: " + height);
				// Drawing a rectangle
				contentStream.addRect(ultimo[1] - acomodoX, (ph - ultimo[0]) + acomodoY, widht, height);
			}

			contentStream.setLineWidth(1);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			// Drawing a rectangle
			contentStream.fill();
			logger.info("Se han agregado correctamente los espacios de firma");

			contentStream.close();

		}
		// Saving the document
		File file1 = new File(url);
		document.save(file1);

		// Closing the document
		document.close();

		return ResponseEntity.ok("Se obtuvieron las coordenadas");

	}

	// Obtener la coordenadas del archivo PlandePagos
	@GetMapping("/anexosContratoNominal")
	public ResponseEntity<?> obtenerCoordenadasAnexosContratoNominal(String url, DoctEnvio dEnvio) throws IOException {

		// PDDocument doc1 = PDDocument.load(file);
		logger.info("Ejecutando obtenerCoordenadasAnexosContratoNominal");
		String keyWords = "ACEPTO";
		String pdfPath = url;
		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
		List<float[]> list = kwp.getCoordinate();
		// float[] c = list.get(0);

		for (int i = 0; i < list.size(); i++) {
			float[] c = list.get(i);
			logger.info("Página: " + c[2]);

			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);

			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);

			// Retrieving a page of the PDF Document
			PDPage page = document.getPage((int) c[2] - 1);
			float acomodoX = 60.00f;
			float acomodoY = 10.00f;
			float widht = 155.00f;
			float height = 60.00f;

			PDRectangle rect = page.getMediaBox();

			float ph = page.getMediaBox().getUpperRightY();
			float x1 = c[1] - acomodoX;
			float y1 = (ph - c[0]) - acomodoY;
			float x2 = x1 + widht;
			float y2 = y1 + height;
			FirmaDocto firmaDocto = new FirmaDocto();
			firmaDocto.setF_coord_firma_x1(x1);
			firmaDocto.setF_coord_firma_x2(x2);
			firmaDocto.setF_coord_firma_y1(y1);
			firmaDocto.setF_coord_firma_y2(y2);
			firmaDocto.setFk_id_docto_envio(dEnvio);
			firmaDocto = firmaDoctoService.save(firmaDocto);
			// Instantiating the PDPageContentStream class
			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
			logger.info("Coordenadas del espacio de firma: ");
			logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: "
					+ height);

			// Drawing a rectangle
			contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
			contentStream.setLineWidth(1);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			// Drawing a rectangle
			contentStream.fill();

			logger.info("Se han agregado correctamente los espacios de firma");
			// Closing the ContentStream object
			contentStream.close();

			// Saving the document
			File file1 = new File(url);
			document.save(file1);

			// Closing the document
			document.close();
		}

		return ResponseEntity.ok("Se obtuvieron las coordenadas");

	}
	
	// Obtener las coordenadas del archivo Contrato
	@GetMapping("/contrato")
	public ResponseEntity<?> obtenerCoordenadasContrato(String url, DoctEnvio dEnvio) throws IOException {

		// PDDocument doc1 = PDDocument.load(file);
		System.out.println("Contrato");
		System.out.println("dEnvio: " + dEnvio.getPk_id_docto_envio());
		logger.info("Ejecutando obtenerCoordenadasContrato");
		String keyWords = "FIRMA";
		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
		String pdfPath = url;
		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
		List<float[]> list = kwp.getCoordinate();
		// float[] c = list.get(0);
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
		System.out.println("Total de paginas Contrato: " + document.getNumberOfPages() );
		for (int i = 0; i < list.size(); i++) {
			float[] c = list.get(i);

			logger.info("Página" + c[2]);

			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);

			// Retrieving a page of the PDF Document
			PDPage page = document.getPage((int) c[2] - 1);

			PDRectangle rect = page.getMediaBox();

			float ph = page.getMediaBox().getUpperRightY();
			float acomodoX = 70.00f;
			float acomodoY = 00.00f;
			float widht = 0.00f;
			float height = 0.00f;

			// Instantiating the PDPageContentStream class
			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
			if(c[2] != 1.0) {
				
				if(c != list.get(list.size() - 1)) {
					acomodoY = 60.00f;
					widht = 130.00f;
					height = 40.00f;
					float x1 = c[1] - acomodoX;
					float y1 = (ph - c[0]) - acomodoY;
					float x2 = x1 + widht;
					float y2 = y1 + height;
					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(x1);
					firmaDocto.setF_coord_firma_x2(x2);
					firmaDocto.setF_coord_firma_y1(y1);
					firmaDocto.setF_coord_firma_y2(y2);
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto = firmaDoctoService.save(firmaDocto);
					logger.info("Coordenadas del espacio de firma: ");
					logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: "
							+ height);
					// Drawing a rectangle
					contentStream.addRect(c[1] - acomodoX, (ph - c[0]) - acomodoY, widht, height);
				}
				
				if(c == list.get(list.size() - 1)) {
					logger.info("Obtenemos la última coordenada");
					acomodoY = 5.00f;
					widht = 130.00f;
					height = 20.00f;
					float x1 = c[1] - acomodoX;
					float y1 = (ph - c[0]) + acomodoY;
					float x2 = x1 + widht;
					float y2 = y1 + height;
					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(x1);
					firmaDocto.setF_coord_firma_x2(x2);
					firmaDocto.setF_coord_firma_y1(y1);
					firmaDocto.setF_coord_firma_y2(y2);
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto = firmaDoctoService.save(firmaDocto);
					logger.info("Coordenadas del espacio de firma: ");
					logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: "
							+ height);
					// Drawing a rectangle
					contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
				}

				
			}
			
			contentStream.setLineWidth(1);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			// Drawing a rectangle
			contentStream.fill();

			logger.info("Se han agregado correctamente los espacios de firma");


			// Closing the ContentStream object
			contentStream.close();

		}
		// Saving the document
		File file1 = new File(url);
		document.save(file1);

		// Closing the document
		document.close();

		return ResponseEntity.ok("Se obtuvieron las coordenadas");

	}

}
