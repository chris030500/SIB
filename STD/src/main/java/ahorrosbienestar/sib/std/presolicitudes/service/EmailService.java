package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.Properties;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;
import ahorrosbienestar.sib.std.presolicitudes.repository.ConfiguracionCorreoRepository;

@Service
public class EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class);

    @Autowired
    private ConfiguracionCorreoRepository configuracionCorreoRepository;

    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {

        ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();

        if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
            throw new RuntimeException("Configuración de correo incompleta.");
        }

        String remitente = config.getCorreoRemitente();
        String password = config.getPassword();

        logger.info("Inicia envío de correo.");

        JavaMailSenderImpl mailSender = crearMailSender(remitente, password);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            mailSender.send(message);

            logger.info("Correo enviado correctamente.");

        } catch (Exception e) {
            logger.error("Error al enviar correo.", e);
            throw new RuntimeException("Error al enviar el correo.", e);
        }
    }

    public void sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml,
            String fileName, byte[] fileBytes) {

        logger.info("Inicia envío de correo con adjunto.");

        try {
            ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();

            if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
                throw new RuntimeException("Configuración de correo incompleta.");
            }

            String remitente = config.getCorreoRemitente();
            String password = config.getPassword();

            JavaMailSenderImpl mailSender = crearMailSender(remitente, password);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
            helper.addAttachment(fileName, dataSource);

            mailSender.send(message);

            logger.info("Correo con adjunto enviado correctamente.");

        } catch (MessagingException e) {
            logger.error("Fallo en envío de correo con adjunto.", e);
        } catch (Exception e) {
            logger.error("Error inesperado al enviar correo con adjunto.", e);
        }
    }

    private JavaMailSenderImpl crearMailSender(String remitente, String password) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername(remitente);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}