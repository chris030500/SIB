package ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.model.ParametrosReglasAsignacion;

@Repository
public interface IParametrosReglasAsignacionRepository extends JpaRepository<ParametrosReglasAsignacion, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_PARAMS_REGLAS_ASIGNACION WHERE FK_ID_REGLA=:idRegla", nativeQuery = true)
	public List<ParametrosReglasAsignacion> obtRegParams(@Param("idRegla") Integer idRegla);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_PARAMS_REGLAS_ASIGNACION WHERE FK_ID_REGLA=:idRegla AND I_TIPO_ASIGNADO=:slAsignar AND I_TIPO_CLIENTE=:tipoCliente AND I_TIPO_CARTERA=:slcTipoCartera AND I_PRIORIDAD=:tipoPrioridad", nativeQuery = true)
	public int existeRegistro(Integer slAsignar, Integer tipoCliente, Integer slcTipoCartera, Integer idRegla, Integer tipoPrioridad);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_PARAMS_REGLAS_ASIGNACION WHERE I_TIPO_ASIGNADO=:slAsignar AND I_TIPO_CLIENTE=:tipoCliente AND I_TIPO_CARTERA=:slcTipoCartera AND I_PRIORIDAD=:tipoPrioridad", nativeQuery = true)
	public int existeRegistroAct(Integer slAsignar, Integer tipoCliente, Integer slcTipoCartera, Integer tipoPrioridad);
		
}
