package ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model.DoctoVideoModel;

@Repository
public interface DoctoVideoRepository extends JpaRepository<DoctoVideoModel, Integer> {
	
	

}
