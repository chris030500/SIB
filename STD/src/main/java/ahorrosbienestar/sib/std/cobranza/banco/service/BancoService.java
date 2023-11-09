package ahorrosbienestar.sib.std.cobranza.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.banco.model.BancoModel;
import ahorrosbienestar.sib.std.cobranza.banco.repository.BancoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BancoService {

	@Autowired
	private BancoRepository bancoRepository;
	
	public BancoModel findById(Integer id) {
		return bancoRepository.findById(id).orElse(null);
	}
	
	public List<BancoModel> listaBancosPorEstatus(int estatus) {
		return bancoRepository.listaBancosPorEstatus(estatus);
	}
	
}
