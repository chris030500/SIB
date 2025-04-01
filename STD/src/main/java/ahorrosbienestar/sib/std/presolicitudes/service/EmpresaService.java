package ahorrosbienestar.sib.std.presolicitudes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.presolicitudes.model.Empresa;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.ProcesoEnvioCorreoVobo;
import ahorrosbienestar.sib.std.presolicitudes.repository.EmpresaRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.EmpresaSIBRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.ProcesoEnvioCorreoVoboRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.SolicitudCreditoRepository;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;

@Service
public class EmpresaService {

	@Value("${spring.mail.username}")
	private String remitente;

	private final EmpresaRepository empresaRepository;

	private final EmpresaSIBRepository empresaSIBRepository;

	private final IReporteRepository iReporteRepository;

	private final SolicitudCreditoRepository solicitudCreditoRepository;
	
	@Autowired
	private static final Logger logger = Logger.getLogger(EmpresaService.class); // Evita la declaración estática del
																					// Logger

	@Autowired
	private final JavaMailSender mailSender;

	@Autowired
	private final JasperReportController jasperReportController;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private ProcesoEnvioCorreoVoboRepository procesoEnvioCorreoVoboRepository;

	public EmpresaService(EmpresaRepository empresaRepository, EmpresaSIBRepository empresaSIBRepository,
			IReporteRepository iReporteRepository, JavaMailSender mailSender,
			JasperReportController jasperReportController, SolicitudCreditoRepository solicitudCreditoRepository) {
		this.empresaRepository = empresaRepository;
		this.empresaSIBRepository = empresaSIBRepository;
		this.iReporteRepository = iReporteRepository;
		this.mailSender = mailSender;
		this.jasperReportController = jasperReportController;
		this.solicitudCreditoRepository = solicitudCreditoRepository;
	}

	public List<Empresa> obtenerTodasLasEmpresas() {
		try {
			System.out.println(empresaRepository.findAll());
			return empresaRepository.findAll(); // Asegúrate de que `findAll()` está funcionando
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>(); // Evita que explote si hay un error
		}
	}

	public EmpresaSIB guardarEmpresaVobo(EmpresaSIB empresa) {
		return empresaSIBRepository.save(empresa);
	}

	public boolean empresaExiste(String nombreEmpresa) {
		return empresaSIBRepository.findByNombre(nombreEmpresa) != null;
	}

	// Obtener todas las empresas
	public List<EmpresaSIB> obtenerTodas() {
		return empresaSIBRepository.findAll(); // Devuelve todas las empresas registradas
	}
	
	public EmpresaSIB obtenerIdEmpresa(String nombre) {
		return empresaSIBRepository.findByNombre(nombre);
	}
	
	public EmpresaSIB obtenerPorId(int id) {
		Optional<EmpresaSIB> empresa = empresaSIBRepository.findById(id);
		return empresa.orElse(null); // Si no se encuentra, devuelve null
	}
	
	private boolean debeEnviarNotificacion(EmpresaSIB empresa, LocalDateTime ahora) {
        if (empresa.getFecha() == null) {
            return true;
        }

        long diasDesdeUltima = ahora.toLocalDate().toEpochDay() - empresa.getFecha().toLocalDate().toEpochDay();

        switch (empresa.getFrecuencia()) {
            case 1:
                return diasDesdeUltima >= 7;
            case 2:
                return diasDesdeUltima >= 15;
            case 3:
                return diasDesdeUltima >= 30;
            default:
                return false;
        }
    }

	public void preparCorreoAdjuntoVobo() {
	    LocalDate fechaActual = LocalDate.now();
	    System.out.println("Inicia generación de correo");

	    try {
	        LocalDateTime ahora = LocalDateTime.now();
	        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");
	        List<EmpresaSIB> listaCorreos = obtenerTodas();

	        for (EmpresaSIB correoContenido : listaCorreos) {
	            if (debeEnviarNotificacion(correoContenido, ahora)) {
	                
	                // 1. Contar cuántas solicitudes existen para la empresa
	                int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(
	                    correoContenido.getNombre()
	                );

	                // Si no hay solicitudes, no se hace nada
	                if (cantidadSolicitudes == 0) {
	                	// 6. **Actualizar la fecha de última notificación en EmpresaSIB**
		                correoContenido.setFecha(ahora);
		                empresaSIBRepository.save(correoContenido);
	                    System.out.println("No hay solicitudes para la empresa: " + correoContenido.getNombre());
	                    continue;
	                }

	                // 2. Crear un nuevo proceso de envío de correo
	                ProcesoEnvioCorreoVobo procesoEnvio = new ProcesoEnvioCorreoVobo();
	                procesoEnvio.setVcEmpresa(correoContenido.getNombre()); // Nombre de la empresa
	                procesoEnvio.setDtFechaEnvio(ahora);
	                procesoEnvio.setIEstatus(0);  // Estatus inicial (pendiente)
	                procesoEnvio.setICantidadSolicitudes(cantidadSolicitudes);
	                procesoEnvio = procesoEnvioCorreoVoboRepository.save(procesoEnvio);  // Guarda el proceso y obtiene el ID

	                // 3. Actualizar las solicitudes con el ID del proceso recién creado
	                solicitudCreditoRepository.actualizarSolicitudesPorEmpresa(
	                    correoContenido.getNombre(), procesoEnvio.getPkIdProceso()
	                );

	                // 4. Enviar correo
	                String correo = correoContenido.getCorreoContacto();
	                String fileName = correoContenido.getNombre() + fechaActual + datosReporte.getVc_extension();
	                String contenidoCorreo = contenidoCorreo(correoContenido.getId());
	                InternetAddress direccion = new InternetAddress(correo);
	                
	                String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
	                byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);

	                sendEmailWithAttachment(direccion.getAddress(), 
	                    "Solicitudes de Nómina para Aprobación", contenidoCorreo, fileName, fileBytes);

	                // 5. Actualizar el estatus del proceso a "completado"
	                procesoEnvio.setIEstatus(1);  // 1 para completado
	                procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	                // 6. **Actualizar la fecha de última notificación en EmpresaSIB**
	                correoContenido.setFecha(ahora);
	                empresaSIBRepository.save(correoContenido);
	            }
	        }
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	        System.out.println("Error: " + e.getMessage());
	    }
	}


	// Armar contenido de correo.
	public String contenidoCorreo(int emp) {
	    try {
	        logger.info("empresa: " + emp);
	        String sql = "CALL SIB.SP_CORREO_VOBO(?);";
	        
	        String contenido = jdbcTemplate.queryForObject(sql, new Object[] { emp }, String.class);
	        
	        if (contenido == null) {
	            logger.warn("Contenido correo es nulo para la empresa: " + emp);
	            return ""; // Devuelve un String vacío si el contenido es nulo
	        }
	        return contenido;
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage(), e);
	        return null;
	    }
	}


	public void sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml, String fileName,
			byte[] fileBytes) {
		
		System.out.print("Inicia envio de correo");

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(remitente);
			helper.setTo(destinatario);
			helper.setSubject(asunto);
			helper.setText(contenidoHtml, true);

			DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
			helper.addAttachment(fileName, dataSource);

			mailSender.send(message);
		} catch (MessagingException e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage(), e); // Agregar el error completo en el log
			System.out.print("Inicia generacion de correo FALLO");
		} catch (Exception e) {
			logger.error("Error desconocido al enviar el correo: " + e.getMessage(), e); // Agregar captura más general
			System.out.print("Inicia generacion de correo FALLO 2");
		}
	}
}
