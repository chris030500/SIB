package ahorrosbienestar.sib.std.credito.campaniastemporales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.campaniastemporales.model.CampaniasTemporalesModel;

@Repository
public interface CampaniasTemporalesRepository extends JpaRepository<CampaniasTemporalesModel,Integer>{

}
