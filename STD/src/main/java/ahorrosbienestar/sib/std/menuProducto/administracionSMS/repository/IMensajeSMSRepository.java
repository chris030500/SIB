package ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.MensajeSMS;

@Repository
public interface IMensajeSMSRepository extends JpaRepository<MensajeSMS, Integer>{

}
