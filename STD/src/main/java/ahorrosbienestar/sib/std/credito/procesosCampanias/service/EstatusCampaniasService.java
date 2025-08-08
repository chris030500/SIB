package ahorrosbienestar.sib.std.credito.procesosCampanias.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.EstatusCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor


public class EstatusCampaniasService {
	
	@Autowired
	private EstatusCampaniasRepository repository;
	
	public List<EstatusCampaniasModel> getEstatusCampaniasModel() {
        return repository.findAll();
    }
	
}
