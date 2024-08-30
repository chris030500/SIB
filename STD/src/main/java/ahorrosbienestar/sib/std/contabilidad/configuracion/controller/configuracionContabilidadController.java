package ahorrosbienestar.sib.std.contabilidad.configuracion.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.service.ArchivosFoliosService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ArchivoOfertasAutomaticas;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class configuracionContabilidadController {

	private static final Logger logger = Logger.getLogger(configuracionContabilidadController.class);

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private ArchivosFoliosService archivosFoliosService;

	List<Usuario> usuario = null;

	private String rutaDireccion = "";

	@GetMapping("/parametrosConfiguracion")
	public String parametrosConfiguracion(Model model) throws IOException {
		rutaDireccion = Constantes.CONTABILIDAD_CONFIGURACION;
		try {
			if (validarAcceso(Constantes.CONTABILIDAD_CONFIGURACION) == 1) {

			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}

	}

	public int datosArchivo(String urlArchivo, String idFolio, String nombreArchivo) {
		Integer estatus = 1;
		Integer respuesta = 0;
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			LocalDate fecha = LocalDate.now();
			ArchivosFolios alta = new ArchivosFolios();
			alta.setI_folio(Integer.parseInt(idFolio));
			alta.setDt_fecha_registro(fecha);
			alta.setI_estatus(estatus);
			alta.setVc_ruta_docto(urlArchivo);
			alta.setVc_nombre_docto(nombreArchivo);
			alta.setI_empleado_registro(usuario.get(0).getI_id_empleado());

			ArchivosFolios registro = archivosFoliosService.registrar(alta);

			if (registro.getPk_id_archivo() > 0) {
				respuesta = 1;
			} else {
				respuesta = 0;
			}
			return respuesta;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}
	}

	@GetMapping
	public String UrlPDFServidor() {
		String url = configService.obtenerUrlCSO().get(0).getVc_valor_propiedad();
		return url.toString();
	}

	@PostMapping("/subirArchivo")
	public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files,
			@RequestParam("idFolio") String idFolio) {

		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();
		List<Configuracion> bases = configService.obtenerUrlLayout();
		try {
			if (files.length > 0) {
				for (MultipartFile file : files) {
					byte[] bytes = file.getBytes();
					String folderPath = bases.get(0).getVc_valor_propiedad();
					Path folder = Paths.get(folderPath);

					Path nuevoPath = Paths.get(folderPath + "/" + file.getOriginalFilename());
					Files.write(nuevoPath, bytes);

					mensaje = "Se agrego archivo correctamente.";
					logger.info(mensaje);
				}
			} else {
				mensaje = "No subio archivo.";
				logger.info(mensaje);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		}
	}

	@GetMapping("/listaArchivos")
	public ResponseEntity<List<ArchivoOfertasAutomaticas>> listFilesInFolder() throws IOException {

		try {
			List<Configuracion> bases = configService.obtenerUrlLayout();
			String folderPath = bases.get(0).getVc_valor_propiedad();
			Path folder = Paths.get(folderPath);

			List<ArchivoOfertasAutomaticas> listaArchivos = new ArrayList<>();

			// Listar los archivos en la carpeta
			Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					ArchivoOfertasAutomaticas archivos = new ArchivoOfertasAutomaticas();
					archivos.setNombreArchivo(file.getFileName().toString());
					archivos.setPesoArchivo((attrs.size() / 1024) + " KB");

					logger.info("Nombre:" + archivos.getNombreArchivo());
					logger.info("tamaño:" + archivos.getPesoArchivo());
					long millis = attrs.creationTime().toMillis();
					FileTime fileTime = FileTime.fromMillis(millis);
					LocalDate fechaCreacion = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					String fechaFormateada = fechaCreacion.toString();
					archivos.setFechaCreacion(fechaFormateada);

					long fechaModificacion = attrs.lastModifiedTime().toMillis();
					FileTime fileTimeMod = FileTime.fromMillis(fechaModificacion);
					LocalDate fechaMod = fileTimeMod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					String fechaFormateadaMod = fechaMod.toString();
					archivos.setFechaModifacion(fechaFormateadaMod);

					logger.info("fecha de creacion:" + fechaFormateada);
					logger.info("fecha de Modificacion:" + fechaFormateadaMod);
					archivos.setRutaArchivo(bases.get(0).getVc_valor_propiedad() + file.getFileName().toString());
					listaArchivos.add(archivos);
					return FileVisitResult.CONTINUE;
				}
			});
			return ResponseEntity.ok(listaArchivos);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}
	}

	public int validarAcceso(String rutaController) {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}

	}
}
