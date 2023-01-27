package ahorrosbienestar.sib.std.firma_docto_unificado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;


@Repository
public interface IFirmaDoctoUnificadoRepository extends JpaRepository<FirmaDoctoUnificado, Integer> {

	// Consulta para obtener todas las firmas que se tienen contemplados en el archivo unificado
	@Query(value = "SELECT * FROM SIB.TBL_FIRMA_DOCTO_UNIFICADO where FK_ID_DOCTO_UNIFICADO = :idDoctUnificado ORDER BY I_NO_HOJA ASC;", nativeQuery = true)
	public List<FirmaDoctoUnificado> obtenerCoordenadasFirma(@Param("idDoctUnificado") Integer idDoctUnificado);

}
