package ahorrosbienestar.sib.std.correoSIB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EnvioCorreoService {

	@Value("${spring.mail.username}")
    private String from;
	
	@Autowired
	private final JavaMailSender mailSender;
	
	@Autowired
	public EnvioCorreoService(JavaMailSender mailSender) {
	     this.mailSender = mailSender;
	}
	
	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
	        SimpleMailMessage correo = new SimpleMailMessage();
	        correo.setFrom(from);
	        correo.setTo(destinatario);
	        correo.setSubject(asunto);
	        correo.setText(mensaje);

	        mailSender.send(correo);
	}	
}
