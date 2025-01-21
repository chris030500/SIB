package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ConfiguracionSMSCredito;

@Repository
public interface IConfiguracionSMSCreditoRepository extends JpaRepository<ConfiguracionSMSCredito, Integer> {
	
	@Query( value = "SELECT T1.PK_ID_CONFIG_SMS AS idConfig, "
			+ "T1.FK_VC_TP_CREDITO AS tipoCredito, "
			+ "T1.VC_MENSAJE_ENVIAR AS mensaje, "
			+ "T2.VC_NOMBRE_USUARIO AS userRegistra, "
			+ "DATE_FORMAT(T1.DT_FECHA_REGISTRO, '%d-%m-%Y %H:%i:%s') AS fechaRegistro, "
			+ "T3.VC_NOMBRE_USUARIO AS userModifica, "
			+ "DATE_FORMAT(T1.DT_FECHA_MODIFICACION, '%d-%m-%Y %H:%i:%s') AS fechaModificacion "
			+ "FROM SIB.TBL_CONFIG_SMS T1 "
			+ "LEFT JOIN SIB.TBL_CAT_USUARIOS T2 ON T2.PK_ID_USUARIO = T1.I_USR_REGISTRA "
			+ "LEFT JOIN SIB.TBL_CAT_USUARIOS T3 ON T3.PK_ID_USUARIO = T1.I_USR_MODIFICA "
			+ "WHERE T1.FK_VC_ESTATUS_SOLCRE = 'A' ", nativeQuery = true)
	public List<Map<String, Object>> getAllSMSConfig();
	
	@Query( value = "SELECT * FROM SIB.TBL_CONFIG_SMS "
			+ "WHERE PK_ID_CONFIG_SMS = :idSMSConfig ", nativeQuery = true )
	public Optional<ConfiguracionSMSCredito> getCurrentSMSConfig(Integer idSMSConfig);

}
