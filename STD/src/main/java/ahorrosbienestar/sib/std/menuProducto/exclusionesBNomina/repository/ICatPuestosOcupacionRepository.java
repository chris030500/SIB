package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model.CatPuestosOcupacion;

@Repository
public interface ICatPuestosOcupacionRepository extends JpaRepository<CatPuestosOcupacion, Integer> {
	
	@Query( value = "SELECT T1.PK_I_PUESTO_OCUPACION_ID AS id_puesto, T1.VC_DESCRIPCION AS desc_puesto "
			+ "FROM SIB.TBL_CAT_PUESTOS_OCUPACION T1 "
			+ "WHERE T1.I_INDICADOR_DEPURAR = 0 ", nativeQuery = true)
	List<Map<String, Object>> getPuestosOcupacionActivos();

}
