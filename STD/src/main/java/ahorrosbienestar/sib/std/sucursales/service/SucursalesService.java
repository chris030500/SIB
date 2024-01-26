package ahorrosbienestar.sib.std.sucursales.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;
import ahorrosbienestar.sib.std.sucursales.repository.SucursalesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SucursalesService {
	
	@Autowired
	private SucursalesRepository repository;
	
	public List<SucursalesModel> buscarSucursalPorNombre(String sucursal) {
		return repository.buscarSucursalPorNombre(sucursal);
	}
	
	public SucursalesModel sucursalPorNombre(String sucursal) {
		return repository.sucursalPorNombre(sucursal);
	}
	

}
