package ahorrosbienestar.sib.std.credito.tiposCampanias.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.CampaniaTemporalDTO;
import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.ProcesosxCampaniaTemDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.repository.TiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class TiposCampaniasService {

	@Autowired
	private TiposCampaniasRepository repository;
	public List<TiposCampaniasDTO> listaCampanias(String valor) {
		List<Object[]> resultados = repository.listaCampanias(valor);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
		//return repository.listaCampanias(valor);
	}
	
	public TiposCampaniasModel obtenerCampaniaPorId(Integer id) {
	    return repository.mostrarCampania(id);
	}

	public List<String> validarConfiguracionGeneralParaExtraccion(Integer idCampania) {
		List<String> camposFaltantes = new ArrayList<>();
		TiposCampaniasModel campania = repository.mostrarCampania(idCampania);

		if (campania == null) {
			camposFaltantes.add("Campaña no encontrada");
			return camposFaltantes;
		}

		if (esNuloOCero(campania.getI_tipo_campania())) {
			camposFaltantes.add("Tipo Campaña");
		}
		if (esNulo(campania.getI_ventana_anios_mayor())) {
			camposFaltantes.add("Ventana mayor que");
		}
		if (esNulo(campania.getI_ventana_anios())) {
			camposFaltantes.add("Ventana");
		}
		if (esNuloOMenorIgualCero(campania.getI_edad_maxima())) {
			camposFaltantes.add("Edad Máxima");
		}
		if (esNuloOMenorIgualCero(campania.getI_edad_minima())) {
			camposFaltantes.add("Edad Mínima");
		}
		if (esNuloOMenorIgualCero(campania.getD_monto_minimo())) {
			camposFaltantes.add("Monto Mínimo");
		}
		if (esNuloOMenorIgualCero(campania.getD_monto_maximo())) {
			camposFaltantes.add("Monto Máximo");
		}
		if (esNuloOMenorIgualCero(campania.getD_porc_cubierto_capital())) {
			camposFaltantes.add("Porcentaje Cubierto Capital");
		}
		if (esNulo(campania.getI_dias_gracia())) {
			camposFaltantes.add("Días Gracia");
		}
		if (esNulo(campania.getI_dias_mora())) {
			camposFaltantes.add("Días Mora");
		}
		if (esNuloOMenorIgualCero(campania.getI_plazo_maximo_anios())) {
			camposFaltantes.add("Plazo Máximo Años");
		}
		if (esNuloOMenorIgualCero(campania.getI_minimo_vida_credito())) {
			camposFaltantes.add("Mínimo Vida");
		}
		if (esNulo(campania.getI_vigencia_bc())) {
			camposFaltantes.add("Vigencia BC");
		}
		if (esNulo(campania.getI_vigencia_campania())) {
			camposFaltantes.add("Vigencia Campaña");
		}
		if (esNulo(campania.getI_creditos_liquidados())) {
			camposFaltantes.add("Créditos Liquidados");
		}
		if (esTextoVacio(campania.getVc_es_renovacion())) {
			camposFaltantes.add("Es Renovación");
		}
		if (esNuloOCero(campania.getI_convivencia())) {
			camposFaltantes.add("Convivencia Campaña");
		}
		if (esTextoVacio(campania.getVc_productos())) {
			camposFaltantes.add("Productos");
		}
		if (esNuloOCero(campania.getI_prod_credito_id_oferta())) {
			camposFaltantes.add("ProdCredito");
		}
		if (esNulo(campania.getD_porc_inc_oferta())) {
			camposFaltantes.add("PorceIncremento");
		}
		if (esNuloOMenorIgualCero(campania.getD_tasa_ordinaria_oferta())) {
			camposFaltantes.add("TasaOrdinaria");
		}
		if (esNuloOCero(campania.getI_activa_oferta())) {
			camposFaltantes.add("Activa Oferta");
		}
		if (esNuloOMenorIgualCero(campania.getD_monto_maximo_oferta())) {
			camposFaltantes.add("Monto Máximo Oferta");
		}
		if (esNuloOCero(campania.getI_tipo_buro())) {
			camposFaltantes.add("Tipo Archivo Buró");
		}
		if ("S".equalsIgnoreCase(campania.getVc_es_renovacion())
				&& esNuloOMenorIgualCero(campania.getI_minimo_vida_credito_r())) {
			camposFaltantes.add("Mínimo Vida del Crédito Renovación");
		}

		return camposFaltantes;
	}

	private boolean esNulo(Integer valor) {
		return valor == null;
	}

	private boolean esNulo(Double valor) {
		return valor == null;
	}

	private boolean esNuloOCero(Integer valor) {
		return valor == null || valor <= 0;
	}

	private boolean esNuloOMenorIgualCero(Integer valor) {
		return valor == null || valor <= 0;
	}

	private boolean esNuloOMenorIgualCero(Double valor) {
		return valor == null || valor <= 0;
	}

	private boolean esTextoVacio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
	
	@Transactional
    public boolean actualizarCampania(TiposCampaniasModel campania) {
        int filasAfectadas = repository.actualizarCampania(
            campania.getPk_id_tipo_campania(),
            campania.getI_tipo_campania(),
            campania.getI_ventana_anios(),
            campania.getI_ventana_anios_mayor(),
            campania.getI_edad_maxima(),
            campania.getI_edad_minima(),
            campania.getD_monto_minimo(),
            campania.getD_monto_maximo(),
            campania.getD_porc_cubierto_capital(),
            campania.getI_dias_gracia(),
            campania.getI_dias_mora(),
            campania.getI_plazo_maximo_anios(),
            campania.getI_minimo_vida_credito(),
            campania.getI_vigencia_bc(),
            campania.getI_vigencia_campania(),
            campania.getI_creditos_liquidados(),
            campania.getVc_es_renovacion(),
            campania.getI_convivencia(),
            campania.getVc_productos(),
            campania.getI_prod_credito_id_oferta(),
            campania.getD_porc_inc_oferta(),
            campania.getD_tasa_ordinaria_oferta(),
            campania.getI_activa_oferta(),
            campania.getD_monto_maximo_oferta(),
            campania.getI_minimo_vida_credito_r(),
            campania.getI_estatus(),
            campania.getDt_fecha_registro(),
            campania.getI_tipo_buro()
            
        );
        return filasAfectadas > 0;
    }
	
	@Transactional
	public boolean crearCampania(TiposCampaniasModel c) {
	    int filas = repository.insertarCampania(
	    	c.getVc_tipo_campania(),
	    	c.getI_tipo_campania(),
	    	c.getI_ventana_anios(),
	    	c.getI_ventana_anios_mayor(),
	        c.getI_edad_maxima(),
	        c.getI_edad_minima(),
	        c.getD_monto_minimo(),
	        c.getD_monto_maximo(),
	        c.getD_porc_cubierto_capital(),
	        c.getI_dias_gracia(),
	        c.getI_dias_mora(),
	        c.getI_plazo_maximo_anios(),
	        c.getI_minimo_vida_credito(),
	        c.getI_vigencia_bc(),
	        c.getI_vigencia_campania(),
	        c.getI_creditos_liquidados(),
	        c.getVc_es_renovacion(),
	        c.getI_convivencia(),
	        c.getVc_productos(),
	        c.getI_prod_credito_id_oferta(),
	        c.getD_porc_inc_oferta(),
	        c.getD_tasa_ordinaria_oferta(),
	        c.getI_activa_oferta(),
	        c.getD_monto_maximo_oferta(),
	        c.getI_minimo_vida_credito_r(),
	        c.getI_estatus(),
	        c.getDt_fecha_registro(),
	        c.getI_tipo_buro()
	    );
	    return filas > 0;
	}
	
	public TiposCampaniasModel save(TiposCampaniasModel model) {
		return repository.save(model);
	}

	public List<TiposCampaniasDTO> buscarCampaniasActivas(String valorBusqueda) {
		List<Object[]> resultados = repository.listaCampaniasActivas(valorBusqueda);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}
	
	public Optional<TiposCampaniasModel> obtenerCampaniaxID(int idCampania) {
		return repository.findById(idCampania);
	}

	public List<TiposCampaniasDTO> buscarCampaniasTemporales(String valorBusqueda) {
		List<Object[]> resultados = repository.buscarCampaniasTemporales(valorBusqueda);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}
	
	public List<TiposCampaniasDTO> listaCampaniasRecurrentes() {
		List<Object[]> resultados = repository.listaCampaniasRecurrentes();
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}

	public List<TiposCampaniasModel> campaniasReportes() {
	
		   return repository.findAll();
	}
	
	public List<TiposCampaniasModel> getTiposCampaniasModel() {
        return repository.findAll();
    }

	public int existeNombre(String nombreCampania, int idcampania) {
		return repository.existeNombre(nombreCampania,idcampania);
	}

	public List<CampaniaTemporalDTO> obtenerCampañaRegistrada(int valorCampaniaID) {
		
		List<Object[]> resultados = repository.obtenerCampañaRegistrada(valorCampaniaID);
		return resultados.stream()
			    .map(r -> new CampaniaTemporalDTO(
			        (Integer) r[0],       // idCampaniaSeleccionada
			        (String) r[1],        // nombreCampania
			        (Integer) r[2],       // vigenciaMeses
			        (String) r[3],        // fechaRegistro
			        (String) r[4],        // fechaEnvioMC
			        (String) r[5],        // fechaFin
			        (Integer) r[6],       // estatusCampania
			        r[7] != null && ((Number) r[7]).intValue() != 0       // recurrente
			    ))
			    .collect(Collectors.toList());
	}

	public List<ProcesosxCampaniaTemDTO> obtProcesos(Integer idcampania) {
		List<Object[]> resultados = repository.obtProcesos(idcampania);
		 return resultados.stream()
		            .map(r -> new ProcesosxCampaniaTemDTO(
		                (String) r[0],        // codigoCampania
		                (Integer) r[1],       // idProcesoCampania
		                (String) r[2],        // estatus
		                (Integer) r[3],        // estatus
		                (String) r[4],        // dtFechaExtraccion
		                (String) r[5],        // dtFechaInicio
		                (String) r[6],        // dtFechaFin
		                (String) r[7],        // dtFechaEnvioMC
		                (String) r[8],        // dtFechaRegistro
		                (String) r[9]         // nombreUsuario
		            ))
		            .collect(Collectors.toList());
	}

	public String obtProceso(int valorCampaniaID) {
		
		return repository.obtProceso(valorCampaniaID);
	}
	
	/*public List<TiposCampaniasModel> obtenerCampaniasActivos() {
        return repository.listaTiposCampanias(1);
    }*/
	
	/*public List<TiposCampaniasDTO> obtenerCampaniasActivos(Integer valor) {
		List<Object[]> resultados = repository.obtenerCampaniasActivos(valor);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
		//return repository.listaCampanias(valor);
	}*/
	
	/*public TiposCampaniasModel BuscarCampania(TiposCampaniasModel model) {
		return repository.findById(model);
	}*/
	
}
