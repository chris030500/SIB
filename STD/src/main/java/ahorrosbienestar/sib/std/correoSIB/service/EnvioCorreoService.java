package ahorrosbienestar.sib.std.correoSIB.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.correoSIB.dtos.ContenidoCorreo;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;

import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataSource;


@Service
public class EnvioCorreoService {

	@Value("${spring.mail.username}")
    private String remitente;
	
	@Autowired
	private final JavaMailSender mailSender;
	
	@Autowired
	private final JasperReportController jasperReportController;
	
	@Autowired
	private final EnvioCorreoContenidoService envioCorreoContenidoService;
	@Autowired
	private static final Logger logger = Logger.getLogger(EnvioCorreoService.class);
	
	@Autowired
	public EnvioCorreoService(JavaMailSender mailSender, EnvioCorreoContenidoService envioCorreoContenidoService, JasperReportController jasperReportController) {
	     this.mailSender = mailSender;
	     this.envioCorreoContenidoService = envioCorreoContenidoService;
	     this.jasperReportController = jasperReportController;
	}
	
	public void prepararCorreo(int tipoMns,String errores) {
		try {
			List<ContenidoCorreo> listaCorreos= envioCorreoContenidoService.contenidoCorreo(tipoMns,errores);
			for(ContenidoCorreo correoContenido : listaCorreos) {
				String[] listDireccion = correoContenido.getVc_destinatarios().split(";");
				for(String correo : listDireccion) {
					InternetAddress direccion = new InternetAddress(correo);
					correoContenidoHTML(direccion.getAddress(),correoContenido.getVc_asunto(),correoContenido.getVc_html());
				}
				
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
		
	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
		try {
			SimpleMailMessage correo = new SimpleMailMessage();
		    correo.setFrom(remitente);
		    correo.setTo(String.valueOf(destinatario));
		    correo.setSubject(asunto);
		    correo.setText(mensaje);
		    mailSender.send(correo);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
		
	     
	}
	
	public void correoContenidoHTML(String destinatario, String asunto, String contenidoHtml) {
		
        try {
        	MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper correoEnviar = new MimeMessageHelper(message, true);
			
			correoEnviar.setFrom(remitente);
			correoEnviar.setTo(String.valueOf(destinatario));
			correoEnviar.setSubject(asunto);
			correoEnviar.setText(contenidoHtml, true);
			
			mailSender.send(message);
		} catch (MessagingException e) {
			logger.info("Error:"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml, String fileName, byte[] fileBytes) {
       

        try {
        	MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
            // Attach the file from byte array
            helper.addAttachment(fileName, dataSource);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
	
	public void preparCorreoAdjunto(int tipoCorreo) {
		try {
			Integer idReporte = 7;
			String parametros = "?IDSUCURSAL=";
			
			
			List<Integer> listaSucRegistros = envioCorreoContenidoService.registrosXsucursal();
			for(Integer idSuc:listaSucRegistros) {
				String fileName="registros_asignados_suc"+idSuc+".xls";
	        	byte[] fileBytes = jasperReportController.generarArchivoCorreo(idReporte,parametros+idSuc);
				List<ContenidoCorreo> listaCorreos= envioCorreoContenidoService.contenidoCorreo(tipoCorreo,String.valueOf(idSuc));
				for(ContenidoCorreo correoContenido : listaCorreos) {
					String[] listDireccion = correoContenido.getVc_destinatarios().split(";");
					for(String correo : listDireccion) {
						InternetAddress direccion = new InternetAddress(correo);
						sendEmailWithAttachment(direccion.getAddress(),correoContenido.getVc_asunto(),correoContenido.getVc_html(),fileName,fileBytes);
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
	
	
	
}
