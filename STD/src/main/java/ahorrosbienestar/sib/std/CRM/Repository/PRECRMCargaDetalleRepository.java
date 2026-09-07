package ahorrosbienestar.sib.std.CRM.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.PRECRMCargaDetalle;

@Repository
public interface PRECRMCargaDetalleRepository
        extends JpaRepository<PRECRMCargaDetalle, Long> {

    boolean existsByNombreProspectoIgnoreCaseAndTelefonoContactoAndResultadoCarga(
            String nombreProspecto,
            String telefonoContacto,
            String resultadoCarga
    );
}