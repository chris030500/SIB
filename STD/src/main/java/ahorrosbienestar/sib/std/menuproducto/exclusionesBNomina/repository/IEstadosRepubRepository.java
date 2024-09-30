package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.EstadosRepub;

@Repository
public interface IEstadosRepubRepository extends JpaRepository<EstadosRepub, Integer> {
	
	@Query( value = "SELECT T1.PK_I_ESTADO_ID AS id_estado, T1.VC_NOMBRE AS nombre_estado "
			+ "FROM SIB.TBL_ESTADOS_REPUB T1", nativeQuery = true)
	List<Map<String, Object>> getEstadosRepub();

}
