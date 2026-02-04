package ahorrosbienestar.sib.std.credito.tiposCampanias.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclisionesClientesModel;

@Repository

public interface ExclusionClienteRepository extends JpaRepository<ExclisionesClientesModel, Integer> {
	
	@Query(value = "SELECT COUNT(*) " +
            "FROM SIB.TBL_EXCLUSION_CLIENTES " +
            "WHERE I_ID_CLIENTE = :idCliente " +
            " AND I_ESTATUS_CLIENTE = 1",
    nativeQuery = true)
	int existeClienteExclusion(@Param("idCliente") Integer idCliente);
	
	@Modifying
	@Transactional // Necesario para ejecutar updates
	@Query(value = "UPDATE SIB.TBL_EXCLUSION_CLIENTES SET "
		+ "I_ESTATUS_CLIENTE = :estatusCliente "
        + "WHERE I_ID_CLIENTE = :idCliente", nativeQuery = true)
	int actualizarExcCliente(
	    @Param("idCliente") Integer idCliente,
	    @Param("estatusCliente") Integer estatusCliente
	);
	
}
