package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.List;
import java.util.Optional;

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
	
	public Page<SolicitudCredito> listarSolicitudes(Pageable pageable) {
	    return solicitudCreditoRepository.findAllByOrderByIdDesc(pageable);
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
	
}
