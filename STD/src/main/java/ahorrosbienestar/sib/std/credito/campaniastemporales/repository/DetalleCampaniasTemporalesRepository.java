package ahorrosbienestar.sib.std.credito.campaniastemporales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;

@Repository
public interface DetalleCampaniasTemporalesRepository extends JpaRepository<DetalleCampaniasTemporalesModel,Integer>{

}
