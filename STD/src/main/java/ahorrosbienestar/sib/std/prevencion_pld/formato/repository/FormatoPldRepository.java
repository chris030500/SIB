package ahorrosbienestar.sib.std.prevencion_pld.formato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.formato.model.FormatoPldModel;

@Repository
public interface FormatoPldRepository extends JpaRepository<FormatoPldModel, Integer> {

}
