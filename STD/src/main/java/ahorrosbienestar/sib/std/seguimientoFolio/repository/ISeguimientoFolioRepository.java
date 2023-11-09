package ahorrosbienestar.sib.std.seguimientoFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.seguimientoFolio.model.SeguimientoFolio;

@Repository
public interface ISeguimientoFolioRepository extends JpaRepository<SeguimientoFolio, Integer> {

	
	@Modifying
	@Transactional
	@Query(value="UPDATE TBL_SEGUIMIENTO SET VC_DESC_SEGUIMIENTO=:desc_seguimiento, I_CONFIRMACION_RESPONSABLE=:estatus, DT_FECHA_MOD=now(),I_USUARIO_MOD=:usuario WHERE PK_ID_SEGUIMIENTO =:idSeguimiento ",nativeQuery = true)
	public int modificarSeguimiento(@Param("idSeguimiento") Integer idSeguimiento, @Param("desc_seguimiento") String desc_seguimiento, @Param("estatus") Integer estatus, @Param("usuario") Integer usuario);

}
