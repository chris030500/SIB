package ahorrosbienestar.sib.std.presolicitudes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;
import ahorrosbienestar.sib.std.presolicitudes.repository.PuestoRepository;

import java.util.List;

@Service
public class PuestoService {

    @Autowired
    private PuestoRepository puestoRepository;

    public List<Puesto> obtenerTodosLosPuestos() {
        return puestoRepository.findAll();
    }
}

