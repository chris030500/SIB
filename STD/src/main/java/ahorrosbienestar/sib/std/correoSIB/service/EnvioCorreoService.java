package ahorrosbienestar.sib.std.correoSIB.service;

import java.util.List;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.correoSIB.dtos.ContenidoCorreo;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;


@Service
public class EnvioCorreoService {

	@Value("${spring.mail.username}")
    private String remitente;
	
	@Autowired
	private final JavaMailSender mailSender;
	
	@Autowired
	private final JasperReportController jasperReportController;
	
	@Autowired 
	private IReporteRepository iReporteRepository;
	
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
			logger.error(Constantes.ERROR_LOG+e.getMessage());
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
			logger.error(Constantes.ERROR_LOG+e.getMessage());
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
			logger.info(Constantes.ERROR_LOG+e.getMessage());
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
   
			logger.error(Constantes.MENSAJE_ERROR+e.getMessage());
        }
    }
	
	public void preparCorreoAdjunto(int tipoCorreo, String nombreReporte, String parametros, String nombre, int tipoBusqueda) {
		try {	
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			List<Integer> listaSucRegistros = envioCorreoContenidoService.registrosXsucursal(tipoBusqueda);
			
			for(Integer idSuc:listaSucRegistros) {
					 try {
						 String fileName=nombre+idSuc+datosReporte.getVc_extension();
				        	byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(),parametros+idSuc);
							List<ContenidoCorreo> listaCorreos= envioCorreoContenidoService.contenidoCorreo(tipoCorreo,String.valueOf(idSuc));
							for(ContenidoCorreo correoContenido : listaCorreos) {
								String[] listDireccion = correoContenido.getVc_destinatarios().split(";");
								for(String correo : listDireccion) {
									InternetAddress direccion = new InternetAddress(correo);
									sendEmailWithAttachment(direccion.getAddress(),correoContenido.getVc_asunto(),correoContenido.getVc_html(),fileName,fileBytes);
								}
							}
					} catch (Exception e) {
						
						logger.error(Constantes.MENSAJE_ERROR+e.getMessage());
					}	
			}
						
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG+e.getMessage());
		}
	}
	
	public void preparCorreoAdjuntoSuc(int tipoCorreo, String nombreReporte, String parametros, String nombre, int numSucursal) {
		try {	
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			
			
				try {
							String fileName=nombre+numSucursal+datosReporte.getVc_extension();
				        	byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(),parametros+numSucursal);
							List<ContenidoCorreo> listaCorreos= envioCorreoContenidoService.contenidoCorreo(tipoCorreo,String.valueOf(numSucursal));
							for(ContenidoCorreo correoContenido : listaCorreos) {
								String[] listDireccion = correoContenido.getVc_destinatarios().split(";");
								for(String correo : listDireccion) {
									InternetAddress direccion = new InternetAddress(correo);
									sendEmailWithAttachment(direccion.getAddress(),correoContenido.getVc_asunto(),correoContenido.getVc_html(),fileName,fileBytes);
								}
							}
					} catch (Exception e) {
						
						logger.error(Constantes.MENSAJE_ERROR+e.getMessage());
					}
					 
					 
				
			
						
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG+e.getMessage());
		}
	}
	
	
	
	
}
