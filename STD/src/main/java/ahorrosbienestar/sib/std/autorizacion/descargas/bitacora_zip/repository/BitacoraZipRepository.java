package ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.model.BitacoraZipModel;

@Repository
public interface BitacoraZipRepository extends JpaRepository<BitacoraZipModel, Integer> {
	
	

}
