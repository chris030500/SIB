package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model.ExclusionSucursales;

@Repository
public interface IExclusionSucursalesRepository extends JpaRepository<ExclusionSucursales, Integer> {
	
	@Query( value = "SELECT T1.PK_I_SUCURSAL_ID AS id_sucursal, T1.VC_NOMBRE AS nombre_sucursal "
			+ "FROM SIB.TBL_EXCLUSION_SUCURSALES T1 "
			+ "WHERE T1.I_INDICADOR_DEPURAR = 0 ", nativeQuery = true)
	List<Map<String, Object>> getSucursalesActivos();
	
	@Query( value = "SELECT T1.PK_I_SUCURSAL_ID AS id_sucursal, T1.VC_NOMBRE AS nombre_sucursal "
			+ "FROM SIB.TBL_EXCLUSION_SUCURSALES T1 "
			+ "WHERE T1.I_INDICADOR_DEPURAR = 1 ", nativeQuery = true)
	List<Map<String, Object>> getSucursalesDepurar();

}
