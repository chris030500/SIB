package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;

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

	public Page<Map<String, Object>> listarSolicitudes(Integer idEmpleado, Pageable pageable) {
	    Page<Object[]> rawResults = solicitudCreditoRepository.buscarSolicitudesRaw(idEmpleado, pageable);

	    return rawResults.map(row -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", row[0]);
	        map.put("empleado", row[1]);
	        map.put("fechaSolicitud", row[2]);
	        map.put("empresa", row[3]);
	        map.put("montoSolicitado", row[4]);
	        map.put("montoDescontar", row[5]);
	        map.put("nombreSucursal", row[6]);
	        map.put("estatus", row[7]);
	        return map;
	    });
	}

	public Page<Map<String, Object>> listarSolicitudesPorSucursales(List<Integer> sucursales, Pageable pageable) {
	    Page<Object[]> rawResults = solicitudCreditoRepository.buscarSolicitudesPorSucursalesRaw(sucursales, pageable);

	    return rawResults.map(row -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", row[0]);
	        map.put("empleado", row[1]);
	        map.put("fechaSolicitud", row[2]);
	        map.put("empresa", row[3]);
	        map.put("montoSolicitado", row[4]);
	        map.put("montoDescontar", row[5]);
	        map.put("nombreSucursal", row[6]);
	        map.put("estatus", row[7]);
	        return map;
	    });
	}

	public Page<Map<String, Object>> listarTodasSolicitudes(Pageable pageable) {
	    Page<Object[]> resultados = solicitudCreditoRepository.buscarTodasSolicitudesRaw(pageable);

	    return resultados.map(row -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("idSolicitud", row[0]);
	        map.put("empleado", row[1]);
	        map.put("fechaSolicitud", row[2]);
	        map.put("empresa", row[3]);
	        map.put("montoSolicitado", row[4]);
	        map.put("montoDescontar", row[5]);
	        map.put("nombreSucursal", row[6]);
	        map.put("estatus", row[7]);
	        return map;
	    });
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
						solicitudCredito.setFechaAprobacion(LocalDate.now());
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
