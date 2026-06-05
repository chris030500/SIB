package ahorrosbienestar.sib.std.marketing.creditosdesembolsados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.marketing.creditosdesembolsados.model.ConfigCreditosDesembolsados;

@Repository
public interface IConfigCreditosDesembolsadosRepository extends JpaRepository<ConfigCreditosDesembolsados, Integer> {

    @Query(value = "SELECT COUNT(*) "
            + "FROM SIB.TBL_CONFIG_CREDITOS_DESEMBOLSADOS "
            + "WHERE VC_NOMBRE_CONFIG LIKE CONCAT('%', :nombreConfig, '%') "
            + "AND VC_VALOR_CONFIG LIKE CONCAT('%', :usuario, '%')",
            nativeQuery = true)
    public int searchConfiguracion(
            @Param("nombreConfig") String nombreConfig,
            @Param("usuario") String usuario);

}