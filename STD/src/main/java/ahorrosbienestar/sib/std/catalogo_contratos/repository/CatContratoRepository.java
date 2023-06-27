package ahorrosbienestar.sib.std.catalogo_contratos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatContrato;

@Repository
@Transactional
public interface CatContratoRepository extends JpaRepository<CatContrato, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_CONTRATO WHERE I_ESTATUS = 1 ORDER BY PK_ID_CONTRATO DESC", nativeQuery = true)
	public List<CatContrato> listaContratosActivos();
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_CONTRATO ORDER BY PK_ID_CONTRATO DESC", nativeQuery = true)
	public List<CatContrato> listaCatalogo();

}
