package ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ImagenesExpedienteCredito;


@Repository
public interface IImagenesExpedienteCreditoRepository extends JpaRepository<ImagenesExpedienteCredito, Integer> {
	
	@Query( value = "SELECT * "
			+ "FROM TBL_SCCB_IMAGENES_EXP_CREDITO "
			+ "WHERE I_EXPEDIENTE_ID = :idExpediente ", nativeQuery = true)
	public List<ImagenesExpedienteCredito> getDetalleExpedienteCredito(@Param("idExpediente") Integer idExpediente);
}
