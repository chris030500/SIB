package ahorrosbienestar.sib.std.docto_envio.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.docto_envio.repository.IDoctEnvioRepository;

@Service
public class DoctEnvioService {

	private IDoctEnvioRepository iDoctEnvioRepository;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	public DoctEnvioService(IDoctEnvioRepository iDoctEnvioRepository) {
		this.iDoctEnvioRepository = iDoctEnvioRepository;
	}

	// Metodo para guardar la solicitud de envio
	public DoctEnvio save(DoctEnvio doctEnvio) {
		return iDoctEnvioRepository.save(doctEnvio);
	}

	public int totalHojas(int totalHojas, int idDoctoEnvio) {
		return iDoctEnvioRepository.totalHojas(totalHojas, idDoctoEnvio);
	}

	public Optional<DoctEnvio> findById(Integer idDoctoEnvio) {
		return iDoctEnvioRepository.findById(idDoctoEnvio);
	}

	public DoctEnvio obtenerDoctoEnvio(Integer idDoctoEnvio) {
		return iDoctEnvioRepository.obtenerDoctoEnvio(idDoctoEnvio);
	}
	
	public int validarDoctEnvio(Integer estatus, Integer idDoctoEnvio) {
		return iDoctEnvioRepository.validarDoctEnvio(estatus, idDoctoEnvio);
	}

	public List<DoctEnvio> listadoDoctEnvioActivos(Integer envioId) {
		return iDoctEnvioRepository.listadoDoctEnvioActivos(envioId);
	}
	
	public List<DoctEnvio> listaDocumentosPorSolicitudEnvio(@Param("envioID") Integer envioID) {
		return iDoctEnvioRepository.listaDocumentosPorSolicitudEnvio(envioID);
	}

}
