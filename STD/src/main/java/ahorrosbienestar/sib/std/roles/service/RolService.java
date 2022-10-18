package ahorrosbienestar.sib.std.roles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.repository.IRolRepository;


@Service
public class RolService {
	
	private IRolRepository iRolRepository;
	
	@Autowired
	public RolService(IRolRepository iRolRepository) {
		this.iRolRepository = iRolRepository;
	}
	
	//Metodo para dar de alta rol
		public Rol save(Rol usuario) {
			return iRolRepository.save(usuario);
		}
		
		//Metodo paraa buscar por id de Usuario
		public Optional<Rol> findById(Integer id){
			return iRolRepository.findById(id);
		}
		
		//Metodo para buscar todos los usuarios
		public List<Rol> findAll(){
			return iRolRepository.findAll();
		}
		
		public Rol edit(Rol rol) {
			return iRolRepository.save(rol);
		}

}
