package ahorrosbienestar.sib.std.presolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Integer> {
	
	@Query(value = "SELECT DISTINCT tipEmp.PK_ID_TIPO, tipEmp.VC_DESCRIPCION FROM SIB.TBL_CONDICIONES_CONVENIOS cond \r\n"
			+ "INNER JOIN SIB.TBL_TIPOEMPLEADO tipEmp \r\n"
			+ "ON cond.I_TIPOEMP = tipEmp.PK_ID_TIPO\r\n"
			+ "WHERE I_EMPRESA = :empresa", nativeQuery = true)
	List<Puesto> findDistinctPuestoByEmpresa(@Param("empresa") Integer empresa);
	
}

