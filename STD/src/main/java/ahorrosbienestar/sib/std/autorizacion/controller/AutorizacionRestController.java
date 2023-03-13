package ahorrosbienestar.sib.std.autorizacion.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.histo_notificacion.service.HistoricoNotificacionService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AutorizacionRestController {

	// variable ruta de almacenamiento
	private final static int URL_RUTA_ALMACENAMIENTO = 14;

	// variable estatus de autorizar
	private final static int ESTATUS_AUTORIZAR = 6;

	// variable estatus de rechazar
	private final static int ESTATUS_RECHAZAR = 7;

	// variable nombre de la carpeta de descargas
	private final static String NOMBRE_CARPETA_DESCARGAS = "DESCARGAS";

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private DoctEnvioService doctEnvioService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;

	@Autowired
	private ConfigService configService;
	
	private static final int BUFFER_SIZE = 4096;

	// Metodo para autorizar el estatus de una solicitud a autorizado/rechazado
	@RequestMapping(value = { "/autorizar_la_solicitud" }, method = RequestMethod.GET)
	public HttpStatus cambiarEstatusSolicitud(@RequestParam("tipoEstatus") String tipoEstatus,
			@RequestParam("envioID") Integer envioID, @RequestParam("observaciones") String observaciones)
			throws ParseException {

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Crear Fecha
		Date diaActual = new Date();

		// Formato fecha
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

		// Fecha actual
		String fecha = formatoFecha.format(diaActual);
		Date fechaActual = formatoFecha.parse(fecha);
		LocalDate fechaLocal = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Formato para hora
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		// Hora actual
		String horaActual = formatoHora.format(diaActual);

		// Solicitud envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);

		// si es autorizar
		if (tipoEstatus.equals("autorizar")) {

			// Seteamos datos de la solicitud de envio
			solicitudEnvio.setI_estatus(ESTATUS_AUTORIZAR);
			solicitudEnvio.setVc_obser_autoriza(observaciones);
			solicitudEnvio.setFk_usr_autoriza(usuario);
			solicitudEnvio.setDt_fecha_autorizacion(fechaLocal);
			solicitudEnvioService.save(solicitudEnvio);

			// Seteamos datos de los documentos de envio
			doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
				doct.setI_estatus(ESTATUS_AUTORIZAR);
				doctEnvioService.save(doct);
			});

			// si es rechazar
		} else if (tipoEstatus.equals("rechazar")) {

			// Seteamos datos de la solicitud de envio
			solicitudEnvio.setI_estatus(ESTATUS_RECHAZAR);
			solicitudEnvio.setVc_obser_cancelacion(observaciones);
			solicitudEnvio.setFk_usr_cancela(usuario);
			solicitudEnvio.setDt_fecha_cancelacion(fechaLocal);
			solicitudEnvio.setVc_hora_cancelacion(horaActual);
			solicitudEnvioService.save(solicitudEnvio);

			// Seteamos datos de los documentos de envio
			doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
				doct.setI_estatus(ESTATUS_RECHAZAR);
				doct.setPk_usr_cancela(usuario);
				doct.setDt_fecha_cancelacion(fechaLocal);
				doctEnvioService.save(doct);
			});

		}

		// Llamamos el método para enviar el correo
		agregarNotificacion(tipoEstatus, fechaLocal, horaActual, envioID, observaciones);

		return HttpStatus.OK;
	}

	// Método que manda notificacion al correo
	public void agregarNotificacion(String tipo, LocalDate fecha, String hora, Integer envioID, String motivo) {

		HistoricoNotificacion model = new HistoricoNotificacion();

		model.setDt_fecha_registro(fecha);
		model.setVc_hora_registro(hora);
		// Seteamos el modelo de la solicitud de envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);
		model.setFk_solicitud_envio(solicitudEnvio);

		if (tipo.equals("autorizar"))
			model.setVc_mensaje("Se ha autorizado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);
		else if (tipo.equals("rechazar"))
			model.setVc_mensaje("Se ha rechazado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);

		historicoNotificacionService.save(model);
	}

	// Método para mover los archivos a la carpeta correspondiente
	public void moverArchivosDescarga(Integer dEnvioID) throws IOException {

		/*
		 * Path temp = Files.move( Paths.get(
		 * "C:\\Users\\jose.corona\\Documents\\Documentos_Imp\\Actividades_DES-22-0015(27-01-23).docx"
		 * ), Paths.get(
		 * "C:\\Users\\jose.corona\\Documents\\STD\\Descargas\\Actividades_DES-22-0015(27-01-23).docx"
		 * ));
		 * 
		 * if (temp != null) { System.out.println("El archivo se movió correctamente");
		 * } else { System.out.println("Hubo un error al mover el archivo"); }
		 */

		/* --------------------------------------------------------------- */

		// Obtener configuración
		Configuracion configuracionModel = configService.obtenerConfiguracion(URL_RUTA_ALMACENAMIENTO);

		// Obtener solicitud de envio
		SolicitudEnvio solicitudEnvioModel = solicitudEnvioService.obtenerSolicitudEnvio(dEnvioID);

		// Aquí hace referencia al destino donde se van a mover los archivos
		String destino = configuracionModel.getVc_valor_propiedad() + "/"
				+ solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "/"
				+ solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "-"
				+ solicitudEnvioModel.getPk_id_solicitud_envio() + "-"
				+ solicitudEnvioModel.getI_identificador_ruta() + "/";

		// Se recupera los documentos que pertenecen a la solicitud de envio
		doctEnvioService.listaDocumentosPorSolicitudEnvio(dEnvioID).forEach(doct -> {
			
			try {

				// Obtener el nombre del archivo
				URL origen = new URL(doct.getVc_ruta_docto());
				
				// Aquí realiza el cambio entre carpetas/ubicación
				Path cambioDeCarpeta = Files.move(Paths.get(doct.getVc_ruta_docto()), Paths.get(destino + origen.getFile()));

				// Se notifica si realizo el cambio
				if (cambioDeCarpeta != null)
					System.out.println("El archivo se movió correctamente");
				else
					System.out.println("Hubo un error al mover el archivo");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	// Se crea la carpeta de DESCARGA
	public void crearCarpetaDescarga() {

		// Aqui se define la ubicación de la carpeta de DESCARGAS
		// File directorio = new
		// File("C:\\Users\\jose.corona\\Documents\\STD\\Descargas\\carpetaNueva");

		// Obtener configuración
		Configuracion configuracionModel = configService.obtenerConfiguracion(URL_RUTA_ALMACENAMIENTO);

		// Aqui se define la ubicación de la carpeta de DESCARGAS
		File directorio = new File(configuracionModel.getVc_valor_propiedad() + NOMBRE_CARPETA_DESCARGAS);

		// Pregunta si existe la carpeta
		if (!directorio.exists()) {
			// Pregunta si se puede realizar la acción
			if (directorio.mkdirs()) {
				// Se crea la carpeta
				System.out.println("Directorio creado");
			} else {
				// Hubo un error
				System.out.println("Error al crear directorio");
			}
		}
	}
	
	// Método para obtener el archivo .zip
	public void unzip(String archivoZipPath, String destinoPath) throws IOException {
		File Destination_Directory = new File(destinoPath);
		if (!Destination_Directory.exists()) {
			Destination_Directory.mkdir();
		}
		ZipInputStream Zip_Input_Stream = new ZipInputStream(new FileInputStream(archivoZipPath));
		ZipEntry Zip_Entry = Zip_Input_Stream.getNextEntry();

		while (Zip_Entry != null) {
			String File_Path = destinoPath + File.separator + Zip_Entry.getName();
			if (!Zip_Entry.isDirectory()) {

				extractFile(Zip_Input_Stream, File_Path);
			} else {

				File directory = new File(File_Path);
				directory.mkdirs();
			}
			Zip_Input_Stream.closeEntry();
			Zip_Entry = Zip_Input_Stream.getNextEntry();
		}
		Zip_Input_Stream.close();
	}

	// Método para extaer los archivos del zip
	private static void extractFile(ZipInputStream Zip_Input_Stream, String File_Path) throws IOException {
		BufferedOutputStream Buffered_Output_Stream = new BufferedOutputStream(new FileOutputStream(File_Path));
		byte[] Bytes = new byte[BUFFER_SIZE];
		int Read_Byte = 0;
		while ((Read_Byte = Zip_Input_Stream.read(Bytes)) != -1) {
			Buffered_Output_Stream.write(Bytes, 0, Read_Byte);
		}
		Buffered_Output_Stream.close();
	}
	
	// autorizacionRestController.unzip("C:\\Users\\jose.corona\\Documents\\JACS-Documentos\\detail.zip", "C:\\Users\\jose.corona\\Documents\\JACS-Documentos\\Bitacora 2023");

}
