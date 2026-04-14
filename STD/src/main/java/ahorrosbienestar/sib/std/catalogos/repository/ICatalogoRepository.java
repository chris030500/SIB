package ahorrosbienestar.sib.std.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;

public interface ICatalogoRepository extends JpaRepository<Catalogo, Integer>{

	@Query(value = "SELECT * FROM TBL_OPC_BUSQUEDA WHERE I_ID_GRUPO =:id_group AND i_estatus = 1", nativeQuery = true)
	public List<Catalogo> generarOpciones(@Param ( "id_group" ) Integer id);
	
	@Query(value = "select t2.i_opcion i_opcion, t2.v_valor v_valor "
	        + "from SIB.TBL_TIPOS_CAMPANIA t1 inner join SIB.TBL_OPC_BUSQUEDA t2 on t1.i_tipo_buro = t2.i_opcion "
	        + "where t2.i_id_grupo = :id_group "
	        + "and t1.pk_id_tipo_campania = :tipo_campania "
	        + "and t1.I_TIPO_CAMPANIA = 2 "
	        + "union all "
	        + "select t2.i_opcion i_opcion, t2.v_valor v_valor "
	        + "from SIB.TBL_PARAM_BURO_CREDITO t1 inner join SIB.TBL_OPC_BUSQUEDA t2 on t1.I_TIPO__EVALUACION = t2.i_opcion "
	        + "inner join SIB.TBL_TIPOS_CAMPANIA t3 "
	        + "on t3.pk_id_tipo_campania = t1.FK_CAMPANIA_ID "
	        + "where t2.i_id_grupo = :id_group "
	        + "and t1.FK_CAMPANIA_ID = :tipo_campania "
	        + "and t3.I_TIPO_CAMPANIA = 1 "
	        + "and t1.I_ACTIVO = 1 ", nativeQuery = true)
	List<Object[]> listaTipoBuro(@Param("id_group") Integer id_group, @Param("tipo_campania") Integer pk_id_tipo_campania);

}
