package ahorrosbienestar.sib.std.prevencion_pld.listas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;

@Repository
public interface ListasPldRepository extends JpaRepository<ListasPldModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_LISTAS_QEQ"
			+ " WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<ListasPldModel> listasActivas();

}
