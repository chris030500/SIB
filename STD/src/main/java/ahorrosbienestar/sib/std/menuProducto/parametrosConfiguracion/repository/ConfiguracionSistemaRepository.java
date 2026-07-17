package ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.repository;
 
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
 
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.model.ConfiguracionSistema;
 
 
@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Integer> {
 
    @Query(value = "SELECT COUNT(*) FROM SIB.TBL_CONFIGURACION_PARAMETROS_PRODUCTOS "
            + "WHERE VC_NOMBRE_CAMPO LIKE CONCAT('%', :campoParametro, '%') "
            + "AND VC_CONFIGURACION LIKE CONCAT('%', :usuario, '%')",
            nativeQuery = true)
    public int buscarConfiguracion(
            @Param("campoParametro") String campoParametro,
            @Param("usuario") String usuario);
 
}