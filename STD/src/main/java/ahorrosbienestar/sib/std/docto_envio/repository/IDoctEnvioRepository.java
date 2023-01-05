package ahorrosbienestar.sib.std.docto_envio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Repository
public interface IDoctEnvioRepository extends JpaRepository<DoctEnvio, Integer>{

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_DOCTO_ENVIO SET I_TOTAL_HOJAS = :totalHojas WHERE PK_ID_DOCTO_ENVIO = :idDoctoEnvio", nativeQuery=true)
	public int totalHojas( @Param("totalHojas") Integer totalHojas, @Param("idDoctoEnvio") Integer idDoctoEnvio);
	
	@Query(value = "SELECT * FROM SIB.TBL_DOCTO_ENVIO WHERE PK_ID_DOCTO_ENVIO = :idDoctoEnvio", nativeQuery = true)
	public DoctEnvio obtenerDoctoEnvio(@Param("idDoctoEnvio") Integer idDoctoEnvio);
}
