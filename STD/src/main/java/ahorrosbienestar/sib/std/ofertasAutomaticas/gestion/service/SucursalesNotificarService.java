package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.SucursalesNotificar;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository.ISucursalesNotificarRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SucursalesNotificarService {
	
	
	@Autowired
	private ISucursalesNotificarRepository iSucursalesNotificarRepository;
	
	public List<SucursalesNotificar> consultaSucursal(){
		return iSucursalesNotificarRepository.consultaSucursal();
	}

}
