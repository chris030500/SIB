package ahorrosbienestar.sib.std.presolicitudes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

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
	private static final Logger logger = Logger.getLogger(EmpresaService.class); // Evita la declaraciÃ³n estÃ¡tica del

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
			return empresaRepository.findAll(); // AsegÃºrate de que `findAll()` estÃ¡ funcionando
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
        
        logger.info("Frecuencia empresa=" + empresa.getNombre() + " frecuencia=" + empresa.getFrecuencia());
        
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
    logger.info("Inicia generacion de correo VoBo (masivo)");

    try {
        LocalDateTime ahora = LocalDateTime.now();
        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");
        if (datosReporte == null) {
            logger.error("No se encontro reporte rpt_solicitudes_vobo");
            return;
        }
        List<EmpresaSIB> listaCorreos = obtenerTodas();
        logger.info("Empresas a evaluar: " + listaCorreos.size());

        for (EmpresaSIB correoContenido : listaCorreos) {
            if (!debeEnviarNotificacion(correoContenido, ahora)) {
                continue;
            }

            int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(
                correoContenido.getNombre()
            );
            logger.info("Empresa=" + correoContenido.getNombre() + " solicitudes=" + cantidadSolicitudes);

            if (cantidadSolicitudes == 0) {
                correoContenido.setFecha(ahora);
                empresaSIBRepository.save(correoContenido);
                logger.info("No hay solicitudes para la empresa: " + correoContenido.getNombre());
                continue;
            }

            ProcesoEnvioCorreoVobo procesoEnvio = new ProcesoEnvioCorreoVobo();
            procesoEnvio.setVcEmpresa(correoContenido.getNombre());
            procesoEnvio.setDtFechaEnvio(ahora);
            procesoEnvio.setIEstatus(0);
            procesoEnvio.setICantidadSolicitudes(cantidadSolicitudes);
            procesoEnvio = procesoEnvioCorreoVoboRepository.save(procesoEnvio);

            solicitudCreditoRepository.actualizarSolicitudesPorEmpresa(
                correoContenido.getNombre(), procesoEnvio.getPkIdProceso()
            );

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            String correos = correoContenido.getCorreoContacto();
            logger.info("Empresa=" + correoContenido.getNombre() + " correos=" + correos);
            String[] destinatarios = parseDestinatarios(correos);
            logger.info("Correos normalizados empresa=" + correoContenido.getNombre()
                    + " destinatarios=" + Arrays.toString(destinatarios));
            boolean todosValidos = true;

            for (String dest : destinatarios) {
                try {
                    new InternetAddress(dest).validate();
                } catch (AddressException e) {
                    logger.warn("Correo invalido empresa=" + correoContenido.getNombre() + " correo=" + dest, e);
                    todosValidos = false;
                    break;
                }
            }

            if (!todosValidos) {
                logger.warn("Se omite envio por correos invalidos empresa=" + correoContenido.getNombre());
                continue;
            }

            String fileName = correoContenido.getNombre() + fechaActual + datosReporte.getVc_extension();
            String contenidoCorreo = contenidoCorreo(correoContenido.getId());
            String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
            byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);
            logger.info("Archivo generado empresa=" + correoContenido.getNombre()
                    + " bytes=" + (fileBytes != null ? fileBytes.length : 0));

            boolean enviado = sendEmailWithAttachment(
                correos,
                "Solicitudes Vo.bo. Bienestar",
                contenidoCorreo,
                fileName,
                fileBytes
            );

            if (enviado) {
                procesoEnvio.setIEstatus(1);
                procesoEnvioCorreoVoboRepository.save(procesoEnvio);

                correoContenido.setFecha(ahora);
                empresaSIBRepository.save(correoContenido);
                logger.info("Envio OK empresa=" + correoContenido.getNombre());
            } else {
                logger.warn("Envio FAIL empresa=" + correoContenido.getNombre());
            }
        }
    } catch (Exception e) {
        logger.error("Error en envio masivo VoBo: " + e.getMessage(), e);
    }
}

public void prepararCorreoPorEmpresa(String nomEmpresa) {
    LocalDate fechaActual = LocalDate.now();
    logger.info("Inicia generacion de correo para empresa: " + nomEmpresa);

    try {
        LocalDateTime ahora = LocalDateTime.now();
        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");
        if (datosReporte == null) {
            logger.error("No se encontro reporte rpt_solicitudes_vobo");
            return;
        }

        EmpresaSIB empresa = empresaSIBRepository.findByNombre(nomEmpresa);
        if (empresa == null) {
            logger.warn("Empresa no encontrada: " + nomEmpresa);
            return;
        }

        if (!debeEnviarNotificacion(empresa, ahora)) {
            logger.info("No es momento de enviar notificacion para empresa: " + nomEmpresa);
            return;
        }

        int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(nomEmpresa);
        logger.info("Empresa=" + nomEmpresa + " solicitudes=" + cantidadSolicitudes);

        if (cantidadSolicitudes == 0) {
            empresa.setFecha(ahora);
            empresaSIBRepository.save(empresa);
            logger.info("No hay solicitudes para la empresa: " + nomEmpresa);
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
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String correos = empresa.getCorreoContacto();
        logger.info("Empresa=" + nomEmpresa + " correos=" + correos);
        String[] destinatarios = parseDestinatarios(correos);
        logger.info("Correos normalizados empresa=" + nomEmpresa + " destinatarios="
                + Arrays.toString(destinatarios));
        boolean todosValidos = true;

        for (String dest : destinatarios) {
            try {
                new InternetAddress(dest).validate();
            } catch (AddressException e) {
                logger.warn("Correo invalido empresa=" + empresa.getNombre() + " correo=" + dest, e);
                todosValidos = false;
                break;
            }
        }

        if (!todosValidos) {
            logger.warn("Se omite envio por correos invalidos empresa=" + empresa.getNombre());
            return;
        }

        String fileName = empresa.getNombre() + fechaActual + datosReporte.getVc_extension();
        String contenidoCorreo = contenidoCorreo(empresa.getId());
        String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
        byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);
        logger.info("Archivo generado empresa=" + empresa.getNombre()
                + " bytes=" + (fileBytes != null ? fileBytes.length : 0));

        boolean enviado = sendEmailWithAttachment(
            correos,
            "Solicitudes Vo.bo. Bienestar",
            contenidoCorreo,
            fileName,
            fileBytes
        );

        if (enviado) {
            procesoEnvio.setIEstatus(1);
            procesoEnvioCorreoVoboRepository.save(procesoEnvio);

            empresa.setFecha(ahora);
            empresaSIBRepository.save(empresa);
            logger.info("Envio OK empresa=" + empresa.getNombre());
        } else {
            logger.warn("Envio FAIL empresa=" + empresa.getNombre());
        }

    } catch (Exception e) {
        logger.error("Error al enviar correo por empresa=" + nomEmpresa + " msg=" + e.getMessage(), e);
    }
}

public String prepararCorreoPorEmpresaV(Integer idEmpresa) {
    LocalDate fechaActual = LocalDate.now();
    logger.info("Inicia generacion de correo para empresa con ID: " + idEmpresa);

    try {
        LocalDateTime ahora = LocalDateTime.now();
        ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitudes_vobo");
        if (datosReporte == null) {
            logger.error("No se encontro reporte rpt_solicitudes_vobo");
            return "Reporte no disponible.";
        }

        EmpresaSIB empresa = empresaSIBRepository.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            logger.warn("Empresa no encontrada id=" + idEmpresa);
            return "Empresa no encontrada.";
        }

        int cantidadSolicitudes = solicitudCreditoRepository.contarSolicitudesPorEmpresa(empresa.getNombre());
        logger.info("Empresa=" + empresa.getNombre() + " solicitudes=" + cantidadSolicitudes);
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
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String correos = empresa.getCorreoContacto();
        logger.info("Empresa=" + empresa.getNombre() + " correos=" + correos);
        String[] destinatarios = parseDestinatarios(correos);
        logger.info("Correos normalizados empresa=" + empresa.getNombre()
                + " destinatarios=" + Arrays.toString(destinatarios));

        for (String dest : destinatarios) {
            try {
                new InternetAddress(dest).validate();
            } catch (AddressException e) {
                logger.warn("Correo invalido empresa=" + empresa.getNombre() + " correo=" + dest, e);
                return "Correo invalido: " + dest;
            }
        }

        String fileName = empresa.getNombre() + fechaActual + datosReporte.getVc_extension();
        String contenidoCorreo = contenidoCorreo(empresa.getId());
        String parametros = "?IDPROCESO=" + procesoEnvio.getPkIdProceso();
        byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);
        logger.info("Archivo generado empresa=" + empresa.getNombre()
                + " bytes=" + (fileBytes != null ? fileBytes.length : 0));

        boolean enviado = sendEmailWithAttachment(
            correos,
            "Solicitudes Vo.bo. Bienestar",
            contenidoCorreo,
            fileName,
            fileBytes
        );

        if (enviado) {
            procesoEnvio.setIEstatus(1);
            procesoEnvioCorreoVoboRepository.save(procesoEnvio);

            empresa.setFecha(ahora);
            empresaSIBRepository.save(empresa);
            logger.info("Envio OK empresa=" + empresa.getNombre());
            return "Correo enviado correctamente.";
        }

        logger.warn("Envio FAIL empresa=" + empresa.getNombre());
        return "Error al enviar el correo.";

    } catch (Exception e) {
        logger.error("Error al enviar correo por empresa id=" + idEmpresa + " msg=" + e.getMessage(), e);
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
	            return ""; // Devuelve un String vacÃ­o si el contenido es nulo
	        }
	        return contenido;
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage(), e);
	        return null;
	    }
	}


    private String[] parseDestinatarios(String correos) {
        if (correos == null) {
            return new String[0];
        }
        LinkedHashSet<String> clean = new LinkedHashSet<>();
        String[] tokens = correos.split("[;,]");
        for (String token : tokens) {
            String trimmed = token == null ? "" : token.trim();
            if (!trimmed.isEmpty()) {
                clean.add(trimmed);
            }
        }
        return clean.toArray(new String[0]);
    }

	public boolean sendEmailWithAttachment(String destinatario, String asunto, String contenidoHtml, String fileName, byte[] fileBytes) {
    long startMs = System.currentTimeMillis();
    logger.info("Inicia envio correo asunto=" + asunto
            + " file=" + fileName
            + " bytes=" + (fileBytes != null ? fileBytes.length : 0)
            + " contenidoLen=" + (contenidoHtml != null ? contenidoHtml.length() : 0));

    try {
        ConfiguracionCorreo config = configuracionCorreoRepository.findTopByOrderByIdAsc();
        if (config == null || config.getCorreoRemitente() == null || config.getPassword() == null) {
            throw new RuntimeException("No se encontro remitente o password en BD.");
        }

        String remitente = config.getCorreoRemitente();
        String password = config.getPassword();

        logger.info("Enviando correo desde: " + remitente);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername(remitente);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        logger.info("SMTP host=" + mailSender.getHost() + " port=" + mailSender.getPort());

        String[] destinatarios = parseDestinatarios(destinatario);
        logger.info("Destinatarios: " + Arrays.toString(destinatarios));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(remitente);
        helper.setTo(destinatarios);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true);

        DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/octet-stream");
        helper.addAttachment(fileName, dataSource);

        mailSender.send(message);
        logger.info("Correo enviado a: " + Arrays.toString(destinatarios)
                + " elapsedMs=" + (System.currentTimeMillis() - startMs));
        return true;

    } catch (MessagingException e) {
        logger.error("Fallo en envio de correo: " + e.getMessage(), e);
        return false;
    } catch (Exception e) {
        logger.error("Error desconocido al enviar correo: " + e.getMessage(), e);
        return false;
    }
}
}

