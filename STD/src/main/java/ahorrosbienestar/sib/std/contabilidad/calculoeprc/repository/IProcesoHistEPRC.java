package ahorrosbienestar.sib.std.contabilidad.calculoeprc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ahorrosbienestar.sib.std.contabilidad.calculoeprc.model.EPRC;




public interface IProcesoHistEPRC extends JpaRepository<EPRC, Integer> {
	@Query(value = "select PK_ID_PROCESO as proceso "
			+ ",case MONTH(t1.DT_FECHA_CORTE) "
			+ "when 1 then 'Enero' "
			+ "when 2 then 'Febrero' "
			+ "when 3 then 'Marzo' "
			+ "when 4 then 'Abril' "
			+ "when 5 then 'Mayo' "
			+ "when 6 then 'Junio' "
			+ "when 7 then 'Julio' "
			+ "when 8 then 'Agosto' "
			+ "when 9 then 'Septiembre' "
			+ "when 10 then 'Octubre' "
			+ "when 11 then 'Noviembre' "
			+ "when 12 then 'Diciembre' "
			+ "END mes_proceso "
			+ ",case when t1.DT_FECHA_CORTE is not null then DATE_FORMAT(t1.DT_FECHA_CORTE, '%Y-%m-%d') else  COALESCE(t1.DT_FECHA_CORTE, '0000-00-00') end fecha_corte "
			+ ",case when t3.VC_NOMBRE_USUARIO is not null then t3.VC_NOMBRE_USUARIO else COALESCE(t3.VC_NOMBRE_USUARIO , '--.--') end usuario_vobo "
			+ ",case when t1.DT_FECHA_REGISTRO_VOBO is not null then DATE_FORMAT(t1.DT_FECHA_REGISTRO_VOBO, '%Y-%m-%d') else COALESCE(t1.DT_FECHA_REGISTRO_VOBO, '0000-00-00') end fecha_registro_vobo "
			+ "from SIB.TBL_REG_PROCESO_EPRC t1 "
			+ "left join SIB.TBL_CAT_USUARIOS t3 on t3.PK_ID_USUARIO = t1.I_USUARIO_VOBO "
			+ "where t1.I_VOBO = 1 and I_ACTIVO=1 "
			+ "order by t1.PK_ID_PROCESO desc", nativeQuery = true) 
	public List<EPRC> obtProcesoEPRC();
}
