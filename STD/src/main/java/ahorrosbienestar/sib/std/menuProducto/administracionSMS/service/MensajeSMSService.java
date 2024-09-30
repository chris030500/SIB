package ahorrosbienestar.sib.std.menuproducto.administracionSMS.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.MensajeSMS;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.repository.IMensajeSMSRepository;

@Service
public class MensajeSMSService {

	private static final int BATCH_SIZE = 1000;

	private final IMensajeSMSRepository mensajeSMSRepository;

	public MensajeSMSService(IMensajeSMSRepository mensajeSMSRepository) {
		this.mensajeSMSRepository = mensajeSMSRepository;
	}

	public MensajeSMS editarMensaje(int id, String nuevoMensaje) {
		MensajeSMS mensajeExistente = mensajeSMSRepository.findById(id).orElse(null);
		if (mensajeExistente != null) {
			mensajeExistente.setDescripcionMensaje(nuevoMensaje); // Actualizar el campo de mensaje
			return mensajeSMSRepository.save(mensajeExistente); // Guardar el mensaje actualizado en la base de datos
		}
		return null; // Devolver null si no se encuentra el mensaje con el ID dado
	}

	public MensajeSMS inactivarMensaje(int id, int estatus) {
		MensajeSMS mensajeExistente = mensajeSMSRepository.findById(id).orElse(null);
		if (mensajeExistente != null) {
			mensajeExistente.setEstatus(estatus); // Actualizar el campo de mensaje
			return mensajeSMSRepository.save(mensajeExistente); // Guardar el mensaje actualizado en la base de datos
		}
		return null; // Devolver null si no se encuentra el mensaje con el ID dado
	}

	@Transactional
	public void actualizarEstadoMensajes(int id, List<Integer> idsMensajes) {
		for (int i = 0; i < idsMensajes.size(); i += BATCH_SIZE) {
			int end = Math.min(i + BATCH_SIZE, idsMensajes.size());
			List<Integer> batch = idsMensajes.subList(i, end);
			mensajeSMSRepository.actualizarEstadoMensajes(id, batch);
		}
	}

	@Transactional
	public void actualizarEstadoMensajesRecibidos(int id, int estatus) {
		mensajeSMSRepository.actualizarEstadoMensajesRecibidos(id, estatus);
	}

}
