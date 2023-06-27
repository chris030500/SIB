package ahorrosbienestar.sib.std.catalogo_contratos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatContrato;
import ahorrosbienestar.sib.std.catalogo_contratos.repository.CatContratoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CatContratoService {

	@Autowired
	private CatContratoRepository catalogoRepository;

	public List<CatContrato> listaCatalogo() {
		return catalogoRepository.listaCatalogo();
	}
	
	public List<CatContrato> listaContratosActivos() {
		return catalogoRepository.listaContratosActivos();
	}

	public CatContrato findById(Integer idContrato) {
		return catalogoRepository.findById(idContrato).orElse(null);
	}

	public CatContrato guardarContrato(CatContrato catContrato) {
		return catalogoRepository.save(catContrato);
	}

}
