package ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model.DoctoVideoModel;


@Repository
public interface DoctoVideoRepository extends JpaRepository<DoctoVideoModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_DOCTO_VIDEO WHERE FK_SOLICITUD_ENVIO =  :solicitud", nativeQuery = true)
	public DoctoVideoModel findByIdentificador(@Param("solicitud") Integer solicitud);
}
