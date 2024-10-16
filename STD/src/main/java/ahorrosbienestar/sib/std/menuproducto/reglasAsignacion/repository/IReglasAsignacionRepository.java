package ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.model.ReglasAsignacion;


@Repository
public interface IReglasAsignacionRepository extends JpaRepository<ReglasAsignacion, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_REGLAS_ASIGNACION", nativeQuery = true)
	public List<ReglasAsignacion> obtReglasAsignacion();

	@Query(value = "SELECT count(*) as NUMERO FROM SIB.TBL_REGLAS_ASIGNACION WHERE I_ESTATUS = 1", nativeQuery = true)
	public int existeActivo();
}
