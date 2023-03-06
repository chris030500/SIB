package ahorrosbienestar.sib.std.reportesJasper.controller;

import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.tomcat.util.codec.binary.Base64;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
@CrossOrigin
@RestController
@RequestMapping("/exportarReporte")
public class JasperReportController {
	
	
    protected final Log logger = LogFactory.getLog(this.getClass());
	
    @RequestMapping("generarReporte")
    public ResponseEntity<?> autenticacion() {
				
    	 // Request Header
        HttpHeaders headers = new HttpHeaders();
        headers = createHeaders("juan.trejo", "Bienestar99");
    
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
