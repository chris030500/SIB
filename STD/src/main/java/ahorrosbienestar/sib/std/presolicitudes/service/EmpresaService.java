package ahorrosbienestar.sib.std.presolicitudes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;
import ahorrosbienestar.sib.std.presolicitudes.model.Empresa;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.ProcesoEnvioCorreoVobo;
import ahorrosbienestar.sib.std.presolicitudes.repository.ConfiguracionCorreoRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.EmpresaRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.EmpresaSIBRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.ProcesoEnvioCorreoVoboRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.SolicitudCreditoRepository;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;

@Service
public class EmpresaService {

	private final EmpresaRepository empresaRepository;

	private final EmpresaSIBRepository empresaSIBRepository;

	private final IReporteRepository iReporteRepository;

	private final SolicitudCreditoRepository solicitudCreditoRepository;
	
	@Autowired
    private ConfiguracionCorreoRepository configuracionCorreoRepository;
	
	@Autowired
	private static final Logger logger = Logger.getLogger(EmpresaService.class); // Evita la declaración estática del

	@Autowired
	private final JasperReportController jasperReportController;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private ProcesoEnvioCorreoVoboRepository procesoEnvioCorreoVoboRepository;

	public EmpresaService(EmpresaRepository empresaRepository, EmpresaSIBRepository empresaSIBRepository,
			IReporteRepository iReporteRepository,
			JasperReportController jasperReportController, SolicitudCreditoRepository solicitudCreditoRepository) {
		this.empresaRepository = empresaRepository;
		this.empresaSIBRepository = empresaSIBRepository;
		this.iReporteRepository = iReporteRepository;
		this.jasperReportController = jasperReportController;
		this.solicitudCreditoRepository = solicitudCreditoRepository;
	}

	public List<Empresa> obtenerTodasLasEmpresas() {
		try {
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
        
        System.out.print("FRECUENCIA: " + empresa.getFrecuencia());
        
        switch (empresa.getFrecuencia()) {
            case 1:
                return diasDesdeUltima >= 7;
            case 2:
                return diasDesdeUltima >= 15;
            case 3:
                return diasDesdeUltima >= 30;
            case 4:
                return diasDesdeUltima >= 1;
            case 5: 
            	return true;    
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
	                    correoContenido.setFecha(ahora);
	                    empresaSIBRepository.save(correoContenido);
	                    System.out.println("No hay solicitudes para la empresa: " + correoContenido.getNombre());
	                    continue;
	                }

	                // 2. Crear un nuevo proceso de envío de correo
	                ProcesoEnvioCorreoVobo procesoEnvio = new ProcesoEnvioCorreoVobo();
	                procesoEnvio.setVcEmpresa(correoContenido.getNombre());
	                procesoEnvio.setDtFechaEnvio(ahora);
	                procesoEnvio.setIEstatus(0); // Estatus inicial (pendiente)
	                procesoEnvio.setICantidadSolicitudes(cantidadSolicitudes);
	                procesoEnvio = procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	                // 3. Actualizar las solicitudes con el ID del proceso recién creado
	                solicitudCreditoRepository.actualizarSolicitudesPorEmpresa(
	                    correoContenido.getNombre(), procesoEnvio.getPkIdProceso()
	                );
	                
	                try {
	    	            TimeUnit.SECONDS.sleep(10);      // o bien: Thread.sleep(2000);
	    	        } catch (InterruptedException ex) {
	    	            // Restablece el estado de interrupción por buenas prácticas
	    	            Thread.currentThread().interrupt();
	    	            // Aquí podrías registrar el evento o decidir abortar el proceso
	    	        }

	                // 4. Validar correos
	                String correos = correoContenido.getCorreoContacto();
	                String[] destinatarios = correos.split(";");
	                boolean todosValidos = true;

	                for (String dest : destinatarios) {
	                    try {
	                        new InternetAddress(dest.trim()).validate();
	                    } catch (AddressException e) {
	                        System.out.println("Correo inválido para empresa " + correoContenido.getNombre() + ": " + dest);
	                        todosValidos = false;
	                        break;
	                    }
	                }

	                if (!todosValidos) {
	                    continue; // Saltar al siguiente si algún correo es inválido
	                }

	                // 5. Preparar y enviar el correo
	                String fileName = correoContenido.getNombre() + fechaActual + datosReporte.getVc_extension();
	                String contenidoCorreo = contenidoCorreo(correoContenido.getId());
	                String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
	                byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);

	                
	                
	                sendEmailWithAttachment(
	                    correos,  // puede ir con ;
	                    "Solicitudes Vo.bo. Bienestar",
	                    contenidoCorreo,
	                    fileName,
	                    fileBytes
	                );

	                // 6. Actualizar el estatus del proceso a "completado"
	                procesoEnvio.setIEstatus(1);
	                procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	                // 7. Actualizar la fecha de última notificación en EmpresaSIB
	                correoContenido.setFecha(ahora);
	                empresaSIBRepository.save(correoContenido);
	            }
	        }
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	public void prepararCorreoPorEmpresa(String nomEmpresa) {
	    LocalDate fechaActual = LocalDate.now();
	    System.out.println("Inicia generación de correo para empresa: " + nomEmpresa);

	    try {
	        LocalDateTime ahora = LocalDateTime.now();
	        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");

	        EmpresaSIB empresa = empresaSIBRepository.findByNombre(nomEmpresa);

	        if (!debeEnviarNotificacion(empresa, ahora)) {
	            System.out.println("No es momento de enviar notificación para la empresa: " + nomEmpresa);
	            return;
	        }

	        int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(nomEmpresa);

	        if (cantidadSolicitudes == 0) {
	            empresa.setFecha(ahora);
	            empresaSIBRepository.save(empresa);
	            System.out.println("No hay solicitudes para la empresa: " + nomEmpresa);
	            return;
	        }

	        ProcesoEnvioCorreoVobo procesoEnvio = new ProcesoEnvioCorreoVobo();
	        procesoEnvio.setVcEmpresa(empresa.getNombre());
	        procesoEnvio.setDtFechaEnvio(ahora);
	        procesoEnvio.setIEstatus(0);
	        procesoEnvio.setICantidadSolicitudes(cantidadSolicitudes);
	        procesoEnvio = procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	        solicitudCreditoRepository.actualizarSolicitudesPorEmpresa(
	            empresa.getNombre(), procesoEnvio.getPkIdProceso()
	        );

	        try {
	            TimeUnit.SECONDS.sleep(10);      // o bien: Thread.sleep(2000);
	        } catch (InterruptedException ex) {
	            // Restablece el estado de interrupción por buenas prácticas
	            Thread.currentThread().interrupt();
	            // Aquí podrías registrar el evento o decidir abortar el proceso
	        }
	        
	        String correos = empresa.getCorreoContacto();
	        String[] destinatarios = correos.split(";");
	        boolean todosValidos = true;

	        for (String dest : destinatarios) {
	            try {
	                new InternetAddress(dest.trim()).validate();
	            } catch (AddressException e) {
	                System.out.println("Correo inválido para empresa " + empresa.getNombre() + ": " + dest);
	                todosValidos = false;
	                break;
	            }
	        }

	        if (!todosValidos) {
	            return;
	        }

	        String fileName = empresa.getNombre() + fechaActual + datosReporte.getVc_extension();
	        String contenidoCorreo = contenidoCorreo(empresa.getId());
	        String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
	        byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);
	        
	        sendEmailWithAttachment(
	            correos,
	            "Solicitudes Vo.bo. Bienestar",
	            contenidoCorreo,
	            fileName,
	            fileBytes
	        );

	        procesoEnvio.setIEstatus(1);
	        procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	        empresa.setFecha(ahora);
	        empresaSIBRepository.save(empresa);

	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	public String prepararCorreoPorEmpresaV(Integer idEmpresa) {
	    LocalDate fechaActual = LocalDate.now();
	    System.out.println("Inicia generación de correo para empresa con ID: " + idEmpresa);

	    try {
	        LocalDateTime ahora = LocalDateTime.now();
	        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");

	        EmpresaSIB empresa = empresaSIBRepository.findById(idEmpresa).orElse(null);
	        if (empresa == null) {
	            return "Empresa no encontrada.";
	        }

	        int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(empresa.getNombre());
	        if (cantidadSolicitudes == 0) {
	            empresa.setFecha(ahora);
	            empresaSIBRepository.save(empresa);
	            return "No hay solicitudes para enviar.";
	        }

	        ProcesoEnvioCorreoVobo procesoEnvio = new ProcesoEnvioCorreoVobo();
	        procesoEnvio.setVcEmpresa(empresa.getNombre());
	        procesoEnvio.setDtFechaEnvio(ahora);
	        procesoEnvio.setIEstatus(0);
	        procesoEnvio.setICantidadSolicitudes(cantidadSolicitudes);
	        procesoEnvio = procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	        solicitudCreditoRepository.actualizarSolicitudesPorEmpresa(
	            empresa.getNombre(), procesoEnvio.getPkIdProceso()
	        );

	        try {
	            TimeUnit.SECONDS.sleep(10);      // o bien: Thread.sleep(2000);
	        } catch (InterruptedException ex) {
	            // Restablece el estado de interrupción por buenas prácticas
	            Thread.currentThread().interrupt();
	            // Aquí podrías registrar el evento o decidir abortar el proceso
	        }
	        String correos = empresa.getCorreoContacto();
	        String[] destinatarios = correos.split(";");
	        boolean todosValidos = true;

	        for (String dest : destinatarios) {
	            try {
	                new InternetAddress(dest.trim()).validate();
	            } catch (AddressException e) {
	                System.out.println("Correo inválido para empresa " + empresa.getNombre() + ": " + dest);
	                return "Correo inválido: " + dest;
	            }
	        }

	        String fileName = empresa.getNombre() + fechaActual + datosReporte.getVc_extension();
	        String contenidoCorreo = contenidoCorreo(empresa.getId());
	        String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
	        byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);
	        
	        sendEmailWithAttachment(
	            correos,
	            "Solicitudes Vo.bo. Bienestar",
	            contenidoCorreo,
	            fileName,
	            fileBytes
	        );

	        procesoEnvio.setIEstatus(1);
	        procesoEnvioCorreoVoboRepository.save(procesoEnvio);

	        empresa.setFecha(ahora);
	        empresaSIBRepository.save(empresa);

	        return "Correo enviado correctamente.";

	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	        return "Error al enviar el correo: " + e.getMessage();
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


	public void sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml, String fileName, byte[] fileBytes) {
	    System.out.print("Inicia envío de correo");

	    try {
	        // 🔹 Obtener remitente y contraseña de la BD
	        ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();
	        if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
	            throw new RuntimeException("No se encontró un remitente o contraseña configurada en la base de datos.");
	        }

	        String remitente = config.getCorreoRemitente();
	        String password = config.getPassword();

	        System.out.println("Enviando correo desde: " + remitente);

	        // 🔹 Crear un JavaMailSender dinámico con los datos de la BD
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.office365.com");
	        mailSender.setPort(587);
	        mailSender.setUsername(remitente);
	        mailSender.setPassword(password);

	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        // 🔹 Dividir los destinatarios separados por punto y coma (;)
	        String[] destinatarios = destinatario.split(";");
	        for (int i = 0; i < destinatarios.length; i++) {
	            destinatarios[i] = destinatarios[i].trim(); // Limpiar espacios
	        }

	        // 🔹 Crear el mensaje
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(remitente);
	        helper.setTo(destinatarios); // 👈 Aquí va el arreglo de correos
	        helper.setSubject(asunto);
	        helper.setText(contenidoHtml, true);

	        // 🔹 Adjuntar archivo
	        DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
	        helper.addAttachment(fileName, dataSource);

	        // 🔹 Enviar correo
	        mailSender.send(message);
	        System.out.println("Correo enviado exitosamente a: " + Arrays.toString(destinatarios));

	    } catch (MessagingException e) {
	        System.out.print("Fallo en el envío de correo: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.print("Error desconocido al enviar el correo: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
