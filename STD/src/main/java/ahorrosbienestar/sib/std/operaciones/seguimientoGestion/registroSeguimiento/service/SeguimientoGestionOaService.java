package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.repository.RelacionCodigosRepository;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.repository.SeguimientoGestionOaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeguimientoGestionOaService {

	@Autowired
	private SeguimientoGestionOaRepository repository;
	
	@Autowired
	private RelacionCodigosRepository repo;

	public SeguimientoGestionOaModel save(SeguimientoGestionOaModel model) {
		return repository.save(model);
	}

	public SeguimientoGestionOaModel findById(Integer id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<SeguimientoGestionOaModel> listaGestionPorOferta(int asignacion) {
		return repository.listaGestionPorOferta(asignacion);
	}
	
	public SeguimientoGestionOaModel editarSeguimientoGestion(int id, LocalDate fechaCita, LocalDate fechaGestion, int codigoRelacion, String descripcion, String telefono) {
		SeguimientoGestionOaModel seguimientoOa = repository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado: " + id));
	        
		RelacionCodigosModel codigo = repo.findById(codigoRelacion)
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado: " + codigoRelacion));
		
		seguimientoOa.setDt_fecha_cita(fechaCita);
		seguimientoOa.setDt_fecha_gestion_realizada(fechaGestion);
		seguimientoOa.setRelacionCodigosModel(codigo);
		seguimientoOa.setVc_descripcion(descripcion);
		seguimientoOa.setI_telefono(telefono);
	        
	        return repository.save(seguimientoOa);
	}
	
	public SeguimientoGestionOaModel inactivarSeguimientoGestion(int id) {
		SeguimientoGestionOaModel seguimientoOa = repository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado: " + id));
		
		seguimientoOa.setI_estatus(0);;
	        
	    return repository.save(seguimientoOa);
	}
	
	public SeguimientoGestionOaModel realizarSeguimientoGestion(int id) {
		SeguimientoGestionOaModel seguimientoOa = repository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado: " + id));
		
		seguimientoOa.setI_estatus(2);;
	        
	    return repository.save(seguimientoOa);
	}
}
