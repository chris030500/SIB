package ahorrosbienestar.sib.std.catalogo_contratos.service;

import java.util.List;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatCrontato;
import ahorrosbienestar.sib.std.catalogo_contratos.repository.CatCrontatoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CatCrontatoService {
	
	private CatCrontatoRepository catalogoRepository;

	
	public List<CatCrontato> listaCatalogo() {
		return catalogoRepository.findAll();
	}
	
}
