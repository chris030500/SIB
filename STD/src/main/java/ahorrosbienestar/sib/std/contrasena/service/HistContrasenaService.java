package ahorrosbienestar.sib.std.contrasena.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.repository.IHistContrasenaRepository;


@Service
public class HistContrasenaService {
	
	private IHistContrasenaRepository iHistContrasenaRepository;
	
	@Autowired
	public HistContrasenaService(IHistContrasenaRepository iHistContrasenaRepository) {
		this.iHistContrasenaRepository = iHistContrasenaRepository;
	}
	
	//Metodo para dar de alta Historico Contraseña
		public HistContrasena save(HistContrasena iHistContrasena) {
			return iHistContrasenaRepository.save(iHistContrasena);
		}
		
		//Metodo paraa buscar por id de Historico Contraseña
		public Optional<HistContrasena> findById(Integer id){
			return iHistContrasenaRepository.findById(id);
		}
		
		//Metodo para buscar todos los Historico Contraseña
		public List<HistContrasena> findAll(){
			return iHistContrasenaRepository.findAll();
		}
		
		//Metodo para editar Historico Contraseña
		public HistContrasena edit(HistContrasena histContrasena) {
			return iHistContrasenaRepository.save(histContrasena);
		}
}
