package ahorrosbienestar.sib.std.sucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;

@Repository
public interface SucursalesRepository extends JpaRepository<SucursalesModel, Integer> {
	
	@Query(value = "SELECT t1.*\r\n"
			+ "FROM SIB.TBL_SUCURSALES t1\r\n"
			+ "LEFT JOIN SIB.TBL_TELEFONOS_SUCURSAL_OA t2 ON t1.PK_ID_SUCURSAL = t2.FK_ID_SUCURSAL\r\n"
			+ "WHERE t1.VC_NOMBRESUCURSAL LIKE CONCAT(:sucursal, '%')\r\n"
			+ "AND t2.FK_ID_SUCURSAL IS NULL;", nativeQuery = true)
	public List<SucursalesModel> buscarSucursalPorNombre(@Param("sucursal") String sucursal);
	
	@Query(value = "SELECT * FROM SIB.TBL_SUCURSALES WHERE VC_NOMBRESUCURSAL = :sucursal", nativeQuery = true)
	public SucursalesModel sucursalPorNombre(@Param("sucursal") String sucursal);

	@Query(value = "SELECT t1.*\r\n"
			+ "FROM SIB.TBL_SUCURSALES t1\r\n"
			+ "LEFT JOIN SIB.TBL_TELEFONOS_SUCURSAL_OA t2 ON t1.PK_ID_SUCURSAL = t2.FK_ID_SUCURSAL\r\n"
			+ "WHERE t1.VC_NOMBRESUCURSAL LIKE CONCAT(:sucursal, '%');\r\n", nativeQuery = true)
	public List<SucursalesModel> buscarSucursalPorNombree(@Param("sucursal") String sucursal);
}
