package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.Properties;
import javax.mail.internet.MimeMessage;
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
	        // 🔹 Obtener remitente y contraseña de la BD
	        ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();
	        if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
	            throw new RuntimeException("No se encontró un remitente o contraseña configurada en la base de datos.");
	        }

	        String remitente = config.getCorreoRemitente();
	        String password = config.getPassword();

	        System.out.println("Enviando correo desde: " + remitente);

	        // 🔹 Configurar JavaMailSender dinámico con los datos obtenidos
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.office365.com"); // Ajusta esto según el proveedor SMTP
	        mailSender.setPort(587);
	        mailSender.setUsername(remitente);
	        mailSender.setPassword(password);

	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        try {
	            // 🔹 Crear el mensaje con contenido HTML
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(remitente);
	            helper.setTo(destinatario);
	            helper.setSubject(asunto);
	            helper.setText(contenidoHtml, true); // ✅ Contenido en HTML

	            // 🔹 Enviar el correo
	            mailSender.send(message);
	            System.out.println("Correo enviado exitosamente a: " + destinatario);
	        } catch (Exception e) {
	            System.err.println("Error al enviar el correo: " + e.getMessage());
	            throw new RuntimeException("Error al enviar el correo.", e);
	        }
	    }
}

