package ahorrosbienestar.sib.std.config_ws.ws_configuraciones.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.config_ws.ws_configuraciones.model.WSConfiguracion;
import ahorrosbienestar.sib.std.config_ws.ws_configuraciones.repository.IWSConfiguracionRepository;

@Service
public class WSConfiguracionService {

	private static final int ESTATUS_ACTIVO = 1;

	@Autowired
	private IWSConfiguracionRepository wsConfiguracionRepository;

	public Optional<WSConfiguracion> buscarConfiguracionActiva(Integer idConfiguracion) {
		if (idConfiguracion == null) {
			return Optional.empty();
		}
		return wsConfiguracionRepository.findConfiguracionActivaPorId(idConfiguracion, ESTATUS_ACTIVO);
	}

	public Optional<WSConfiguracion> buscarConfiguracionActivaPorDescripcion(String descripcion) {
		if (descripcion == null || descripcion.trim().isEmpty()) {
			return Optional.empty();
		}
		return wsConfiguracionRepository.findConfiguracionActivaPorDescripcion(descripcion, ESTATUS_ACTIVO);
	}
}
