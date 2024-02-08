package ahorrosbienestar.sib.std.menuProducto.administracionSMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.adminSMS;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository.IAdminSMSRepository;

@Service
public class adminSMSService {
	
	private IAdminSMSRepository iAdminSMSRepository;

	@Autowired
	public adminSMSService(IAdminSMSRepository iAdminSMSRepository) {
		this.iAdminSMSRepository = iAdminSMSRepository;
	}
	
	public List<adminSMS> findAll() {
		return iAdminSMSRepository.obtenerMensajes();
	}
}
