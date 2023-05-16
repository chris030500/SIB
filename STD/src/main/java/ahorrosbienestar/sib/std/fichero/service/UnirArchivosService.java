package ahorrosbienestar.sib.std.fichero.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;
import ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.model.ObtenerCoordenadas;
import ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.service.ObtenerCoordenadasJDBCService;
import ahorrosbienestar.sib.std.docto_unificado.service.DoctoUnificadoService;
import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.service.FirmaDoctoUnificadoService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class UnirArchivosService {

	@Autowired
	private ObtenerCoordenadasJDBCService obtenerCoordenadasJDBCService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DoctoUnificadoService doctoUnificadoService;

	@Autowired
	private FirmaDoctoUnificadoService firmaDoctoUnificadoService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public String eliminarUltimaPalabra(String s) {
		int pos;
		s = s.trim();
		pos = s.lastIndexOf("/");
		if (pos != -1) {
			s = s.substring(0, pos);
		} else {
			s = "";
		}
		return s;
	}

	@Transactional
	public String guardarArchivoPDF(int solicitudEnvio) throws Exception {
		DoctoUnificado doctoUnificado = new DoctoUnificado();
		System.out.println("SolicitudEnvio: " + solicitudEnvio);
		int operación = 0;
		List<ObtenerCoordenadas> documentos = obtenerCoordenadasJDBCService.obtenerInfoCoordenadas(solicitudEnvio,
				operación);
		System.out.println("Tamaño de la lista: " + documentos.size());

		String ruta = documentos.get(0).getVc_ruta_docto();

		String rutaArchivoFinal = eliminarUltimaPalabra(ruta);
		System.out.println("Ruta para Archivo final:" + rutaArchivoFinal);
		String nombreArchivoFinal = configService.findAll().get(34).getVc_valor_propiedad().toString();
		String nombreArchivoXML = configService.findAll().get(44).getVc_valor_propiedad().toString();
		String rutaConfig = configService.findAll().get(13).getVc_valor_propiedad().toString();
		System.out.println("RutaConfig: " + rutaConfig);
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		String rutaArchivo = rutaArchivoFinal;
		//String rutaArchivo = rutaArchivoFinal.replaceAll(rutaConfig.toString(), "");
		System.out.println("RutaArchivo: " + rutaArchivo);
		// System.out.println("rutaArchivo: " + ("/"+ rutaArchivo+ "/" +
		// nombreArchivoFinal));
		// path de destino del archivo generado
		pdfMerger.setDestinationFileName(rutaConfig + rutaArchivoFinal + "/" + nombreArchivoFinal);

		try {
			String[] archivo = new String[documentos.size()];
			for (int i = 0; i < documentos.size(); i++) {
				archivo[i] = rutaConfig + documentos.get(i).getVc_ruta_docto();
				pdfMerger.addSource(archivo[i]);
				// System.out.println("Datos coordenadas: " +
				// documentos.get(i).getVc_ruta_docto());
			}

			pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
			// Cargamos el archivo final que ya tiene cargados todos los documentos
			File file = new File(rutaConfig + rutaArchivoFinal + "/" + nombreArchivoFinal);
			PDDocument document = PDDocument.load(file);
			// Declaramos que agregamos un pagina nueva en blanco
			PDPage page = new PDPage();
			document.addPage(page);

			// Guardamos los cambios del documento.
			document.save(file);
			document.close();

			// Lista para obtener las coordenadas de los documentos
			List<ObtenerCoordenadas> obtenerCoordDocts = obtenerCoordenadasJDBCService
					.obtenerInfoCoordenadas(solicitudEnvio, 1);

			int tamanoObtenerCoordDocts = obtenerCoordDocts.size();

			System.out.println("Número de las coordenadas de la lista");
			System.out.println("tamanoObtenerCoordDocts: " + tamanoObtenerCoordDocts);
			System.out.println("documentos" + documentos.size());
			int totalHojas = 1;

			for (int i = 0; i < documentos.size(); i++) {
				totalHojas += documentos.get(i).getNo_hoja();
			}

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

			// Guardamos los documentos unificados

			doctoUnificado = new DoctoUnificado();

			SolicitudEnvio sEnvio = solicitudEnvioService.findById(solicitudEnvio).get();
			doctoUnificado.setFk_id_solicitud_envio(sEnvio);
			doctoUnificado.setI_total_hojas(totalHojas);
			doctoUnificado.setI_estatus(1);
			doctoUnificado.setVc_ruta_archivo_pdf((rutaArchivo + "/" + nombreArchivoFinal));
			doctoUnificado.setVc_ruta_archivo_xml((rutaArchivo + "/" + nombreArchivoXML));
			doctoUnificado.setPk_usr_registra(usu);
			doctoUnificado.setDt_fecha_envio(localDateHoy);

			doctoUnificadoService.save(doctoUnificado);

//			System.out.println("Solicitud Envio: " + sEnvio.getPk_id_solicitud_envio());
//			System.out.println("TotalHojas: " + totalHojas);
//			System.out.println("Ruta archivo PDF: " + (rutaArchivo + "/" + nombreArchivoFinal));
//			System.out.println("Ruta archivo PDF: " + (rutaArchivo + "/" + nombreArchivoXML));
//			System.out.println("Estatus: " + 1);
//			System.out.println("Usuario: " + usu.getPk_id_usuario());
//			System.out.println("Fecha de envio: " + localDateHoy);

			int totalHojas2 = 0;
			for (int i = 0; i < documentos.size(); i++) {
				totalHojas2 += documentos.get(i).getNo_hoja();
				int auxIdDoctoEnvio = documentos.get(i).getPk_id_docto_envio();
				System.out.println("Docto envio:" + auxIdDoctoEnvio);
				System.out.println("Total hojas: " + totalHojas2);
				for (int j = 0; j < tamanoObtenerCoordDocts; j++) {
					if (auxIdDoctoEnvio == obtenerCoordDocts.get(j).getPk_id_docto_envio()) {

						int tHojas = obtenerCoordDocts.get(j).getTotal_hojas();
						int noHoja = obtenerCoordDocts.get(j).getNo_hoja();
						float x1 = obtenerCoordDocts.get(j).getCoordenada_x1();
						float x2 = obtenerCoordDocts.get(j).getCoordenada_x2();
						float y1 = obtenerCoordDocts.get(j).getCoordenada_y1();
						float y2 = obtenerCoordDocts.get(j).getCoordenada_y2();
						float xCentral = obtenerCoordDocts.get(j).getCoordenada_xCentral();
						float yCentral = obtenerCoordDocts.get(j).getCoordenada_yCentral();
						int hojaFirmaDoctUnificado = ((totalHojas2 - tHojas) + noHoja);

						// Guardamos las coordenadas de las paginas del documento unificado

						FirmaDoctoUnificado fDoctoUnificado = new FirmaDoctoUnificado();
						fDoctoUnificado.setFk_id_docto_unificado(doctoUnificado);
						fDoctoUnificado.setI_no_hoja(hojaFirmaDoctUnificado);
						fDoctoUnificado.setF_coord_firma_xCentral(xCentral);
						fDoctoUnificado.setF_coord_firma_yCentral(yCentral);
						fDoctoUnificado.setF_coord_firma_x1(x1);
						fDoctoUnificado.setF_coord_firma_x2(x2);
						fDoctoUnificado.setF_coord_firma_y1(y1);
						fDoctoUnificado.setF_coord_firma_y2(y2);

						firmaDoctoUnificadoService.save(fDoctoUnificado);

//						System.out.println("Firma docto: " + obtenerCoordDocts.get(j).getId_firma_docto());
//						System.out.println("x1: " + x1);
//						System.out.println("x2: " + x2);
//						System.out.println("y1: " + y1);
//						System.out.println("y2: " + y2);
//						System.out.println("xCentral: " + xCentral);
//						System.out.println("yCentral: " + yCentral);
						// System.out.println("No Hoja: " + obtenerCoordDocts.get(j).getNo_hoja());
						// System.out.println("Total hojas en segundo: " +
						// obtenerCoordDocts.get(j).getTotal_hojas());
						System.out.println("Hoja firma doc concatenado = " + hojaFirmaDoctUnificado);
//						int sumaHojas = auxIdDoctoEnvio + obtenerCoordDocts.get(j).getTotal_hojas();
//						System.out.println("Suma Hojas: " + sumaHojas);

					}
				}
				System.out.println("---------------------------------------");
			}

			String xCentralHF = configService.findAll().get(36).getVc_valor_propiedad();
			String yCentralHF = configService.findAll().get(37).getVc_valor_propiedad();
			String x1HF = configService.findAll().get(38).getVc_valor_propiedad();
			String x2HF = configService.findAll().get(39).getVc_valor_propiedad();
			String y1HF = configService.findAll().get(40).getVc_valor_propiedad();
			String y2HF = configService.findAll().get(41).getVc_valor_propiedad();
			System.out.println("HojaFinal: ");
			FirmaDoctoUnificado fDoctoUnificado = new FirmaDoctoUnificado();
			fDoctoUnificado.setFk_id_docto_unificado(doctoUnificado);
			fDoctoUnificado.setI_no_hoja(totalHojas);
			fDoctoUnificado.setF_coord_firma_xCentral(Float.parseFloat(xCentralHF)); // Lo consulta en BD
			fDoctoUnificado.setF_coord_firma_yCentral(Float.parseFloat(yCentralHF)); // Lo consulta en BD
			fDoctoUnificado.setF_coord_firma_x1(Float.parseFloat(x1HF)); // Lo consulta en BD
			fDoctoUnificado.setF_coord_firma_x2(Float.parseFloat(x2HF)); // Lo consulta en BD
			fDoctoUnificado.setF_coord_firma_y1(Float.parseFloat(y1HF)); // Lo consulta en BD
			fDoctoUnificado.setF_coord_firma_y2(Float.parseFloat(y2HF)); // Lo consulta en BD
			firmaDoctoUnificadoService.save(fDoctoUnificado);
//			System.out.println("xCentral: " + Float.parseFloat(xCentralHF));
//			System.out.println("yCentral: " + Float.parseFloat(yCentralHF));
//			System.out.println("x1: " + Float.parseFloat(x1HF));
//			System.out.println("x2: " + Float.parseFloat(x2HF));
//			System.out.println("y1: " + Float.parseFloat(y1HF));
//			System.out.println("y2: " + Float.parseFloat(y2HF));
//			System.out.println("TotalHojas:" + totalHojas);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Error al unir los archivos. Error: " + e.getMessage());
			e.printStackTrace();
			return "Error al unir los archivos.";
		}

		return "1";
	}
}
