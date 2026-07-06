//DetallePEPsRepository
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;

@Repository
public interface DetallePEPsRepository
        extends JpaRepository<DetallePEPsModel, Integer> {

	@Query("SELECT d FROM DetallePEPsModel d WHERE d.iClienteId = :clienteId")
	List<DetallePEPsModel> buscarPorClienteId(
	        @Param("clienteId") int clienteId
	);


}