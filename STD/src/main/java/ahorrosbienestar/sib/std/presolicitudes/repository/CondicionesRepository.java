package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import ahorrosbienestar.sib.std.presolicitudes.model.Condiciones;

public interface CondicionesRepository extends JpaRepository<Condiciones, Integer> {

	// Método para obtener productos únicos por empresa
	@Query("SELECT DISTINCT c.vcProducto FROM Condiciones c WHERE c.iEmpresa = :empresa AND c.iTipoemp = :puesto")
	List<String> findDistinctProductosByEmpresa(@Param("empresa") Integer empresa, @Param("puesto") Integer puesto);
	
	@Query("SELECT DISTINCT c.vcProducto FROM Condiciones c WHERE c.iEmpresa = :empresa")
	List<String> findDistinctProductosByPuesto(@Param("empresa") Integer empresa);

	@Query("SELECT DISTINCT c.vcPlazo FROM Condiciones c WHERE c.vcPlazo IS NOT NULL AND c.vcProducto = :producto AND c.iEmpresa = :empresa")
	List<String> findDistinctPlazoByProductoAndEmpresa(@Param("producto") String producto,
			@Param("empresa") Integer empresa);

	@Query(value = "SELECT " +
            "MIN(I_MONTOMIN), " +
            "MAX(I_MONTOMAX), " +
            "I_EMPRESA, " +
            "VC_PRODUCTO, " +
            "I_TIPOEMP, " +
            "DC_TASA, " +
            "VC_FRECUENCIA, " +
            "VC_PLAZO " +
            "FROM SIB.TBL_CONDICIONES_CONVENIOS " +
            "WHERE I_EMPRESA = :empresa " +
            "AND I_TIPOEMP = :tipoEmp " +
            "AND VC_PRODUCTO = :producto " +
            "GROUP BY I_EMPRESA, VC_PRODUCTO, I_TIPOEMP, DC_TASA, VC_FRECUENCIA, VC_PLAZO " +
            "LIMIT 1", 
            nativeQuery = true)
Object[] obtenerCondicionesRaw(@Param("empresa") Integer empresa, 
                               @Param("tipoEmp") Integer tipoEmp, 
                               @Param("producto") String producto);


}
