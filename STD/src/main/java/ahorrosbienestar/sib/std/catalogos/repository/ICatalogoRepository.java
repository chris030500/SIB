package ahorrosbienestar.sib.std.catalogos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;

public interface ICatalogoRepository extends JpaRepository<Catalogo, Integer>{

	@Query(value = "SELECT * FROM TBL_OPC_BUSQUEDA WHERE I_ID_GRUPO =:id_group", nativeQuery = true)
	public List<Catalogo> generarOpciones(@Param ( "id_group" ) Integer id);

}
