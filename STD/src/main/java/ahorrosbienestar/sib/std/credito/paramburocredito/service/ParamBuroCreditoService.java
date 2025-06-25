package ahorrosbienestar.sib.std.credito.paramburocredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.paramburocredito.model.ParamBuroCredito;
import ahorrosbienestar.sib.std.credito.paramburocredito.repository.ParamBuroCreditoRepository;

@Service
public class ParamBuroCreditoService {
	
	@Autowired
	private ParamBuroCreditoRepository paramBuroCreditoRepository;
	
	public List<ParamBuroCredito> obtParametrosBC() {
        return paramBuroCreditoRepository.findAll();
    }
	
	public void guardarConfig(List<ParamBuroCredito> paramBC) {
	      this.paramBuroCreditoRepository.saveAll(paramBC);
	}

	/*public int buscarConfiguracion(String campoParametro, String usuario) {
		// TODO Auto-generated method stub
		return this.paramBuroCreditoRepository.buscarConfiguracion(campoParametro,usuario);
	}*/
}
