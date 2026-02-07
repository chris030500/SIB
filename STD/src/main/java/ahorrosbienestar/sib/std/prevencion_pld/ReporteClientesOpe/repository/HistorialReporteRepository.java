package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;

@Repository
public interface HistorialReporteRepository
        extends JpaRepository<HistorialReporte, Integer> {

    List<HistorialReporte> findAllByOrderByFechaGeneracionDesc();



    // Para llenar la pestaña Historial (lo que ya llamas como procesoVobo())
    @Query("SELECT h FROM HistorialReporte h ORDER BY h.fechaGeneracion DESC")
    List<HistorialReporte> procesoVobo();

    // ¿Ya existe el pendiente? (evita duplicados al dar click varias veces al amarillo)
    @Query("""
    	    SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END
    	    FROM HistorialReporte h
    	    WHERE h.estatus = 0
    	      AND LOWER(h.tipoReporte) = LOWER(:tipo)
    	      AND h.fechaInicio = :ini
    	      AND h.fechaFin = :fin
    	      AND h.i_usuario.id = :idUsuario
    	""")
    	boolean existePendiente(Integer idUsuario, String tipo,
    	                        LocalDateTime ini, LocalDateTime fin);

    // Trae el pendiente exacto para hacer VoBo
    @Query("""
    	    SELECT h
    	    FROM HistorialReporte h
    	    WHERE h.estatus = 0
    	      AND UPPER(h.tipoReporte) = 'CLIENTES'
    	    ORDER BY h.fechaGeneracion DESC
    	""")
    	Optional<HistorialReporte> buscarUltimoPendienteClientes();
    }


 