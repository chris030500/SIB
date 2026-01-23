package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;
import ahorrosbienestar.sib.std.presolicitudes.repository.ConfiguracionCorreoRepository;

@Service
public class EmailService {
    
	 @Autowired
	    private ConfiguracionCorreoRepository configuracionCorreoRepository;

	    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {
	        // ðŸ”¹ Obtener remitente y contraseÃ±a de la BD
	        ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();
	        if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
	            throw new RuntimeException("No se encontrÃ³ un remitente o contraseÃ±a configurada en la base de datos.");
	        }

	        String remitente = config.getCorreoRemitente();
	        String password = config.getPassword();

	        System.out.println("Enviando correo desde: " + remitente);

	        // ðŸ”¹ Configurar JavaMailSender dinÃ¡mico con los datos obtenidos
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.office365.com"); // Ajusta esto segÃºn el proveedor SMTP
	        mailSender.setPort(587);
	        mailSender.setUsername(remitente);
	        mailSender.setPassword(password);

	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        try {
	            // ðŸ”¹ Crear el mensaje con contenido HTML
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(remitente);
	            helper.setTo(destinatario);
	            helper.setSubject(asunto);
	            helper.setText(contenidoHtml, true); // âœ… Contenido en HTML

	            // ðŸ”¹ Enviar el correo
	            mailSender.send(message);
	            System.out.println("Correo enviado exitosamente a: " + destinatario);
	        } catch (Exception e) {
	            System.err.println("Error al enviar el correo: " + e.getMessage());
	            throw new RuntimeException("Error al enviar el correo.", e);
	        }
	    }
	    
	    public void sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml, String fileName, byte[] fileBytes) {
	        System.out.print("Inicia envÃ­o de correo");

	        try {
	            // ðŸ”¹ Obtener remitente y contraseÃ±a de la BD
	            ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();
	            if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
	                throw new RuntimeException("No se encontrÃ³ un remitente o contraseÃ±a configurada en la base de datos.");
	            }

	            String remitente = config.getCorreoRemitente();
	            String password = config.getPassword();

	            System.out.println("Enviando correo desde: " + remitente);

	            // ðŸ”¹ Crear un JavaMailSender dinÃ¡mico con los datos de la BD
	            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	            mailSender.setHost("smtp.office365.com"); // Cambia esto segÃºn tu proveedor SMTP
	            mailSender.setPort(587);
	            mailSender.setUsername(remitente);
	            mailSender.setPassword(password);

	            Properties props = mailSender.getJavaMailProperties();
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.smtp.starttls.enable", "true");

	            // ðŸ”¹ Crear el mensaje
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(remitente);
	            helper.setTo(destinatario);
	            helper.setSubject(asunto);
	            helper.setText(contenidoHtml, true);

	            // ðŸ”¹ Adjuntar archivo
	            DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
	            helper.addAttachment(fileName, dataSource);

	            // ðŸ”¹ Enviar correo
	            mailSender.send(message);
	            System.out.println("Correo enviado exitosamente desde: " + remitente);

	        } catch (MessagingException e) {
	            System.out.print("Fallo en el envÃ­o de correo: " + e.getMessage());
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.print("Error desconocido al enviar el correo: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
}


