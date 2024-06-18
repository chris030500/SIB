package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.model.ConsultarRelacionesModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.repository.ConsultarRelacionesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultarRelacionesService {

	@Autowired
	private ConsultarRelacionesRepository repository;

	public List<ConsultarRelacionesModel> relacionesActivas(String valor) {
		return repository.relacionesActivas(valor);
	}
	
	public ConsultarRelacionesModel nombreRelacion(String valor) {
		return repository.nombreRelacion(valor);
	}

}
