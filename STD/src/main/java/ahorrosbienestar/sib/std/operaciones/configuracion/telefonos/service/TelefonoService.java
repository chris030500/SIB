package ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.model.TelefonoModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.repository.TelefonoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TelefonoService {

	@Autowired
	private TelefonoRepository telefonoRepository;

	public List<TelefonoModel> listaTelActivos() {
		return telefonoRepository.findAll();
	}
	
	public TelefonoModel save(TelefonoModel model) {
		return telefonoRepository.save(model);
	}
	
	public TelefonoModel buscarTelefonoPorId(int idTelefono) {
		return telefonoRepository.buscarTelefonoPorId(idTelefono);
	}

}
