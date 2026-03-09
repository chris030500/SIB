package ahorrosbienestar.sib.std.sccb.historialCobranza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.historialCobranza.model.Participantes;


@Repository
public interface IParticipantesRepository extends JpaRepository<Participantes, Integer> {
	@Query(value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_PARTICIPANTES "
			+ "WHERE I_CREDITOID = :idCredito ", nativeQuery = true)
	public List<Participantes> getByIdCreditoParticipantes(@Param("idCredito") Integer idCredito);
	
	@Query(value = "SELECT * "
	        + "FROM SIB.TBL_SCCB_PARTICIPANTES "
	        + "WHERE VC_NOMBRECLIENTE LIKE CONCAT('%', :nombre, '%') "
	        + "LIMIT 100",
	        nativeQuery = true)
	public List<Participantes> getByNameParticipantes(
	        @Param("nombre") String nombre);
	
}
