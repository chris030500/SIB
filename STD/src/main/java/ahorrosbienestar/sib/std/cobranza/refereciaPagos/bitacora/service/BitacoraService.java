package ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.model.BitacoraModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.repository.BitacoraRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BitacoraService {
	
	@Autowired
	private BitacoraRepository bitacoraRepository;
	
	public BitacoraModel save(BitacoraModel model) {
		return bitacoraRepository.save(model);
	}

}
