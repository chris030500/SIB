package ahorrosbienestar.sib.std.presolicitudes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import ahorrosbienestar.sib.std.presolicitudes.dto.CondicionesDTO;
import ahorrosbienestar.sib.std.presolicitudes.repository.CondicionesRepository;

@Service
public class CondicionesService {

	private static final Logger logger =
	        LoggerFactory.getLogger(CondicionesService.class);

    @Autowired
    private CondicionesRepository condicionesRepository;

    public List<String> obtenerProductosUnicosPorPuesto(Integer empresa, Integer puesto) {
        return condicionesRepository.findDistinctProductosByEmpresa(empresa, puesto);
    }

    public List<String> obtenerProductosUnicosPorEmpresa(Integer empresa) {
        return condicionesRepository.findDistinctProductosByPuesto(empresa);
    }

    public List<String> obtenerPlazosPorProducto(String producto) {
        return condicionesRepository.findPlazosByDescripcionProducto(producto);
    }

    public CondicionesDTO obtenerCondiciones(String producto, Integer empresa, Integer tipoEmp) {
        try {
            Object[] resultado = condicionesRepository.obtenerCondicionesRaw(empresa, tipoEmp, producto);

            if (resultado == null || resultado.length < 1 || !(resultado[0] instanceof Object[])) {
                logger.warn("El resultado es nulo o el primer elemento no es un array.");
                return null;
            }

            Object[] datos = (Object[]) resultado[0];

            if (datos.length < 8) {
                logger.warn("El array datos tiene menos de 8 elementos: " + Arrays.toString(datos));
                return null;
            }

            double montoMin = (datos[0] instanceof Number) ? ((Number) datos[0]).doubleValue() : 0.0;
            double montoMax = (datos[1] instanceof Number) ? ((Number) datos[1]).doubleValue() : 0.0;
            Integer empresaId = (datos[2] instanceof Integer) ? (Integer) datos[2] : null;
            String productoStr = (datos[3] instanceof String) ? (String) datos[3] : "";
            Integer tipoEmpId = (datos[4] instanceof Integer) ? (Integer) datos[4] : null;
            double tasa = (datos[5] instanceof Number) ? ((Number) datos[5]).doubleValue() : 0.0;
            String frecuencia = (datos[6] instanceof String) ? (String) datos[6] : "";
            String plazo = (datos[7] instanceof String) ? (String) datos[7] : "";

            return new CondicionesDTO(montoMin, montoMax, empresaId, productoStr, tipoEmpId, tasa, frecuencia, plazo);

        } catch (Exception e) {
            logger.error("Error al obtener condiciones.", e);
            return null;
        }
    }
}