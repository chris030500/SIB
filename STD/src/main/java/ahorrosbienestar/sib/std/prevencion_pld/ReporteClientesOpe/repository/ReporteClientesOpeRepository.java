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
	@Query("select new ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.OperacionRowDTO(" +
		       " r.I_ClienteID, r.VC_nombre_completo, r.VC_tipo_persona, r.DT_fecha_operacion, r.N_monto_operacion," +
		       " r.VC_destinatario_credito, r.VC_instrumento_monetario, r.VC_tipo_producto_cliente," +
		       " r.VC_descripcion_movimiento, r.VC_referencia_movimiento, r.C_naturaleza_movimiento," +
		       " r.I_num_transaccion, r.I_numero_contrato) " +
		       "from ReporteClientesOpeModel r " +
		       "where r.I_num_transaccion is not null " +
		       "  and r.DT_fecha_operacion between :ini and :fin " +
		       "order by r.DT_fecha_operacion asc, r.I_num_transaccion asc")
		List<OperacionRowDTO> fetchOpsSinPaginacion(@Param("ini") LocalDate ini,
		                                            @Param("fin") LocalDate fin,
		                                            Pageable page);


// ================== CLIENTES / USUARIOS ==================
@Query("select new ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.ClienteRowDTO(" +
	       " r.VC_tipo_cliente, r.VC_estatus_cliente, r.DT_FechaAlta, r.I_ClienteID," +
	       " r.DT_FechaNacimiento, r.VC_nombre_completo, r.VC_GENERO," +
	       " r.VC_entidad_federativa_nacimiento, r.VC_pais_nacimiento, r.VC_nacionalidad," +
	       " r.VC_numero_telefono, r.VC_Correo, r.VC_representante_legal, r.VC_tipo_persona," +
	       " r.VC_rfc, r.C_curp, r.VC_Peps, r.VC_ocupacion," +
	       " r.I_ActividadINEGIID, r.VC_ActividadINEGI," +
	       " r.I_ActividadBMXID, r.VC_ActividadBMX, r.I_ActividadSCIANID, r.VC_nivel_riesgo," +
	       " r.VC_calle, r.VC_numeroCasa, r.VC_num_interior, r.VC_colonia, r.VC_municipio, r.VC_estado, r.VC_cp," +
	       " r.VC_TIPO_OCUPACION, r.VC_LUGARDETRABAJO, r.I_ActividadFRID, r.VC_Actividad_FR," +
	       " r.I_SECTORECONOMICOID, r.VC_SECTORECONOMICO, r.N_volumen_creditos) " +
	       "from ReporteClientesOpeModel r " +
	       "where r.I_num_transaccion is null " +
	       "  and r.DT_FechaAlta between :ini and :fin " +
	       "order by r.DT_FechaAlta asc, r.I_ClienteID asc")
	List<ClienteRowDTO> fetchClientesSinPaginacion(@Param("ini") LocalDate ini,
	                                               @Param("fin") LocalDate fin,
	                                               Pageable page);

}

