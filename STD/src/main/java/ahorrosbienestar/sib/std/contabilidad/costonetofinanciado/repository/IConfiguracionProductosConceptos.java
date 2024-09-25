package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ConfiguracionProductosConceptos;

@Repository
public interface IConfiguracionProductosConceptos extends JpaRepository<ConfiguracionProductosConceptos,Integer>{

	
	@Query(value = "select PK_ID, "
			+ "FK_ID_PRODUCTO, "
			+ "FK_ID_CONCEPTO, "
			+ "I_ESTATUS, "
			+ "I_USUARIO_REGISTRA, "
			+ "DT_FECHA_REGISTRO from SIB.TBL_CAT_CONF_CONCEPTOS_PRORRATEOS "
			+ "where FK_ID_PRODUCTO =:codProd", nativeQuery = true)
	public List<ConfiguracionProductosConceptos> obtenerXproducto(int codProd);

	@Query(value = "select * from SIB.TBL_CAT_CONF_CONCEPTOS_PRORRATEOS "
			+ "where FK_ID_PRODUCTO =:codProd and FK_ID_CONCEPTO=:idConcepto", nativeQuery = true)
	public ConfiguracionProductosConceptos existeRegistro(int codProd, int idConcepto);

	
}
