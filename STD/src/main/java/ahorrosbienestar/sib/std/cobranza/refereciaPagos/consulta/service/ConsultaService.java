package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.repository.ConsultaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	public ConsultaModel save(ConsultaModel model) {
		return consultaRepository.save(model);
	}

	public ConsultaModel findById(int id) {
		return consultaRepository.findById(id).orElse(null);
	}

	public ConsultaModel consultaPorCuentaCliente(int cuenta) {
		return consultaRepository.consultaPorCuentaCliente(cuenta);
	}

}
