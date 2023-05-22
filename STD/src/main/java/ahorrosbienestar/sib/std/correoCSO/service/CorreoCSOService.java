package ahorrosbienestar.sib.std.correoCSO.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.correoCSO.repository.ICorreoCSORepository;

@Service
public class CorreoCSOService {
	private ICorreoCSORepository iCorreoCSORepository;
	
	@Autowired
	public CorreoCSOService(ICorreoCSORepository iCorreoCSORepository) {
		this.iCorreoCSORepository = iCorreoCSORepository;
	}
}
