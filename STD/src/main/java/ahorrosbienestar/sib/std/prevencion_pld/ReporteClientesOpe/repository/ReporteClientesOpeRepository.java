package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.ClienteRowDTO;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.OperacionRowDTO;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import org.springframework.data.domain.Pageable;                    
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;                                      
import java.util.List;

@Repository
public interface ReporteClientesOpeRepository extends JpaRepository<ReporteClientesOpeModel, Integer> {
 
// ================== OPERACIONES ==================
	@Query("select new ... " +
		       "from ReporteClientesOpeModel r " +
		       "where r.I_num_transaccion is not null " +
		       "  and r.DT_fecha_operacion between :ini and :fin " +
		       "order by r.DT_fecha_operacion asc, r.I_num_transaccion asc")
		List<OperacionRowDTO> fetchOpsSinPaginacion(@Param("ini") LocalDate ini,
		                                            @Param("fin") LocalDate fin,
		                                            Pageable page);


// ================== CLIENTES / USUARIOS ==================
	@Query("select new ... " +
		       "from ReporteClientesOpeModel r " +
		       "where r.I_num_transaccion is null " +
		       "  and r.DT_FechaAlta between :ini and :fin " +
		       "order by r.DT_FechaAlta asc, r.I_ClienteID asc")
		List<ClienteRowDTO> fetchClientesSinPaginacion(@Param("ini") LocalDate ini,
		                                               @Param("fin") LocalDate fin,
		                                               Pageable page);
}

