package ahorrosbienestar.sib.std.credito.reportes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.reportes.repository.ISolicitudesAutorizadasRepository;

@Service
public class SolicitudesAutorizadasService {
	
	@Autowired
	private ISolicitudesAutorizadasRepository autorizadasRepository;
	
	public List<Map<String, Object>> getClientes(String valor) {
		return autorizadasRepository.getClientes(valor);
	}
	
	public List<Map<String, Object>> getCustomerCellphones(String valor) {
		return autorizadasRepository.getCustomerCellphones(valor);
	}
}
