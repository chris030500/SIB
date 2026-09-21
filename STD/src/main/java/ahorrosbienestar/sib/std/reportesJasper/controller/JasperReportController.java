package ahorrosbienestar.sib.std.reportesJasper.controller;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.reportesJasper.component.Desencriptar;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;

@CrossOrigin
@RestController
@RequestMapping("/exportarReporte")

public class JasperReportController {
	
	@Autowired
	private Desencriptar desencriptar;
	
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	
	
    //protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(JasperReportController.class);
	
	
	private static final String jaspersserver_url = "jaspersserver_url";
	private static final String jaspersserver_username = "jaspersserver_username";
	private static final String jaspersserver_password = "jaspersserver_password";
	
    private String extPDF = ".pdf";
    private String extXLS = ".xls";
    private String extXLSX = ".xlsx";
    
    
    
    
	@SuppressWarnings("deprecation")
	@RequestMapping("generarReporte")
    public ResponseEntity<Resource> reporteJasper(@RequestParam(name = "idReporte") Integer idReporte, @RequestParam(name = "parametros") String parametros, @RequestParam(name = "tipoDocumento") String tipoDocumento, @RequestParam(name = "identificadorDoc") String identificadorDoc) {
   	 
    	try {
    		/**
    		 * 
    		 * para el campo parametros si envian mas de 1 parametro usar el caracter "~" para dividir los parametros ejemplo: FECHA_INICIO=2023/03/01~FECHA_FIN=2023/03/05
						
    		 */
    					logger.info("datos:IDreporte"+idReporte+"parametros:"+parametros+"tipoDocumento:"+tipoDocumento+"identificadorDoc:"+identificadorDoc);
						ReporteJasper report = iReporteRepository.getById(idReporte);
    				
    					String urlGenerarReporte = iConfiguracionRepository.buscarPropiedad(jaspersserver_url)+report.getVc_url_reporte()+tipoDocumento+parametros.replace("~", "&");
    					
    					logger.info("url:"+urlGenerarReporte);
    				
    					
    					HttpHeaders headers = new HttpHeaders();
    				    headers = createHeaders(iConfiguracionRepository.buscarPropiedad(jaspersserver_username), desencriptar.decrypt(iConfiguracionRepository.buscarPropiedad(jaspersserver_password)));
    				    		//configService.findAll().get(16).getVc_valor_propiedad());
    					headers.set("Content-Type",report.getVc_tipo_contenido());
    					headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM));
      					
    					   					
    					// Request Body
    					MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    					// Request Entity
    					HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    					
    					// RestTemplate
    					RestTemplate restTemplate = new RestTemplate();

    					// DELETE cancelar
    					ResponseEntity<byte[]> response = restTemplate.exchange(urlGenerarReporte, HttpMethod.GET, requestEntity, byte[].class);

    					// ResponseBody
    					byte[] responseBody = response.getBody();
    					
    					//exportar reporte 
    					//String fechaCreacion = null;
    					StringBuilder stringBuilder = null;
    					ContentDisposition contentDisposition = null;
    					HttpHeaders h2 = new HttpHeaders();
                        MediaType mediaType = MediaType.APPLICATION_PDF;
    					if(extPDF.equalsIgnoreCase(tipoDocumento)) {
    						//fecha de creacion
        					//fechaCreacion = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        				    stringBuilder = new StringBuilder().append(report.getVc_nombre_reporte());
        				    contentDisposition = ContentDisposition.builder("inline")
        				    .filename(stringBuilder.append("_")
        				    .append(identificadorDoc)
        				    .append(tipoDocumento)
        				    .toString())
        				    .build();
        				    h2.setContentDisposition(contentDisposition);
        				    mediaType = MediaType.APPLICATION_PDF;
    					}else if(extXLS.equalsIgnoreCase(tipoDocumento)) {
    						//fechaCreacion = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        				    stringBuilder = new StringBuilder().append(report.getVc_nombre_reporte());
        				    contentDisposition = ContentDisposition.builder("attachment")
        				    .filename(stringBuilder.append("_")
        				    .append(identificadorDoc)
        				    .append(tipoDocumento)
        				    .toString())
        				    .build();
        				    h2.setContentDisposition(contentDisposition);
        				    mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
    					} else if (extXLSX.equalsIgnoreCase(tipoDocumento)) {
    						stringBuilder = new StringBuilder().append(report.getVc_nombre_reporte());
    			            contentDisposition = ContentDisposition.builder("attachment")
    			                    .filename(stringBuilder.append("_")
    			                            .append(identificadorDoc)
    			                            .append(tipoDocumento)
    			                            .toString())
    			                    .build();
    			            h2.setContentDisposition(contentDisposition);
    			            mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    			        }
    					
    				    return ResponseEntity.ok().contentLength((long) responseBody.length)
    				    .contentType(mediaType)
    				    .headers(h2).body(new ByteArrayResource(responseBody));
    					    		
    	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
    }
	
	@SuppressWarnings("deprecation")
	@RequestMapping("generarArchivoRuta")
	@Transactional
    public ResponseEntity<Resource> generarArchivoRuta(
    			@RequestParam(name = "idReporte") Integer idReporte
    			, @RequestParam(name = "parametros") String parametros
    			, @RequestParam(name = "tipoDocumento") String tipoDocumento
    			, @RequestParam(name = "identificadorDoc") String identificadorDoc
    			, @RequestParam(name = "rutaCreacion") String rutaCreacion) {
   	 
    	try {
    		/**
    		 * 
    		 * para el campo parametros si envian mas de 1 parametro usar el caracter "~" para dividir los parametros ejemplo: FECHA_INICIO=2023/03/01~FECHA_FIN=2023/03/05
						
    		 */
    					logger.info("datos:IDreporte"+idReporte+"parametros:"+parametros+"tipoDocumento:"+tipoDocumento+"identificadorDoc:"+identificadorDoc);
						ReporteJasper report = iReporteRepository.getById(idReporte);
    				
						try {
							String urlGenerarReporte = iConfiguracionRepository.buscarPropiedad(jaspersserver_url)+report.getVc_url_reporte()+tipoDocumento+parametros.replace("~", "&");
							
							logger.info("url:"+urlGenerarReporte);
		    				
	    					
	    					HttpHeaders headers = new HttpHeaders();
	    				    headers = createHeaders(iConfiguracionRepository.buscarPropiedad(jaspersserver_username), desencriptar.decrypt(iConfiguracionRepository.buscarPropiedad(jaspersserver_password)));
	    				    		//configService.findAll().get(16).getVc_valor_propiedad());
	    					headers.set("Content-Type",report.getVc_tipo_contenido());
	    					headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM));
	      					
	    					   					
	    					// Request Body
	    					MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

	    					// Request Entity
	    					HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	    					
	    					// RestTemplate
	    					RestTemplate restTemplate = new RestTemplate();

	    					// DELETE cancelar
	    					ResponseEntity<byte[]> response = restTemplate.exchange(urlGenerarReporte, HttpMethod.GET, requestEntity, byte[].class);

	    					// ResponseBody
	    					byte[] responseBody = response.getBody();
	    					
	    					
	    					Path archivoPath = Paths.get(rutaCreacion+"/"+identificadorDoc+report.getVc_extension());
	    					
	    					try (FileOutputStream archivoOutputStream = new FileOutputStream(archivoPath.toString())) {
	    					    archivoOutputStream.write(responseBody);
	    					}
	    					logger.error("El archivo \"" + archivoPath.toString() + "\" se ha creado o reemplazado exitosamente.");
	    					
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
    				
    					

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
    }
        
	@SuppressWarnings("deprecation")
	@Transactional
	public byte[] generarArchivoCorreo(Integer idReporte, String parametros) {
		try {
			ReporteJasper report = iReporteRepository.getById(idReporte);
			
			String urlGenerarReporte = iConfiguracionRepository.buscarPropiedad(jaspersserver_url)+report.getVc_url_reporte()+report.getVc_extension()+parametros.replace("~", "&");
			
			logger.info("url:"+urlGenerarReporte);
			
			HttpHeaders headers = new HttpHeaders();
		    headers = createHeaders(iConfiguracionRepository.buscarPropiedad(jaspersserver_username), desencriptar.decrypt(iConfiguracionRepository.buscarPropiedad(jaspersserver_password)));
		    		//configService.findAll().get(16).getVc_valor_propiedad());
			headers.set("Content-Type",report.getVc_tipo_contenido());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM));
				
			// Request Body
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

			// Request Entity
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			
			// RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// DELETE cancelar
			ResponseEntity<byte[]> response = restTemplate.exchange(urlGenerarReporte, HttpMethod.GET, requestEntity, byte[].class);

			// ResponseBody
			byte[] responseBody = response.getBody();
			
			return responseBody;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	
    
	private static HttpHeaders createHeaders(String username, String password) {
	    HttpHeaders headers = new HttpHeaders();

	    String auth = username + ":" + password;
	    String encodedAuth = Base64.getEncoder()
	            .encodeToString(auth.getBytes(StandardCharsets.US_ASCII));

	    headers.set("Authorization", "Basic " + encodedAuth);

	    return headers;
	}


    
}
