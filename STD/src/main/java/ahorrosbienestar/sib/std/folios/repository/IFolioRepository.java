package ahorrosbienestar.sib.std.folios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.folios.model.Folios;

@Repository
public interface IFolioRepository extends JpaRepository<Folios, Integer>{

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_FOLIOS  WHERE VC_FOLIO = :noficio ", nativeQuery = true)
	public int existeFolio(@Param("noficio") String noficio);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_FOLIOS "
			+ "SET DT_FECHA_RECEPCION =:dt_recepcion "
			+ ",VC_ASUNTO=:vc_asunto "
			+ ",I_DIRIGIDO =:i_dirigido "
			+ ",VC_PUESTO=:vc_puesto "
			+ ",I_TIPO_OFICIO=:i_tipo_oficio "
			+ ",I_ORGANO_EMITE=:i_organo_emite "
			+ ",DT_FECHA_REGISTRO=:dt_registro "
			+ ",I_PLAZO_ATENCION=:i_Plazo "
			+ ",DT_FECHA_VENCIMIENTO=:dt_Vencimiento "
			+ ",I_ESTATUS=:i_estatus "
			+ ",VC_RUTA_OFICIO=:vc_ruta "
			+ " WHERE VC_FOLIO =:noficio", nativeQuery = true)
	public int modFolio(@Param("noficio") String noficio,@Param("dt_recepcion") String dt_recepcion, @Param("vc_asunto") String vc_asunto, @Param("i_dirigido") Integer i_dirigido, @Param("vc_puesto") String vc_puesto,
	@Param("i_tipo_oficio") Integer i_tipo_oficio, @Param("i_organo_emite") Integer i_organo_emite,@Param("dt_registro") String dt_registro,@Param("i_Plazo") Integer i_Plazo, @Param("dt_Vencimiento") String dt_Vencimiento,
	@Param("i_estatus") Integer i_estatus,@Param("vc_ruta") String vc_ruta);

	@Modifying
	@Transactional
	@Query(value="UPDATE SIB.TBL_FOLIOS SET I_ESTATUS=:estatus WHERE PK_ID_FOLIO =:folioID ",nativeQuery = true)
	public int actEstatus(@Param("folioID") Integer folioID, @Param("estatus") Integer estatus);

	

}
