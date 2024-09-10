package ahorrosbienestar.sib.std.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;

public interface ICatalogoRepository extends JpaRepository<Catalogo, Integer>{

	@Query(value = "SELECT * FROM TBL_OPC_BUSQUEDA WHERE I_ID_GRUPO =:id_group", nativeQuery = true)
	public List<Catalogo> generarOpciones(@Param ( "id_group" ) Integer id);
	
	@Query(value = "SELECT V_VALOR FROM TBL_OPC_BUSQUEDA WHERE I_ID_GRUPO =:id_group and I_OPCION =:i_opcion ", nativeQuery = true)
	public String obtDescripcion(@Param ( "id_group" ) Integer id,@Param ( "i_opcion" ) Integer i_opcion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_OPC_BUSQUEDA SET V_VALOR=:costo WHERE I_ID_GRUPO =:grupo and I_OPCION =:opcion ", nativeQuery = true)
	public int guardarCosto(String costo, int grupo, int opcion);

}
