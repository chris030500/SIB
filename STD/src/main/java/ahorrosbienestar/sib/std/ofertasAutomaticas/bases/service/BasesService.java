package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.repository.IBasesRepository;

@Service
public class BasesService {

	private IBasesRepository iBasesRepository;

	@Autowired
	public BasesService(IBasesRepository iBasesRepository) {
		this.iBasesRepository = iBasesRepository;
	}

	public Bases save(Bases bases) {
		return iBasesRepository.save(bases);
	}

	public Optional<Bases> findById(Integer id) {
		return iBasesRepository.findById(id);
	}

	public List<Bases> findAll() {
		return iBasesRepository.findAll();
	}

	public Bases actualizarUsuario(Bases bases) {
		if (iBasesRepository.existsById(bases.getPk_id_base())) {
			// Actualizar el registro del usuario
			return iBasesRepository.save(bases);
		} else {
			return null;
		}
	}
	
	public boolean esHorarioValido(int horaInicio, int horaFin) {
        int horaActual = obtenerHoraActual(); // Implementa la lógica para obtener la hora actual
        System.out.println("horaActual: " + horaActual);
        return horaActual >= horaInicio && horaActual <= horaFin;
    }

    public boolean esDiaValido(int diaInicio, int diaFin) {
        int diaActual = obtenerDiaActual(); // Implementa la lógica para obtener el día actual
        System.out.println("diaActual: " + diaActual);
        return diaActual >= diaInicio && diaActual <= diaFin;
    }

    public boolean esDiaValido(int diaInicio) {
        int diaActual = obtenerDiaActual(); // Implementa la lógica para obtener el día actual
        System.out.println("diaActual: " + diaActual);
        return diaActual == diaInicio;
    }

    public int obtenerHoraActual() {
    	Calendar cal = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
        cal.setTimeZone(timeZone);

        boolean esHorarioVerano = timeZone.inDaylightTime(cal.getTime());

        int horaActual = cal.get(Calendar.HOUR_OF_DAY);

        if (esHorarioVerano) {
            horaActual -= 1;
        }

        if (horaActual < 0) {
            horaActual = 23;
        }

        return horaActual;

    }

    public int obtenerDiaActual() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

	public int actParams(Integer basesID, String dComienzoProceso, String dFinalProceso, String hComienzoProceso,
			String hFinalProceso, String mPeriodoEjecucion, String rutaArchivo) {
		// TODO Auto-generated method stub
		return iBasesRepository.actParams(basesID,dComienzoProceso,dFinalProceso,hComienzoProceso,hFinalProceso,mPeriodoEjecucion,rutaArchivo);
	}


}
