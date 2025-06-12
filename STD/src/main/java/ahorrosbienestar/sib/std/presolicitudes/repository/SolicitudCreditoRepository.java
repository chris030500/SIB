package ahorrosbienestar.sib.std.presolicitudes.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;

@Repository
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Integer> {
	
	Page<SolicitudCredito> findAllByEjecutivoOrderByIdDesc(Integer ejecutivo, Pageable pageable);
	
	Page<SolicitudCredito> findAllBySucursalIn(List<Integer> sucursales, Pageable pageable);
	
	@Query("SELECT s FROM SolicitudCredito s WHERE s.id IN :ids")
    List<SolicitudCredito> findByIds(@Param("ids") List<Integer> ids);
	
	@Query("SELECT COUNT(s) FROM SolicitudCredito s WHERE s.empresa = :empresa AND s.estatus = 5")
    int contarSolicitudesPorEmpresa(@Param("empresa") String empresa);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE TBL_SOLICITUDES_CREDITO SET FK_ID_PROCESO = :idProceso, I_ESTATUS = 2 WHERE VC_EMPRESA = :empresa AND I_ESTATUS = 5", nativeQuery = true)
	int actualizarSolicitudesPorEmpresa(@Param("empresa") String empresa, @Param("idProceso") Integer idProceso);
}