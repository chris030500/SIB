package ahorrosbienestar.sib.std.prevencion_pld.bitacora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;

@Repository
public interface BitacoraPldRepository extends JpaRepository<BitacoraPldModel, Integer> {

}
