package ahorrosbienestar.sib.std.credito.paramburocredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCreditoModel;
import ahorrosbienestar.sib.std.credito.paramburocredito.repository.ParamBuroCreditoRepository;

@Service
public class ParamBuroCreditoService {
	
	@Autowired
	private ParamBuroCreditoRepository paramBuroCreditoRepository;
	
	public List<ParamBuroCreditoModel> obtParametrosBC() {
        return paramBuroCreditoRepository.findAll();
    }
	
	public void guardarConfig(List<ParamBuroCreditoModel> paramBC) {
	      this.paramBuroCreditoRepository.saveAll(paramBC);
	}

	public ParamBuroCreditoModel obtParamBuro (int valorCampaniaID) {
		  return this.paramBuroCreditoRepository.obtParamBuro(valorCampaniaID);
	
	}

	public ParamBuroCreditoModel save(ParamBuroCreditoModel registroParamBC) {
		return this.paramBuroCreditoRepository.save(registroParamBC);
	}

	/*public int buscarConfiguracion(String campoParametro, String usuario) {
		// TODO Auto-generated method stub
		return this.paramBuroCreditoRepository.buscarConfiguracion(campoParametro,usuario);
	}*/
}
