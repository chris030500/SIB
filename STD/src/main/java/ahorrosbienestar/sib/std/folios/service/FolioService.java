package ahorrosbienestar.sib.std.folios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.folios.model.Folios;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;

@Service
public class FolioService {

	private IFolioRepository iFolioRepository;
	
	@Autowired
	public FolioService(IFolioRepository iFolioRepository) {
		this.iFolioRepository = iFolioRepository;
	}
	
	public int existeFolio(String noficio) {
		return iFolioRepository.existeFolio(noficio);
	}

	public Folios save(Folios altaFolio) {
		return iFolioRepository.save(altaFolio);
		
	}
	
	//Metodo para buscar todos los usuarios
	public List<Folios> findAll(){
		return iFolioRepository.findAll();
	}

	public int modFolio(String noficio, String dt_recepcion, String vc_asunto, Integer i_dirigido, String vc_puesto,
			Integer i_tipo_oficio, Integer i_organo_emite, String dt_registro, Integer i_Plazo, String dt_Vencimiento,
			Integer i_estatus, String vc_ruta) {
		// TODO Auto-generated method stub
		return iFolioRepository.modFolio(noficio,dt_recepcion,vc_asunto,i_dirigido,vc_puesto,i_tipo_oficio,i_organo_emite,dt_registro,i_Plazo,dt_Vencimiento,i_estatus,vc_ruta);
	}
	
}
