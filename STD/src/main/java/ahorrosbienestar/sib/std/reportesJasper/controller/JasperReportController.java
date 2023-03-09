package ahorrosbienestar.sib.std.reportesJasper.controller;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
@CrossOrigin
@RestController
@Produces("application/pdf")
@RequestMapping("/exportarReporte")

public class JasperReportController {
	
	
    protected final Log logger = LogFactory.getLog(this.getClass());
	
   
    public ResponseEntity<?> autenticacion() {
	try {
		
		 // Request Header
        HttpHeaders headers = new HttpHeaders();
        headers = createHeaders("juan.trejo", "Bienestar99");
        
        // Request Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("j_username", "juan.trejo");
        body.add("j_password", "Bienestar99");
        
        // Request Entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
 
        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
 
        // POST Login
        ResponseEntity<String> response = restTemplate.exchange("http://10.150.102.82:8080/jasperserver/rest/login?", HttpMethod.POST, requestEntity,
                String.class);
        
       
        HttpHeaders responseHeaders = response.getHeaders();
        
        String responseBody = response.getBody();
        String responseStatus = String.valueOf(response.getStatusCodeValue());
        
        return  null;
	}catch (HttpStatusCodeException e) {
		logger.info("autenticacion exception: " + e.getMessage());
//      System.out.println("Error en AutenticaciÃ³n: " + e.getMessage());
//      System.out.println("Error e : " + e);
//      System.out.println("Error e[0]: " + e.getLocalizedMessage().codePointAt(0));
      System.out.println("e: " + e.getRawStatusCode());
      if(e.getRawStatusCode() == 401) {
          //System.out.println("Bad credenciales");
          return ResponseEntity.ok("{\r\n"
                  + "    \"error\": \"unauthorized_user\",\r\n"
                  + "    \"error_description\": \"Bad credentials\"\r\n"
                  + "}");
      }
      return ResponseEntity.ok("autenticacion: " + e.getRawStatusCode());
	}
        	
    }
    @SuppressWarnings("deprecation")
	@RequestMapping("generarReporte")
    public byte[] reporteJasper() {
   	 
    	try {
        	/*
            ResponseEntity<String> response = restTemplate.exchange("http://10.150.102.82:8080/jasperserver/rest/reports/reports/Desarrollo/std/rpt_contrato_std.pdf?ID_Reporte=11", HttpMethod.GET, requestEntity,
                    String.class);*/
    		
    		// Request Header
    		   			
    					HttpHeaders headers = new HttpHeaders();
    				    //headers = createHeaders("juan.trejo", "Bienestar99");
    					headers.set("Content-Type","application/x-www-form-urlencoded");
    					headers.set("Cookie","userLocale=es_MX; userLocale=es_MX; JSESSIONID=7d287d4a1f7278e80f280219e4e2");
    					headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM));
      					
    					   					
    					// Request Body
    					MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    					// Request Entity
    					HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    					body.add("j_username", "juan.trejo");
    					
    					
    					// RestTemplate
    					RestTemplate restTemplate = new RestTemplate();

    					// DELETE cancelar
    					//"http://10.150.102.82:8080/jasperserver/rest_v2/reports/reports/Desarrollo/std/rpt_contrato_std.pdf?ID_Reporte=12"
    					//http://10.150.102.82:8080/jasperserver/rest_v2/reports/reports/Desarrollo/reporteador_bienestar/rpt_Nomina_Simultaneo_1.1.xls?FECHA_INICIO=2023/03/01&FECHA_FIN=2023/03/05
    					ResponseEntity<byte[]> response = restTemplate.exchange("http://10.150.102.82:8080/jasperserver/rest_v2/reports/reports/Desarrollo/std/rpt_contrato_std.pdf?ID_Reporte=12", HttpMethod.GET, requestEntity, byte[].class);

    					// ResponseBody
    					byte[] responseBody = response.getBody();
    					
    					
    					//String content = response.getBody();
    					//byte[] bytes = content.getBytes();
    					//Files.write(Paths.get("C:///Users//juan.trejo//Downloads//contrato_apertura.pdf"),responseBody, StandardOpenOption.CREATE );
    					
    					//byte[] reporte = responseBody.getBytes();
    					
    			        //response.getHeaders().set("Content-Type", "text/html;charset=UTF-8");
    			        
    			        return responseBody;
    		
    	
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
