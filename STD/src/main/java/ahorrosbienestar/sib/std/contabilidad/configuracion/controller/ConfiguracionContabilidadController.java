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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.service.ArchivosFoliosService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ArchivoOfertasAutomaticas;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
 
@Controller
@RequestMapping("/contabilidad")
public class ConfiguracionContabilidadController {
 
    private static final Logger logger = Logger.getLogger(ConfiguracionContabilidadController.class);
 
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

    @Autowired
	JdbcTemplate jdbcTemplate;
 
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
        List<Configuracion> bases = configService.obtenerUrlFactura();
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
            List<Configuracion> bases = configService.obtenerUrlFactura();
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
    
    public int obtUsuario() {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return usuario.get(0).getPk_id_usuario();

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}

	}
    
    @PostMapping("/procesarArchivoFactura")
	public ResponseEntity<Map<String, Object>> procesarArchivo(@RequestBody Map<String, String> request) {
		String nombreArchivo = request.get("nombreArchivo");

		try {
			// Aquí agregas la lógica para procesar el archivo
			procesarYMigrarArchivo(nombreArchivo);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("error", "Error al procesar el archivo: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

    public void procesarYMigrarArchivo(String nombreArchivo) throws IOException {

        try {
            List<Object[]> dataToInsert = new ArrayList<>();

            // Inicializa el DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Carga el archivo XML
            File file = new File("\\\\10.150.102.63\\std\\Facturas\\" + nombreArchivo);
            Document document = builder.parse(file);

            // Normaliza el documento (remueve nodos vacíos y espacios)
            document.getDocumentElement().normalize();

            // Obtiene el elemento raíz
            Element root = document.getDocumentElement();
            System.out.println("Elemento raíz: " + root.getNodeName());

            // Obtiene los nodos del "Comprobante"
            NodeList comprobanteList = document.getElementsByTagName("cfdi:Comprobante");

            // Itera sobre los nodos de "Comprobante"
            for (int i = 0; i < comprobanteList.getLength(); i++) {
                Node comprobanteNode = comprobanteList.item(i);

                if (comprobanteNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element comprobanteElement = (Element) comprobanteNode;

                    String folio = comprobanteElement.getAttribute("Folio");
                    String fecha = comprobanteElement.getAttribute("Fecha");
                    String descuento = comprobanteElement.getAttribute("Descuento");
                    String total = comprobanteElement.getAttribute("Total");

                    // Emisor
                    Element emisorElement = (Element) comprobanteElement.getElementsByTagName("cfdi:Emisor").item(0);
                    String emisorRFC = emisorElement.getAttribute("Rfc");
                    String emisorNombre = emisorElement.getAttribute("Nombre");

                    // Receptor
                    Element receptorElement = (Element) comprobanteElement.getElementsByTagName("cfdi:Receptor").item(0);
                    String receptorRFC = receptorElement.getAttribute("Rfc");
                    String receptorNombre = receptorElement.getAttribute("Nombre");
                    
                    System.out.print(folio);

                    // Insertar en TBL_DETALLE_FACTURA
                    String sql1 = "INSERT INTO TBL_DETALLE_FACTURA (PK_I_Folio, DT_Fecha_Emision, DC_Descuento, DC_Total, VC_Emisor_RFC, VC_Emisor_Nombre, VC_Receptor_RFC, VC_Receptor_Nombre) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    jdbcTemplate.update(sql1, folio, fecha, descuento, total, emisorRFC, emisorNombre, receptorRFC, receptorNombre);

                    // Lee los conceptos
                    NodeList conceptosList = comprobanteElement.getElementsByTagName("cfdi:Concepto");
                    for (int j = 0; j < conceptosList.getLength(); j++) {
                        Element conceptoElement = (Element) conceptosList.item(j);
                        String concepto = conceptoElement.getAttribute("NoIdentificacion");
                        String cantidad = conceptoElement.getAttribute("Cantidad");
                        String descripcion = conceptoElement.getAttribute("Descripcion");
                        String oi = conceptoElement.getAttribute("ObjetoImp");
                        String um = conceptoElement.getAttribute("ClaveUnidad");
                        String punit = conceptoElement.getAttribute("ValorUnitario");
                        String subtotalStr = conceptoElement.getAttribute("Importe");
                        String descStr = conceptoElement.getAttribute("Descuento");

                        // Convertir las cadenas a números
                        double subtotal = Double.parseDouble(subtotalStr.isEmpty() ? "0.00" : subtotalStr);
                        double desc = descStr.isEmpty() ? 0.00 : Double.parseDouble(descStr);

                        // Realizar la resta
                        double totalConcepto = subtotal - desc;

                        // Agregar a la lista para TBL_FACTURA
                        dataToInsert.add(new Object[] {cantidad, concepto, descripcion, oi, um, punit, subtotal, desc, totalConcepto, folio});
                    }
                }
            }

            // Insertar en TBL_FACTURA usando batchUpdate
            String sql2 = "INSERT INTO TBL_FACTURA (DC_Cantidad, VC_Concepto, VC_Descripcion, VC_Oi, VC_Um, DC_Precio_Unit, DC_Sub_Total, DC_Descuento, DC_Total, FK_Folio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql2, dataToInsert);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping("/cargarPdf")
    public ResponseEntity<Map<String, String>> uploadPdf(@RequestParam("pdf") MultipartFile file,
                                                        @RequestParam("nombreArchivo") String nombreArchivo) {

        String UPLOAD_DIR = "\\\\10.150.102.63\\std\\PDF\\";
        
        // Si el nombre del archivo termina en .xml, lo cambiamos a .pdf
        if (nombreArchivo.toLowerCase().endsWith(".xml")) {
            nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf(".")) + ".pdf";
        }

        System.out.println("Subiendo archivo: " + nombreArchivo);
        System.out.println("Ruta destino: " + UPLOAD_DIR);

        if (file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El archivo debe ser un PDF.");
            System.out.println("Error: archivo vacío o no es PDF.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            // Guardar el archivo directamente en la ruta especificada
            Path filePath = Paths.get(UPLOAD_DIR).resolve(nombreArchivo);
            System.out.println("Guardando archivo en: " + filePath.toString());
            file.transferTo(filePath);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Archivo PDF cargado exitosamente.");
            return ResponseEntity.ok().body(successResponse);
            
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al cargar el archivo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/verPdf")
    public ResponseEntity<Resource> viewPdf(@RequestParam("nombreArchivo") String nombreArchivo) {
    	
    	String UPLOAD_DIR = "\\\\10.150.102.63\\std\\PDF\\";
    	
    	// Si el nombre del archivo termina en .xml, lo cambiamos a .pdf
        if (nombreArchivo.toLowerCase().endsWith(".xml")) {
            nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf(".")) + ".pdf";
        }
    	
        try {
            // Crear la ruta completa al archivo PDF
            Path filePath = Paths.get(UPLOAD_DIR).resolve(nombreArchivo).normalize();
            System.out.print(filePath);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.print(resource);

            if (resource.exists() && resource.isReadable()) {
                // Establecer el encabezado de respuesta para mostrar el archivo en el navegador
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            } else {
                // Archivo no encontrado
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Error al manejar el archivo
            return ResponseEntity.internalServerError().build();
        }
    }

}