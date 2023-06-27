package ahorrosbienestar.sib.std.reportesJasper.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
@CrossOrigin
@RestController
@RequestMapping("/exportarReporte")

public class JasperReportController {
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	
	
    //protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(JasperReportController.class);
	
    private String extPDF = ".pdf";
    private String extXLS = ".xls";
    
    
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
    				
    					String urlGenerarReporte = configService.findAll().get(14).getVc_valor_propiedad()+report.getVc_url_reporte()+tipoDocumento+parametros.replace("~", "&");
    					
    					logger.info("url:"+urlGenerarReporte);
    					HttpHeaders headers = new HttpHeaders();
    				    headers = createHeaders(configService.findAll().get(15).getVc_valor_propiedad(), configService.findAll().get(16).getVc_valor_propiedad());
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
    					String fechaCreacion = null;
    					StringBuilder stringBuilder = null;
    					ContentDisposition contentDisposition = null;
    					HttpHeaders h2 = new HttpHeaders();
    					if(extPDF.equalsIgnoreCase(tipoDocumento)) {
    						//fecha de creacion
        					fechaCreacion = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        				    stringBuilder = new StringBuilder().append(report.getVc_nombre_reporte());
        				    contentDisposition = ContentDisposition.builder("inline")
        				    .filename(stringBuilder.append("_")
        				    .append(identificadorDoc).append("_")
        				    .append(fechaCreacion)
        				    .append(tipoDocumento)
        				    .toString())
        				    .build();
        				    h2.setContentDisposition(contentDisposition);
    					}else if(extXLS.equalsIgnoreCase(tipoDocumento)) {
    						fechaCreacion = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
        				    stringBuilder = new StringBuilder().append(report.getVc_nombre_reporte());
        				    contentDisposition = ContentDisposition.builder("attachment")
        				    .filename(stringBuilder.append("_")
        				    .append(identificadorDoc).append("_")
        				    .append(fechaCreacion)
        				    .append(tipoDocumento)
        				    .toString())
        				    .build();
        				    h2.setContentDisposition(contentDisposition);
    					}
    					
    				    return ResponseEntity.ok().contentLength((long) responseBody.length)
    				    .contentType(MediaType.APPLICATION_PDF)
    				    .headers(h2).body(new ByteArrayResource(responseBody));
    					    		
    	
		} catch (Exception e) {
			System.out.print(""+e.getMessage());
		}
		return null;
    }
    
    
    private static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }
    
}
