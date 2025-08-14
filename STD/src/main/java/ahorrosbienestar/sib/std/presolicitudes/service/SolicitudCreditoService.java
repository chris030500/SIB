package ahorrosbienestar.sib.std.presolicitudes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;
import ahorrosbienestar.sib.std.presolicitudes.repository.SolicitudCreditoRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Service
public class SolicitudCreditoService {

	private final Logger logger = Logger.getLogger(SolicitudCreditoService.class); // Corregido para no ser estático

	@Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;
	
	public SolicitudCredito guardarSolicitud(SolicitudCredito solicitud) {
        return solicitudCreditoRepository.save(solicitud);
    }
	
	public Optional<SolicitudCredito> obtSolicitudPorId(Integer id) {
        return solicitudCreditoRepository.findById(id);
    }
	
	public List<SolicitudCredito> listarSolicitudes(Integer idEmpleado) {
	    return solicitudCreditoRepository.findAllByEjecutivoOrderByIdDesc(idEmpleado);
	}
	
	public List<SolicitudCredito> listarSolicitudesPorSucursales(List<Integer> sucursales) {
	    return solicitudCreditoRepository.findAllBySucursalIn(sucursales);
	}

	public List<SolicitudCredito> listarTodasSolicitudes() {
	    return solicitudCreditoRepository.findAll();
	}
	
	// Método para obtener una solicitud por su ID
    public SolicitudCredito obtenerSolicitudPorId(Integer id) throws Exception {
        Optional<SolicitudCredito> solicitud = solicitudCreditoRepository.findById(id);
        
        if (solicitud.isPresent()) {
            return solicitud.get();
        } else {
            throw new Exception("Solicitud no encontrada con ID: " + id);
        }
    }
	
    @Transactional
    public void actualizarEstatusSolicitudes(List<Map<String, String>> solicitudes, Usuario usuarioRespuesta) {
        for (Map<String, String> solicitud : solicitudes) {
            String noSolicitudStr = solicitud.get("NoSolicitud");
            String autoriza = solicitud.get("Autoriza SI/NO");
            String comentariosEmpresa = solicitud.get("Comentarios Empresa") == null
            	    ? ""
            	    : solicitud.get("Comentarios Empresa").toUpperCase();

            if (noSolicitudStr == null || autoriza == null) {
                System.out.println("Datos faltantes en la solicitud.");
                continue;
            }

            try {
                Integer noSolicitud = Integer.parseInt(noSolicitudStr);
                Optional<SolicitudCredito> solicitudOptional = solicitudCreditoRepository.findById(noSolicitud);

                if (solicitudOptional.isPresent()) {
                    SolicitudCredito solicitudCredito = solicitudOptional.get();
                    solicitudCredito.setFechaRegistroRespuestaEmp(LocalDate.now());
                    solicitudCredito.setComentariosEmpresa(comentariosEmpresa);
                    solicitudCredito.setUsuarioRegRespuesta(usuarioRespuesta);
                    if ("SI".equalsIgnoreCase(autoriza)) {
                        solicitudCredito.setEstatus(3); // Aprobada
                       
                        logger.info("Solicitud " + noSolicitud + " ha sido APROBADA.");
                    } else {
                        solicitudCredito.setEstatus(4); // Rechazada
                        logger.info("Solicitud " + noSolicitud + " ha sido RECHAZADA.");
                    }

                    solicitudCreditoRepository.save(solicitudCredito);
                } else {
                	logger.info("No se encontró la solicitud con ID " + noSolicitud);
                }
            } catch (NumberFormatException e) {
            	logger.error("Número de solicitud inválido: " + noSolicitudStr);
            }
        }
    }
    
    public void aprobarSolicitud(Integer id) {
    	SolicitudCredito solicitud = solicitudCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Verificas que esté en estado válido para aprobar
        if (solicitud.getEstatus() != 1) {
            throw new IllegalStateException("Solo se pueden aprobar solicitudes en estado pendiente");
        }

        solicitud.setEstatus(5); // 5 = Aceptada
        solicitudCreditoRepository.save(solicitud);
    }
    
    public void cancelarSolicitud(Integer id) {
    	SolicitudCredito solicitud = solicitudCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Verificas que esté en estado válido para cancelar
        if (solicitud.getEstatus() == 0) {
            throw new IllegalStateException("La solicitud ya está cancelada");
        }

        solicitud.setEstatus(0); // 0 = Cancelada
        solicitudCreditoRepository.save(solicitud);
    }
    
}
