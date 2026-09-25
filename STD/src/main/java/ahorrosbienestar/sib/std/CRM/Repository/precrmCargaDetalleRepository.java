package ahorrosbienestar.sib.std.CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.CRM.model.precrmCargaDetalle;


@Repository
public interface precrmCargaDetalleRepository
        extends JpaRepository<precrmCargaDetalle, Long> {


    boolean existsByNombreProspectoIgnoreCaseAndTelefonoContactoAndResultadoCarga(
        String nombreProspecto,
        String telefonoContacto,
        String resultadoCarga
    );


    List<precrmCargaDetalle>
        findByFkCargaAndResultadoCargaInOrderByFilaExcelAsc(
            Long fkCarga,
            List<String> resultados
        );

}