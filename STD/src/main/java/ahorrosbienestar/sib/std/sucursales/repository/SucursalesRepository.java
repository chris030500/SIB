package ahorrosbienestar.sib.std.sucursales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;

@Repository
public interface SucursalesRepository extends JpaRepository<SucursalesModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_SUCURSALES WHERE VC_NOMBRESUCURSAL LIKE CONCAT(:sucursal , '%')", nativeQuery = true)
	public List<SucursalesModel> buscarSucursalPorNombre(@Param("sucursal") String sucursal);
	
	@Query(value = "SELECT * FROM SIB.TBL_SUCURSALES WHERE VC_NOMBRESUCURSAL = :sucursal", nativeQuery = true)
	public SucursalesModel sucursalPorNombre(@Param("sucursal") String sucursal);

}
