package ahorrosbienestar.sib.std.fichero.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ahorrosbienestar.sib.std.coord_doc.model.CoordenadaDocumento;
import ahorrosbienestar.sib.std.coord_doc.service.CoordenadaDocumentoService;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.firma_doct.model.FirmaDocto;
import ahorrosbienestar.sib.std.firma_doct.service.FirmaDoctoService;

@RestController
@RequestMapping("obtenerCoordenadasPDF")
public class obtenerCoordenadasRestController {

	@Autowired
	private FirmaDoctoService firmaDoctoService;

	@Autowired
	private DoctEnvioService doctEnvioService;

	@Autowired
	private CoordenadaDocumentoService coordenadaDocumentoService;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final Logger logger = Logger.getLogger(obtenerCoordenadasRestController.class);

	// Obtener las coordenadas del archivo Pagare
//	@GetMapping()
//	public ResponseEntity<?> obtenerCoordenadasPagare(String url, DoctEnvio dEnvio) throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		
//		//logger.info("RutaAbsoluta: " + rutaAbsoluta);
//		logger.info("Ejecutando obtenerCoordenadasPagare Tabla amortizacion");
//		String keyWords = "FIRMA";
//		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
//		String pdfPath = url;
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(pdfPath);
//		PDDocument document = PDDocument.load(file);
//		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
//		//float arr [][]; 
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 40.00f;
//			float acomodoY = 45.00f;
//			float widht = 110.00f;
//			float height = 30.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//
//			logger.info("Coordenadas del espacio de firma: ");
//			float x1 = 61f;
//			float y1 = 84.3f;
//			float x2 =  84f;
//			float y2 = 87.3f;
//			int hoja = (int) c[2];
//			float xCentral = 68.3702f;
//			float yCentral = 34.4741f;
//			FirmaDocto firmaDocto = new FirmaDocto();
//			firmaDocto.setF_coord_firma_x1(x1);
//			firmaDocto.setF_coord_firma_x2(x2);
//			firmaDocto.setF_coord_firma_y1(y1);
//			firmaDocto.setF_coord_firma_y2(y2);
//			firmaDocto.setFk_id_docto_envio(dEnvio);
//			firmaDocto.setI_no_hoja(hoja);
//			firmaDocto.setF_coord_firma_xCentral(xCentral);
//			firmaDocto.setF_coord_firma_yCentral(yCentral);
//			firmaDocto = firmaDoctoService.save(firmaDocto);
//			
//			logger.info("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
//			logger.info("Coordenadas para pintar el rectangulo -   x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: "
//					+ height);
//
//			// Drawing a rectangle
//			//contentStream.addRect(c[1] - acomodoX, (ph - c[0]) - acomodoY, widht, height);
//			//contentStream.addLine(x1, y1, x2, y2);
//			//contentStream.addLine(x1, y1, 500, 500);
//			//contentStream.lineTo(x2, y2);
////			contentStream.setLineWidth(2);
////			contentStream.setNonStrokingColor(Color.WHITE);
////			contentStream.setStrokingColor(Color.BLACK);
////			contentStream.stroke();
////			// Drawing a rectangle
////			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//
//			// Closing the ContentStream object
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(url);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Metodo para obtener las coordenadas del documento pagare
	@GetMapping()
	public ResponseEntity<?> obtenerCoordenadasPagare(String url, DoctEnvio dEnvio, Documento docto) {
		try {
			// PDDocument doc1 = PDDocument.load(file);

			// logger.info("RutaAbsoluta: " + rutaAbsoluta);
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
			// Obtenemos el documento para despues obtener el número total de hojas.
			String pdfPath = url;
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/Pagare.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
			// logger.info("Numero de paginas del documento: " +
			// doctEnvioService.totalHojas(document.getNumberOfPages(),dEnvio.getPk_id_docto_envio()));
			logger.info("Total de hojas: " + document.getNumberOfPages());
			//logger.info("Total de hojas: " + document.getNumberOfPages());

			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService
					.obtenerCoordenadaDocto(docto.getPk_id_documento());
			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(31);
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				for (int i = 0; i < coordenadas.size(); i++) {
					logger.info("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());
//					System.out
//							.println("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());
//					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
//					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
//					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
//					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
//					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
//					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
//					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
					firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
					firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
					firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
					firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
					firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
					firmaDocto = firmaDoctoService.save(firmaDocto);

				}
				logger.info("Se obtuvieron correctamente las coordenadas del documento pagare");
				return ResponseEntity.ok("Se obtuvieron correctamente las coordenadas del documento pagare");
			} else {
				logger.info("No contiene coordenadas el documento pagare");
				return ResponseEntity.ok("No contiene coordenadas el documento pagare");
			}

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento pagare");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento pagare");
		}
//		logger.info("Se obtuvieron correctamente las coordenadas del documento pagare");
//		return ResponseEntity.ok("Se obtuvieron correctamente las coordenadas del documento pagare");

	}

	/*
	 * QUEDA PENDIENTE HASTA QUE SE REALICE LA MODIFICACION DEL ARCHIVO
	 */

	// Obtener la coordenadas del archivo PlandePagos o tabla de amortizacion
	@GetMapping("/planPagos")
	public ResponseEntity<?> obtenerCoordenadasPlanPagos(String url, DoctEnvio dEnvio, Documento docto)
			throws IOException {
		try {
			// PDDocument doc1 = PDDocument.load(file);

			// logger.info("RutaAbsoluta: " + rutaAbsoluta);
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
			// Obtenemos el documento para despues obtener el número total de hojas.
			String pdfPath = url;
			//String pdfPath = "C:/Users/osvaldo.escamilla/Documents/PlandePagos.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
			logger.info("Total de hojas: " + document.getNumberOfPages());
			//logger.info("Total de hojas: " + document.getNumberOfPages());

			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(docto.getPk_id_documento());
			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService.obtenerCoordenadaDocto(29);

			logger.info("Coordenadas: " + coordenadas);
			//logger.info("Coordenadas: " + coordenadas);
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				logger.info("Contiene información");
				for (int i = 0; i < coordenadas.size(); i++) {

					logger.info("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());
//					System.out
//							.println("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());
//					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
//					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
//					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
//					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
//					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
//					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
//					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
					firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
					firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
					firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
					firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
					firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
					firmaDocto = firmaDoctoService.save(firmaDocto);

				}
				logger.info("Se obtuvieron correctamente las coordenadas del documento tabla de amortización");
				return ResponseEntity
						.ok("Se obtuvieron correctamente las coordenadas del documento tabla de amortización");
			} else {
				logger.info("Es nulo");
				logger.info("No contiene coordenadas el documento tabla de amortización");
				return ResponseEntity.ok("No contiene coordenadas el documento tabla de amortización");
			}

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento tabla de amortización");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento tabla de amortización");
		}
		// logger.info("Se obtuvieron correctamente las coordenadas del documento tabla
		// de amortización");
		// return ResponseEntity.ok("Se obtuvieron correctamente las coordenadas del
		// documento tabla de amortización");
	}

	// Obtener la coordenadas del archivo PlandePagos o tabla de amortizacion
//	@GetMapping("/planPagos")
//	public ResponseEntity<?> obtenerCoordenadasPlanPagos(String url, DoctEnvio dEnvio) throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		logger.info("Ejecutando obtenerCoordenadasPlanPagos");
//		String keyWords = "FIRMA";
//		String pdfPath = url;
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
//		List<float[]> list = kwp.getCoordinate();
//
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//			float xCentral = 0;
//			float yCentral = 0;
//
//			logger.info("Página" + c[2]);
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			File file = new File(pdfPath);
//			PDDocument document = PDDocument.load(file);
//
//			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//			float acomodoX = 82.00f;
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float widht = 140.00f;
//			float height = 30.00f;
//
//			logger.info("Coordenadas del espacio de firma: ");
//			float x1 = c[1] - acomodoX;
//			float y1 = (ph - c[0]);
//			float x2 = x1 + widht;
//			float y2 = y1 + height;
//			int hoja = (int) c[2];
//			xCentral = (x1 + x2) / 2;
//			yCentral = (y1 + y2) / 2;
//			FirmaDocto firmaDocto = new FirmaDocto();
//			firmaDocto.setF_coord_firma_x1(x1);
//			firmaDocto.setF_coord_firma_x2(x2);
//			firmaDocto.setF_coord_firma_y1(y1);
//			firmaDocto.setF_coord_firma_y2(y2);
//			firmaDocto.setI_no_hoja(hoja);
//			firmaDocto.setFk_id_docto_envio(dEnvio);
//			firmaDocto.setF_coord_firma_xCentral(xCentral);
//			firmaDocto.setF_coord_firma_yCentral(yCentral);
//			firmaDocto = firmaDoctoService.save(firmaDocto);
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//
//			logger.info("Coordenadas del espacio de firma: ");
//			logger.info("x: " + (c[1] - acomodoX) + " y: " + (ph - c[0]) + " widht: " + widht + " height: " + height);
//			// Drawing a rectangle
////			contentStream.addRect(c[1] - acomodoX, (ph - c[0]), widht, height);
////			contentStream.setLineWidth(1);
////			contentStream.setNonStrokingColor(Color.WHITE);
////			contentStream.setStrokingColor(Color.BLACK);
////			contentStream.stroke();
//			// Drawing a rectangle
//			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//			// Closing the ContentStream object
//			contentStream.close();
//
//			// Saving the document
//			File file1 = new File(url);
//			document.save(file1);
//
//			// Closing the document
//			document.close();
//		}
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	@GetMapping("/anexosContrato")
	public ResponseEntity<?> obtenerCoordenadasAnexosContrato(String url, DoctEnvio dEnvio, Documento docto,
			String productoNomina) throws IOException {
		try {
			// PDDocument doc1 = PDDocument.load(file);
			// String nomina = "N";
			// logger.info("RutaAbsoluta: " + rutaAbsoluta);
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
			// Obtenemos el documento para despues obtener el número total de hojas.
			String pdfPath = url;
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/AnexosContrato.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
			// logger.info("Numero de paginas del documento: " +
			// doctEnvioService.totalHojas(document.getNumberOfPages(),dEnvio.getPk_id_docto_envio()));
			
			logger.info("Total de hojas: " + document.getNumberOfPages());

			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService
					.obtenerCoordenadaDocto(docto.getPk_id_documento());
			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(91);
			// int totalCoordenadas = coordenadas.size();
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				for (int i = 0; i < coordenadas.size(); i++) {

					if (productoNomina.equals("S")) {
						logger.info(
								"Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

						logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
						logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
						logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
						logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
						logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
						logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
						logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

						FirmaDocto firmaDocto = new FirmaDocto();
						firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
						firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
						firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
						firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
						firmaDocto.setFk_id_docto_envio(dEnvio);
						firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
						firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
						firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
						firmaDocto = firmaDoctoService.save(firmaDocto);

					} else if (productoNomina.equals("N")) {

						if (coordenadas.get(i).getI_pagina() == 4) {
							logger.info("No se contempla la hoja 4");
						} else {
							logger.info(
									"Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

							logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
							logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
							logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
							logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
							logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
							logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
							logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

							FirmaDocto firmaDocto = new FirmaDocto();
							firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
							firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
							firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
							firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
							firmaDocto.setFk_id_docto_envio(dEnvio);
							firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
							firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
							firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
							firmaDocto = firmaDoctoService.save(firmaDocto);
						}

					}

				}

				logger.info("Se obtuvieron correctamente las coordenadas del documento anexo contrato");
				return ResponseEntity.ok("Se obtuvieron correctamente las coordenadas del documento anexo contrato");
			} else {
				logger.info("Es nulo");
				logger.info("No contiene coordenadas el documento anexo contrato");
				return ResponseEntity.ok("No contiene coordenadas el documento anexo contrato");
			}

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento anexo contrato");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento anexo contrato");
		}

	}

//	@GetMapping("/anexosContrato")
//	public ResponseEntity<?> obtenerCoordenadasAnexosContrato(String url, DoctEnvio dEnvio) throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		logger.info("Ejecutando obtenerCoordenadasAnexosContrato");
//		String keyWords = "FIRMA";
//		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
//
//		String pdfPath = url;
//		logger.info("pdfPath: " + pdfPath);
//		logger.info("pdfPath: " + pdfPath);
//		logger.info("Url: " + url);
//		logger.info("pdfPath: " + pdfPath);
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(pdfPath);
//		PDDocument document = PDDocument.load(file);
//		logger.info("Total de hojas anexo contrato: " + document.getNumberOfPages());
//		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
//		for (int i = 0; i < list.size(); i++) {
//			float xCentral = 0;
//			float yCentral = 0;
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 0.0f;
//			float acomodoY = 0.0f;
//			float widht = 0.00f;
//			float height = 0.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//
//			// Sentencia que obtiene las coordenadas de la hoja 1
//			if (c[2] == 1.0) {
//
//				acomodoX = 15.00f;
//				acomodoY = 12.00f;
//				widht = 150.00f;
//				height = 25.00f;
//				logger.info("Coordenadas del espacio de firma: ");
////				float x1 = c[1] ;
////				float y1 = (ph - c[0]) + acomodoY;
////				float x2 = x1 + widht;
////				float y2 = y1 + height;
//				float x1 = 84.10f;
//				float y1 = 18.52f;
//				float x2 = 80.88f;
//				float y2 = 18.52f;
//				int hoja = (int) c[2];
//				xCentral = (x1 + x2) / 2;
//				yCentral = (y1 + y2) / 2;
//				FirmaDocto firmaDocto = new FirmaDocto();
//				firmaDocto.setF_coord_firma_x1(x1);
//				firmaDocto.setF_coord_firma_x2(x2);
//				firmaDocto.setF_coord_firma_y1(y1);
//				firmaDocto.setF_coord_firma_y2(y2);
//				firmaDocto.setI_no_hoja(hoja);
//				firmaDocto.setFk_id_docto_envio(dEnvio);
//				firmaDocto.setF_coord_firma_xCentral(xCentral);
//				firmaDocto.setF_coord_firma_yCentral(yCentral);
//				firmaDocto = firmaDoctoService.save(firmaDocto);
//				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
//				logger.info("Coordenadas del espacio de firma: ");
//				logger.info(
//						"x: " + c[1] + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: " + height);
//				// Drawing a rectangle
//				// contentStream.addRect(c[1], (ph - c[0]) + acomodoY, widht, height);
//			}
//			// Sentencia que obtine las coordenadas de la hoja 2
//			if (c[2] == 2.0) {
//				acomodoX = 60.00f;
//				acomodoY = 15.00f;
//				widht = 165.00f;
//				height = 50.00f;
//				logger.info("Coordenadas del espacio de firma: ");
////				float x1 = c[1] - acomodoX;
////				float y1 = (ph - c[0]) + acomodoY;
////				float x2 = x1 + widht;
////				float y2 = y1 + height;
//				float x1 = 84.10f;
//				float y1 = 18.52f;
//				float x2 = 80.88f;
//				float y2 = 18.52f;
//				int hoja = (int) c[2];
//				xCentral = (x1 + x2) / 2;
//				yCentral = (y1 + y2) / 2;
//				FirmaDocto firmaDocto = new FirmaDocto();
//				firmaDocto.setF_coord_firma_x1(x1);
//				firmaDocto.setF_coord_firma_x2(x2);
//				firmaDocto.setF_coord_firma_y1(y1);
//				firmaDocto.setF_coord_firma_y2(y2);
//				firmaDocto.setI_no_hoja(hoja);
//				firmaDocto.setFk_id_docto_envio(dEnvio);
//				firmaDocto.setF_coord_firma_xCentral(xCentral);
//				firmaDocto.setF_coord_firma_yCentral(yCentral);
//				firmaDocto = firmaDoctoService.save(firmaDocto);
//				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
//				logger.info("Coordenadas del espacio de firma: ");
//				logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht
//						+ " height: " + height);
//
//				// contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht,
//				// height);
//			}
//			// Aquí obtenemos la última posición del tamaño de la lista
//			float[] ultimo = list.get(list.size() - 1);
//
//			// Sentecia que obtiene las coordenadas de la última posición de la lista (Hoja
//			// 3)
//			if (c[2] == 3.0 && c == list.get(list.size() - 1)) {
//				logger.info("Obteniendo coordenadas de la hoja : " + c[2]);
//				// float acomodoX3 = 5.00f;
//				acomodoX = 18.00f;
//				acomodoY = 12.00f;
//				widht = 115.00f;
//				height = 25.00f;
//				logger.info("Coordenadas del espacio de firma: ");
////				float x1 = ultimo[1] - acomodoX;
////				float y1 = (ph - ultimo[0]) - acomodoY;
////				float x2 = x1 + widht;
////				float y2 = y1 + height;
//				float x1 = 84.10f;
//				float y1 = 18.52f;
//				float x2 = 80.88f;
//				float y2 = 18.52f;
//				xCentral = (x1 + x2) / 2;
//				yCentral = (y1 + y2) / 2;
//				int hoja = (int) c[2];
//				FirmaDocto firmaDocto = new FirmaDocto();
//				firmaDocto.setF_coord_firma_x1(x1);
//				firmaDocto.setF_coord_firma_x2(x2);
//				firmaDocto.setF_coord_firma_y1(y1);
//				firmaDocto.setF_coord_firma_y2(y2);
//				firmaDocto.setI_no_hoja(hoja);
//				firmaDocto.setF_coord_firma_xCentral(xCentral);
//				firmaDocto.setF_coord_firma_yCentral(yCentral);
//				firmaDocto.setFk_id_docto_envio(dEnvio);
//				firmaDocto = firmaDoctoService.save(firmaDocto);
//				logger.info("Obteniendo último elemento de la lista : " + list.get(list.size() - 1));
//				logger.info("Coordenadas del espacio de firma: ");
//				logger.info("x: " + (ultimo[1] - acomodoX) + " y: " + ((ph - ultimo[0]) + acomodoY) + " widht: " + widht
//						+ " height: " + height);
//				// Drawing a rectangle
//				// contentStream.addRect(ultimo[1] - acomodoX, (ph - ultimo[0]) + acomodoY,
//				// widht, height);
//			}
//
////			contentStream.setLineWidth(1);
////			contentStream.setNonStrokingColor(Color.WHITE);
////			contentStream.setStrokingColor(Color.BLACK);
////			contentStream.stroke();
////			// Drawing a rectangle
////			contentStream.fill();
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(url);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Obtener la coordenadas del archivo PlandePagos
//	@GetMapping("/anexosContratoNominal")
//	public ResponseEntity<?> obtenerCoordenadasAnexosContratoNominal(String url, DoctEnvio dEnvio) throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		logger.info("Ejecutando obtenerCoordenadasAnexosContratoNominal");
//		String keyWords = "ACEPTO";
//		String pdfPath = url;
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		float xCentral = 0;
//		float yCentral = 0;
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//			logger.info("Página: " + c[2]);
//
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			File file = new File(pdfPath);
//			PDDocument document = PDDocument.load(file);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//			float acomodoX = 60.00f;
//			float acomodoY = 10.00f;
//			float widht = 155.00f;
//			float height = 60.00f;
//
//			PDRectangle rect = page.getMediaBox();
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float x1 = c[1] - acomodoX;
//			float y1 = (ph - c[0]) - acomodoY;
//			float x2 = x1 + widht;
//			float y2 = y1 + height;
//			int hoja = (int) c[2];
//			xCentral = (x1 + x2) / 2;
//			yCentral = (y1 + y2) / 2;
//			FirmaDocto firmaDocto = new FirmaDocto();
//			firmaDocto.setF_coord_firma_x1(x1);
//			firmaDocto.setF_coord_firma_x2(x2);
//			firmaDocto.setF_coord_firma_y1(y1);
//			firmaDocto.setF_coord_firma_y2(y2);
//			firmaDocto.setI_no_hoja(hoja);
//			firmaDocto.setFk_id_docto_envio(dEnvio);
//			firmaDocto.setF_coord_firma_xCentral(xCentral);
//			firmaDocto.setF_coord_firma_yCentral(yCentral);
//			firmaDocto = firmaDoctoService.save(firmaDocto);
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//			logger.info("Coordenadas del espacio de firma: ");
//			logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: "
//					+ height);
//
//			// Drawing a rectangle
////			contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
////			contentStream.setLineWidth(1);
////			contentStream.setNonStrokingColor(Color.WHITE);
////			contentStream.setStrokingColor(Color.BLACK);
////			contentStream.stroke();
////			// Drawing a rectangle
////			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//			// Closing the ContentStream object
//			contentStream.close();
//
//			// Saving the document
//			File file1 = new File(url);
//			document.save(file1);
//
//			// Closing the document
//			document.close();
//		}
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Obtener las coordenadas del archivo Contrato
	@GetMapping("/contrato")
	public ResponseEntity<?> obtenerCoordenadasContrato(String url, DoctEnvio dEnvio, Documento docto)
			throws IOException {
		try {
			// PDDocument doc1 = PDDocument.load(file);

			// logger.info("RutaAbsoluta: " + rutaAbsoluta);
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
			// Obtenemos el documento para despues obtener el número total de hojas.
			String pdfPath = url;
			// String pdfPath =
			// "C:/Users/osvaldo.escamilla/Documents/Contrato_Apertura_272195_23_03_2023.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
			// logger.info("Numero de paginas del documento: " +
			// doctEnvioService.totalHojas(document.getNumberOfPages(),dEnvio.getPk_id_docto_envio()));
			logger.info("Total de hojas: " + document.getNumberOfPages());

			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService
					.obtenerCoordenadaDocto(docto.getPk_id_documento());
			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(94);
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				for (int i = 0; i < coordenadas.size(); i++) {

					logger.info("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
					firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
					firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
					firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
					firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
					firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
					firmaDocto = firmaDoctoService.save(firmaDocto);

				}
				logger.info("Se obtuvieron correctamente las coordenadas del documento contrato de apertura");
				return ResponseEntity
						.ok("Se obtuvieron correctamente las coordenadas del documento contrato de apertura");
			} else {
				logger.info("Es nulo");
				logger.info("No contiene coordenadas el documento contrato de apertura");
				return ResponseEntity.ok("No contiene coordenadas el contrato de apertura");
			}

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento contrato de apertura");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento contrato de apertura");
		}

	}

	// Obtener las coordenadas del archivo Contrato
//	@GetMapping("/contrato")
//	public ResponseEntity<?> obtenerCoordenadasContrato(String url, DoctEnvio dEnvio) throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		logger.info("Contrato");
//		logger.info("dEnvio: " + dEnvio.getPk_id_docto_envio());
//		logger.info("Ejecutando obtenerCoordenadasContrato");
//		String keyWords = "FIRMA";
//		// String archivo = "C:/Users/osvaldo.escamilla/Documents/Modelo.pdf";
//		String pdfPath = url;
//		logger.info("url: " + url);
//		logger.info("pdfPath: " + pdfPath);
//		logger.info("url: " + url);
//		logger.info("pdfPath: " + pdfPath);
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, pdfPath);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(pdfPath);
//		PDDocument document = PDDocument.load(file);
//		doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());
//		logger.info("Total de paginas Contrato: " + document.getNumberOfPages());
//		float xCentral = 0;
//		float yCentral = 0;
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			// PDRectangle rect = page.getMediaBox();
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 00.00f;
//			float acomodoY = 00.00f;
//			float widht = 0.00f;
//			float height = 0.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//			if (c[2] != 1.0) {
//
//				if (c == list.get(list.size() - 3)) {
//					logger.info("c: " + c);
//					acomodoY = 10.00f;
//					acomodoX = 45.00f;
//					widht = 130.00f;
//					height = 40.00f;
//					float x1 = c[1] - acomodoX;
//					float y1 = (ph - c[0]) - acomodoY;
//					float x2 = x1 + widht;
//					float y2 = y1 + height;
//					int hoja = (int) c[2];
//					xCentral = (x1 + x2) / 2;
//					yCentral = (y1 + y2) / 2;
//					FirmaDocto firmaDocto = new FirmaDocto();
//					firmaDocto.setF_coord_firma_x1(x1);
//					firmaDocto.setF_coord_firma_x2(x2);
//					firmaDocto.setF_coord_firma_y1(y1);
//					firmaDocto.setF_coord_firma_y2(y2);
//					firmaDocto.setI_no_hoja(hoja);
//					firmaDocto.setFk_id_docto_envio(dEnvio);
//					firmaDocto.setF_coord_firma_xCentral(xCentral);
//					firmaDocto.setF_coord_firma_yCentral(yCentral);
//					firmaDocto = firmaDoctoService.save(firmaDocto);
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("x: " + (c[1]) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht
//							+ " height: " + height);
//					logger.info("x: " + (c[1]) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht + " height: "
//							+ height);
//					// Drawing a rectangle
//					// contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht,
//					// height);
//				}
//
//				if (c == list.get(list.size() - 1)) {
//					logger.info("Obtenemos la última coordenada");
//					acomodoY = 5.00f;
//					acomodoX = 70.00f;
//					widht = 130.00f;
//					height = 35.00f;
//					float x1 = c[1] - acomodoX;
//					float y1 = (ph - c[0]) + acomodoY;
//					float x2 = x1 + widht;
//					float y2 = y1 + height;
//					int hoja = (int) c[2];
//					xCentral = (x1 + x2) / 2;
//					yCentral = (y1 + y2) / 2;
//					FirmaDocto firmaDocto = new FirmaDocto();
//					firmaDocto.setF_coord_firma_x1(x1);
//					firmaDocto.setF_coord_firma_x2(x2);
//					firmaDocto.setF_coord_firma_y1(y1);
//					firmaDocto.setF_coord_firma_y2(y2);
//					firmaDocto.setI_no_hoja(hoja);
//					firmaDocto.setFk_id_docto_envio(dEnvio);
//					firmaDocto.setF_coord_firma_xCentral(xCentral);
//					firmaDocto.setF_coord_firma_yCentral(yCentral);
//					firmaDocto = firmaDoctoService.save(firmaDocto);
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht
//							+ " height: " + height);
//					// Drawing a rectangle
//					// contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht,
//					// height);
//				}
//
//			}
//
////			contentStream.setLineWidth(1);
////			contentStream.setNonStrokingColor(Color.WHITE);
////			contentStream.setStrokingColor(Color.BLACK);
////			contentStream.stroke();
////			// Drawing a rectangle
////			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//			// Closing the ContentStream object
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(url);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

//	@GetMapping("/contrato2")
//	public ResponseEntity<?> obtenerCoordenadasContrato2() throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//		logger.info("Contrato");
//		// logger.info("dEnvio: " + dEnvio.getPk_id_docto_envio());
//		logger.info("Ejecutando obtenerCoordenadasContrato");
//		String keyWords = "FIRMA";
//		String archivo = "C:/Users/osvaldo.escamilla/Documents/Contrato_Apertura.pdf";
////		String pdfPath = url;
////		logger.info("url: " + url);
////		logger.info("pdfPath: " + pdfPath);
////		logger.info("url: " + url);
////		logger.info("pdfPath: " + pdfPath);
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, archivo);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(archivo);
//		PDDocument document = PDDocument.load(file);
//		// doctEnvioService.totalHojas(document.getNumberOfPages(),
//		// dEnvio.getPk_id_docto_envio());
//		logger.info("Total de paginas Contrato: " + document.getNumberOfPages());
//		float xCentral = 0;
//		float yCentral = 0;
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//			logger.info("Página" + c[2]);
////			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
////			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			// PDRectangle rect = page.getMediaBox();
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 00.00f;
//			float acomodoY = 00.00f;
//			float widht = 0.00f;
//			float height = 0.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//			if (c[2] != 1.0) {
//
//				if (c == list.get(list.size() - 3)) {
//					// logger.info("c: " + c.toString());
//					// logger.info("Coordenada -3");
//					// logger.info(list.get(list.size()));
//					acomodoY = 10.00f;
//					acomodoX = 45.00f;
//					widht = 130.00f;
//					height = 40.00f;
//					float x1 = c[1] - acomodoX;
//					float y1 = (ph - c[0]) - acomodoY;
//					float x2 = x1 + widht;
//					float y2 = y1 + height;
//					int hoja = (int) c[2];
//					xCentral = (x1 + x2) / 2;
//					yCentral = (y1 + y2) / 2;
////					FirmaDocto firmaDocto = new FirmaDocto();
////					firmaDocto.setF_coord_firma_x1(x1);
////					firmaDocto.setF_coord_firma_x2(x2);
////					firmaDocto.setF_coord_firma_y1(y1);
////					firmaDocto.setF_coord_firma_y2(y2);
////					firmaDocto.setI_no_hoja(hoja);
////					firmaDocto.setFk_id_docto_envio(dEnvio);
////					firmaDocto.setF_coord_firma_xCentral(xCentral);
////					firmaDocto.setF_coord_firma_yCentral(yCentral);
////					firmaDocto = firmaDoctoService.save(firmaDocto);
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("x: " + (c[1]) + " y: " + ((ph - c[0]) - acomodoY) + " widht: " + widht
//							+ " height: " + height);
//					logger.info("x: " + (c[1]) + " y: " + ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: "
//							+ height);
//					// Drawing a rectangle
//					contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
//				}
//
//				if (c == list.get(list.size() - 1)) {
//					logger.info("Obtenemos la última coordenada");
//					logger.info("Obtenemos la última coordenada");
//					acomodoY = 5.00f;
//					acomodoX = 70.00f;
//					widht = 130.00f;
//					height = 35.00f;
//					float x1 = c[1] - acomodoX;
//					float y1 = (ph - c[0]) + acomodoY;
//					float x2 = x1 + widht;
//					float y2 = y1 + height;
//					int hoja = (int) c[2];
//					xCentral = (x1 + x2) / 2;
//					yCentral = (y1 + y2) / 2;
////					FirmaDocto firmaDocto = new FirmaDocto();
////					firmaDocto.setF_coord_firma_x1(x1);
////					firmaDocto.setF_coord_firma_x2(x2);
////					firmaDocto.setF_coord_firma_y1(y1);
////					firmaDocto.setF_coord_firma_y2(y2);
////					firmaDocto.setI_no_hoja(hoja);
////					firmaDocto.setFk_id_docto_envio(dEnvio);
////					firmaDocto.setF_coord_firma_xCentral(xCentral);
////					firmaDocto.setF_coord_firma_yCentral(yCentral);
////					firmaDocto = firmaDoctoService.save(firmaDocto);
//					logger.info("Coordenadas del espacio de firma:");
//					logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: "
//							+ widht + " height: " + height);
//					logger.info("Coordenadas del espacio de firma: ");
//					logger.info("x: " + (c[1] - acomodoX) + " y: " + ((ph - c[0]) + acomodoY) + " widht: " + widht
//							+ " height: " + height);
//					// Drawing a rectangle
//					contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
//				}
//
//			}
//
//			contentStream.setLineWidth(1);
//			contentStream.setNonStrokingColor(Color.WHITE);
//			contentStream.setStrokingColor(Color.BLACK);
//			contentStream.stroke();
//			// Drawing a rectangle
//			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//			// Closing the ContentStream object
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(archivo);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Obtener las coordenadas del archivo formato de domiliciacion
//	@GetMapping("/domiciliacion")
//	public ResponseEntity<?> obtenerCoordenadasDomiliciacion() throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//
//		// logger.info("RutaAbsoluta: " + rutaAbsoluta);
//		logger.info("Ejecutando obtenerCoordenadasDomiciliacion");
//		String keyWords = "RAZON";
//		String archivo = "C:/Users/osvaldo.escamilla/Documents/formato de domiciliacion.pdf";
//		// String pdfPath = url;
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, archivo);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(archivo);
//		PDDocument document = PDDocument.load(file);
//		// doctEnvioService.totalHojas(document.getNumberOfPages(),
//		// dEnvio.getPk_id_docto_envio());
//		// float arr [][];
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 25.00f;
//			float acomodoY = 5.00f;
//			float widht = 110.00f;
//			float height = 20.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//
//			logger.info("Coordenadas del espacio de firma: ");
//			float x1 = 61f;
//			float y1 = 84.3f;
//			float x2 = 84f;
//			float y2 = 87.3f;
//			int hoja = (int) c[2];
//			float xCentral = 68.3702f;
//			float yCentral = 34.4741f;
////			FirmaDocto firmaDocto = new FirmaDocto();
////			firmaDocto.setF_coord_firma_x1(x1);
////			firmaDocto.setF_coord_firma_x2(x2);
////			firmaDocto.setF_coord_firma_y1(y1);
////			firmaDocto.setF_coord_firma_y2(y2);
////			firmaDocto.setFk_id_docto_envio(dEnvio);
////			firmaDocto.setI_no_hoja(hoja);
////			firmaDocto.setF_coord_firma_xCentral(xCentral);
////			firmaDocto.setF_coord_firma_yCentral(yCentral);
////			firmaDocto = firmaDoctoService.save(firmaDocto);
//
//			logger.info("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
//			logger.info("Coordenadas para pintar el rectangulo -   x: " + (c[1] - acomodoX) + " y: "
//					+ ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: " + height);
//
//			// Drawing a rectangle
//			contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
//			// contentStream.addLine(x1, y1, x2, y2);
//			// contentStream.addLine(x1, y1, 500, 500);
//			// contentStream.lineTo(x2, y2);
//			contentStream.setLineWidth(2);
//			contentStream.setNonStrokingColor(Color.WHITE);
//			contentStream.setStrokingColor(Color.BLACK);
//			contentStream.stroke();
//			// Drawing a rectangle
//			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//			// Closing the ContentStream object
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(archivo);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Obtener las coordenadas del archivo formato de domiliciacion
	@GetMapping("/domiciliacion")
	public ResponseEntity<?> obtenerCoordenadasDomiliciacion(String url, DoctEnvio dEnvio, Documento docto)
			throws IOException {
		try {

			String pdfPath = url;
			// String pdfPath = "C:/Users/osvaldo.escamilla/Documents/formato de
			// domiciliacion.pdf";
			// transferencia electronica.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());

			// doctEnvioService.totalHojas(document.getNumberOfPages(), 32);

			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService
					.obtenerCoordenadaDocto(docto.getPk_id_documento());
			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(95);
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				for (int i = 0; i < coordenadas.size(); i++) {

					logger.info("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
					firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
					firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
					firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
					firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
					firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
					firmaDocto = firmaDoctoService.save(firmaDocto);
				}
				logger.info("Se obtuvieron correctamente las coordenadas del documento de formato de domiciliacion");
				return ResponseEntity
						.ok("Se obtuvieron correctamente las coordenadas del documento de formato de domiciliacion");
			} else {
				logger.info("Es nulo");
				logger.info("No contiene coordenadas el documento formato de domiciliacion");
				return ResponseEntity.ok("No contiene coordenadas formato de domiciliacion");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento de formato de domiciliacion");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento de formato de domiciliacion");
		}

	}

//	@GetMapping("/autoTransferencia1")
//	public ResponseEntity<?> obtenerCoordenadasAutorizacionTransferencia() throws IOException {
//
//		// PDDocument doc1 = PDDocument.load(file);
//
//		// logger.info("RutaAbsoluta: " + rutaAbsoluta);
//		logger.info("Ejecutando obtenerCoordenadasDomiliciacion");
//		String keyWords = "Firma";
//		String archivo = "C:/Users/osvaldo.escamilla/Documents/autorizacion de transferencia electronica.pdf";
//		// String pdfPath = url;
//		PdfBoxKeyWordPosition kwp = new PdfBoxKeyWordPosition(keyWords, archivo);
//		List<float[]> list = kwp.getCoordinate();
//		// float[] c = list.get(0);
//		File file = new File(archivo);
//		PDDocument document = PDDocument.load(file);
//		// doctEnvioService.totalHojas(document.getNumberOfPages(),
//		// dEnvio.getPk_id_docto_envio());
//		// float arr [][];
//		for (int i = 0; i < list.size(); i++) {
//			float[] c = list.get(i);
//
//			logger.info("Página" + c[2]);
//
//			logger.info("Coordenadas de la palabra clave (" + keyWords + "): x:" + c[1] + " , y: " + c[0]);
//
//			// Retrieving a page of the PDF Document
//			PDPage page = document.getPage((int) c[2] - 1);
//
//			float ph = page.getMediaBox().getUpperRightY();
//			float acomodoX = 60.00f;
//			float acomodoY = 10.00f;
//			float widht = 150.00f;
//			float height = 35.00f;
//
//			// Instantiating the PDPageContentStream class
//			PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
//
//			logger.info("Coordenadas del espacio de firma: ");
//			float x1 = 61f;
//			float y1 = 84.3f;
//			float x2 = 84f;
//			float y2 = 87.3f;
//			int hoja = (int) c[2];
//			float xCentral = 68.3702f;
//			float yCentral = 34.4741f;
////			FirmaDocto firmaDocto = new FirmaDocto();
////			firmaDocto.setF_coord_firma_x1(x1);
////			firmaDocto.setF_coord_firma_x2(x2);
////			firmaDocto.setF_coord_firma_y1(y1);
////			firmaDocto.setF_coord_firma_y2(y2);
////			firmaDocto.setFk_id_docto_envio(dEnvio);
////			firmaDocto.setI_no_hoja(hoja);
////			firmaDocto.setF_coord_firma_xCentral(xCentral);
////			firmaDocto.setF_coord_firma_yCentral(yCentral);
////			firmaDocto = firmaDoctoService.save(firmaDocto);
//
//			logger.info("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
//			logger.info("Coordenadas para pintar el rectangulo -   x: " + (c[1] - acomodoX) + " y: "
//					+ ((ph - c[0]) - acomodoY) + " widht: " + widht + " height: " + height);
//
//			// Drawing a rectangle
//			contentStream.addRect(c[1] - acomodoX, (ph - c[0]) + acomodoY, widht, height);
//			// contentStream.addLine(x1, y1, x2, y2);
//			// contentStream.addLine(x1, y1, 500, 500);
//			// contentStream.lineTo(x2, y2);
//			contentStream.setLineWidth(2);
//			contentStream.setNonStrokingColor(Color.WHITE);
//			contentStream.setStrokingColor(Color.BLACK);
//			contentStream.stroke();
//			// Drawing a rectangle
//			contentStream.fill();
//
//			logger.info("Se han agregado correctamente los espacios de firma");
//
//			// Closing the ContentStream object
//			contentStream.close();
//
//		}
//		// Saving the document
//		File file1 = new File(archivo);
//		document.save(file1);
//
//		// Closing the document
//		document.close();
//
//		return ResponseEntity.ok("Se obtuvieron las coordenadas");
//
//	}

	// Metodo para obtener las coordenadas del documento autorizacion de
	// transferencia
	@GetMapping("/autoTransferencia")
	public ResponseEntity<?> obtenerCoordenadasAutorizacionTransferencia(String url, DoctEnvio dEnvio,
			Documento docto) {

		try {

			String pdfPath = url;
			// String archivo = "C:/Users/osvaldo.escamilla/Documents/autorizacion de
			// transferencia electronica.pdf";
			File file = new File(pdfPath);
			PDDocument document = PDDocument.load(file);
			// Obtenemos el numero total de hojas del documento
			doctEnvioService.totalHojas(document.getNumberOfPages(), dEnvio.getPk_id_docto_envio());

			// doctEnvioService.totalHojas(document.getNumberOfPages(), 32);

			List<CoordenadaDocumento> coordenadas = coordenadaDocumentoService
					.obtenerCoordenadaDocto(docto.getPk_id_documento());
			// List<CoordenadaDocumento> coordenadas =
			// coordenadaDocumentoService.obtenerCoordenadaDocto(32);
			boolean isEmpty = coordenadas != null && !coordenadas.isEmpty();
			if (isEmpty) {
				for (int i = 0; i < coordenadas.size(); i++) {

					logger.info("Coordenadas en posicion: " + i + " = " + coordenadas.get(i).getPk_id_coord_doc());

					logger.info("Posicion X1: " + coordenadas.get(i).getF_x1());
					logger.info("Posicion X2: " + coordenadas.get(i).getF_x2());
					logger.info("Posicion Y1: " + coordenadas.get(i).getF_y1());
					logger.info("Posicion Y2: " + coordenadas.get(i).getF_y2());
					logger.info("Coordenada centro X: " + coordenadas.get(i).getF_centroX());
					logger.info("Coordenada centro Y: " + coordenadas.get(i).getF_centroY());
					logger.info("Pagina: " + coordenadas.get(i).getI_pagina());

					FirmaDocto firmaDocto = new FirmaDocto();
					firmaDocto.setF_coord_firma_x1(coordenadas.get(i).getF_x1());
					firmaDocto.setF_coord_firma_x2(coordenadas.get(i).getF_x2());
					firmaDocto.setF_coord_firma_y1(coordenadas.get(i).getF_y1());
					firmaDocto.setF_coord_firma_y2(coordenadas.get(i).getF_y2());
					firmaDocto.setFk_id_docto_envio(dEnvio);
					firmaDocto.setI_no_hoja(coordenadas.get(i).getI_pagina());
					firmaDocto.setF_coord_firma_xCentral(coordenadas.get(i).getF_centroX());
					firmaDocto.setF_coord_firma_yCentral(coordenadas.get(i).getF_centroY());
					firmaDocto = firmaDoctoService.save(firmaDocto);
				}
				
				logger.info("Se obtuvieron correctamente las coordenadas del documento de autorización de transferencia");
				return ResponseEntity
						.ok("Se obtuvieron correctamente las coordenadas del documento de autorización de transferencia");
				
			} else {
				logger.info("Es nulo");
				logger.info("No contiene coordenadas el documento de autorización de transferencia");
				return ResponseEntity.ok("No contiene coordenadas el documento de autorización de transferencia");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Error al obtener las coordenadas del documento de autorización de transferencia");
			logger.error(e);
			return ResponseEntity.ok("Error al obtener las coordenadas del documento de autorización de transferencia");
		}
		
	}

}
