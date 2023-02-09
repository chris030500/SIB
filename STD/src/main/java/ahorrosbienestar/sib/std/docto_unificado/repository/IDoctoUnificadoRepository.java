package ahorrosbienestar.sib.std.docto_unificado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;

@Repository
public interface IDoctoUnificadoRepository extends JpaRepository<DoctoUnificado, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_DOCTO_UNIFICADO WHERE FK_ID_SOLICITUD_ENVIO = :solicitudEnvio" , nativeQuery = true)
	public Optional<DoctoUnificado> findByFkIdSolicituEnvio(@Param("solicitudEnvio") Integer solicitudEnvio);
	
}
