package ahorrosbienestar.sib.std.sccb.expedientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.expedientes.model.ImagenesExpedienteAfiliacion;


@Repository
public interface IImagenesExpedienteAfiliacionRepository extends JpaRepository<ImagenesExpedienteAfiliacion, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_IMAGENES_EXP_AFILIACION "
			+ "WHERE I_EXPEDIENTE_ID = :idExpediente ", nativeQuery = true)
	public List<ImagenesExpedienteAfiliacion> getDetalleExpedienteAfiliacion(@Param("idExpediente") Integer idExpediente);
}
