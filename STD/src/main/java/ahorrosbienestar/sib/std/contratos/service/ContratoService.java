package ahorrosbienestar.sib.std.contratos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contratos.model.Contrato;
import ahorrosbienestar.sib.std.contratos.repository.IContratoRepository;


@Service
public class ContratoService {

private IContratoRepository iContratoRepository;
	
	@Autowired
	public ContratoService(IContratoRepository iContratoRepository) {
		this.iContratoRepository = iContratoRepository;
	}
	

		public Contrato save(Contrato contrato) {
			return iContratoRepository.save(contrato);
		}
		
		
		public Optional<Contrato> findById(Integer id){
			return iContratoRepository.findById(id);
		}
		
		
		public List<Contrato> findAll(){
			return iContratoRepository.findAll();
		}
		
		public Contrato edit(Contrato producto) {
			return iContratoRepository.save(producto);
		}
		
		public Contrato findByIdDocumento(Integer id) {
			return iContratoRepository.findByIdContrato(id);
					
		}
}
