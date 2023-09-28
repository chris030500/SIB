package ahorrosbienestar.sib.std.cobranza.procesoCarga.bitacora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.cobranza.procesoCarga.bitacora.model.ProcesoCargaBitacoraModel;

@Repository
public interface ProcesoCargaBitacoraRepository extends JpaRepository<ProcesoCargaBitacoraModel, Integer> {
	
	@Query(value = "SELECT pc.PK_ID_PROCESO, b.VC_NOMBRE, pc.I_CANTIDAD_REGISTRO, pc.I_TOTAL_MONTO, pc.DT_FECHA_CARGA, u.VC_NOMBRE_USUARIO FROM SIB.TBL_PROCESO_CARGA_CNV pc"
			+ " JOIN TBL_BANCO_CNV b ON pc.FK_BANCO = b.PK_ID_BANCO"
			+ " JOIN TBL_CAT_USUARIOS u ON pc.FK_USUARIO_CARGA = u.PK_ID_USUARIO"
			+ " ORDER BY pc.PK_ID_PROCESO DESC", nativeQuery = true)
	public List<ProcesoCargaBitacoraModel> listaBitacora();

}
