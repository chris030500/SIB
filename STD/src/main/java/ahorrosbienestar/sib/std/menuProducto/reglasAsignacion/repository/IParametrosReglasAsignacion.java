package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglasAsignacion;

@Repository
public interface IParametrosReglasAsignacion extends JpaRepository<ParametrosReglasAsignacion, Integer>{

	@Query(value = "SELECT * FROM TBL_PARAMS_REGLAS_ASIGNACION WHERE FK_ID_REGLA=:idRegla", nativeQuery = true)
	public List<ParametrosReglasAsignacion> obtRegParams(@Param("idRegla") Integer idRegla);

}
