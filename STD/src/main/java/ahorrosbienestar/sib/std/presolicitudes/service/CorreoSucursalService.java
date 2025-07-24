package ahorrosbienestar.sib.std.presolicitudes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.CorreoSucursal;
import ahorrosbienestar.sib.std.presolicitudes.repository.CorreoSucursalRepository;

@Service
public class CorreoSucursalService {

    @Autowired
    private CorreoSucursalRepository correoSucursalRepository;

    public String obtenerCorreoSucursal(int sucursal) {
        // Buscar la sucursal en la base de datos
        Optional<CorreoSucursal> correoSucursalOpt = correoSucursalRepository.findByiSuc(sucursal);

        // Si no se encuentra, devolver un correo por defecto
        if (correoSucursalOpt.isPresent()) {
            return correoSucursalOpt.get().getVcCorreo();
        } else {
            return "default@empresa.com"; // Valor por defecto
        }
    }
}