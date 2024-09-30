package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.ConveniosNomina;


@Repository
public interface IConveniosNominaRepository extends JpaRepository<ConveniosNomina, Integer> {
	
	@Query( value = "SELECT T1.PK_I_CONVENIO_NOMINA_ID AS id_convenio, T1.VC_DESCRIPCION AS desc_convenio "
			+ "FROM SIB.TBL_CONVENIOS_NOMINA T1 "
			+ "WHERE T1.VC_DESCRIPCION LIKE %:descConvenio% "
			+ "AND T1.I_INDICADOR_DEPURAR = 0 "
			+ "LIMIT 100 ", nativeQuery = true)
	List<Map<String, Object>> getConveniosNominaActivos(@Param("descConvenio") String descConvenio);
	
	@Query( value = "SELECT T1.PK_I_CONVENIO_NOMINA_ID AS id_convenio, T1.VC_DESCRIPCION AS desc_convenio "
			+ "FROM SIB.TBL_CONVENIOS_NOMINA T1 "
			+ "WHERE T1.VC_DESCRIPCION LIKE %:descConvenio% "
			+ "AND T1.I_INDICADOR_DEPURAR = 1 "
			+ "LIMIT 100 ", nativeQuery = true)
	List<Map<String, Object>> getConveniosNominaDepurar(@Param("descConvenio") String descConvenio);

}
