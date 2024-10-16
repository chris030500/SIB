package ahorrosbienestar.sib.std.menuproducto.administracionSMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.adminSMS;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.repository.IAdminSMSRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class adminSMSService {
	
	@Autowired
	private IAdminSMSRepository iAdminSMSRepository;

	
	public List<adminSMS> busquedaPorSeguimiento(String cliente, String sucur, String proce) {
		return iAdminSMSRepository.obtenerMensajes(cliente, sucur, proce);
	}
}
