package ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.service;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.repository.CuentasClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CuentasClienteService {

	@Autowired
	private CuentasClienteRepository clienteRepository;

	public List<CuentasClienteModel> clientePorNumCliente(BigInteger cliente) {
		return clienteRepository.clientePorNumCliente(cliente);
	}
	
	public List<CuentasClienteModel> clientePorNomCliente(String nombre) {
		return clienteRepository.clientePorNomCliente(nombre);
	}

	public List<CuentasClienteModel> clientePorNumCredito(BigInteger credito) {
		return clienteRepository.clientePorNumCredito(credito);
	}

	public CuentasClienteModel findById(int id) {
		return clienteRepository.findById(id).orElse(null);
	}
	
	public List<CuentasClienteModel> clientePorIdCtaCliente(int cuenta) {
		return clienteRepository.clientePorIdCtaCliente(cuenta);
	}

}
