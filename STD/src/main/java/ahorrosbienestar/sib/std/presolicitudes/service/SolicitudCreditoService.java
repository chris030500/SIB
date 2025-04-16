package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;
import ahorrosbienestar.sib.std.presolicitudes.repository.SolicitudCreditoRepository;

@Service
public class SolicitudCreditoService {

	@Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;
	
	public SolicitudCredito guardarSolicitud(SolicitudCredito solicitud) {
        return solicitudCreditoRepository.save(solicitud);
    }
	
	public Optional<SolicitudCredito> obtSolicitudPorId(Integer id) {
        return solicitudCreditoRepository.findById(id);
    }
	
	public Page<SolicitudCredito> listarSolicitudes(Integer idEmpleado, Pageable pageable) {
	    return solicitudCreditoRepository.findAllByEjecutivoOrderByIdDesc(idEmpleado, pageable);
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
    public void actualizarEstatusSolicitudes(List<Map<String, String>> solicitudes) {
        for (Map<String, String> solicitud : solicitudes) {
            String noSolicitudStr = solicitud.get("NoSolicitud");
            String autoriza = solicitud.get("Autoriza SI/NO");

            if (noSolicitudStr == null || autoriza == null) {
                System.out.println("Datos faltantes en la solicitud.");
                continue;
            }

            try {
                Integer noSolicitud = Integer.parseInt(noSolicitudStr);
                Optional<SolicitudCredito> solicitudOptional = solicitudCreditoRepository.findById(noSolicitud);

                if (solicitudOptional.isPresent()) {
                    SolicitudCredito solicitudCredito = solicitudOptional.get();

                    if ("SI".equalsIgnoreCase(autoriza)) {
                        solicitudCredito.setEstatus(3); // Aprobada
                        System.out.println("Solicitud " + noSolicitud + " ha sido APROBADA.");
                    } else {
                        solicitudCredito.setEstatus(4); // Rechazada
                        System.out.println("Solicitud " + noSolicitud + " ha sido RECHAZADA.");
                    }

                    solicitudCreditoRepository.save(solicitudCredito);
                } else {
                    System.out.println("No se encontró la solicitud con ID " + noSolicitud);
                }
            } catch (NumberFormatException e) {
                System.out.println("Número de solicitud inválido: " + noSolicitudStr);
            }
        }
    }
}
