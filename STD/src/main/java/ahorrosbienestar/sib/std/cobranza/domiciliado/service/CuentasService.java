package ahorrosbienestar.sib.std.cobranza.domiciliado.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.domiciliado.model.CuentasModel;
import ahorrosbienestar.sib.std.cobranza.domiciliado.repository.CuentasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CuentasService {
	
	@Autowired
	private CuentasRepository cuentasRepository;
	
	public CuentasModel datosCuentaClabe(String clabe) {
		return cuentasRepository.datosCuentaClabe(clabe);
	}

}
