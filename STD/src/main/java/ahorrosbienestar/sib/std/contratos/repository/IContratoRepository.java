package ahorrosbienestar.sib.std.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contratos.model.Contrato;

@Repository
public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
	

	@Query(value = "SELECT I_NO_SOLICITUD, VC_NOMBRE_CLIENTE FROM TBL_DTOS_CONTRATO", nativeQuery = true)
	public Contrato findByIdContrato(Integer contrato);
	
	//@Query(value = "SELECT ")
	//public Producto findAll(int producto);

}
