package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.HistorialReporteRepository;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
 
import java.util.Date;
import java.util.List;
@Service
public class GuardarHistorialReporte {
 
    private final HistorialReporteRepository historialRepo;
 
    public GuardarHistorialReporte(HistorialReporteRepository historialRepo) {
        this.historialRepo = historialRepo;
    }
 
    public void registrarHistorial(Date fechaGeneracion, String usuario, String nombreArchivo) {
        HistorialReporte historial = new HistorialReporte();
        //historial.setFechaGeneracion(fechaGeneracion);
        //historial.setUsuario(usuario);
        //historial.setNombreArchivo(nombreArchivo);
        //historial.setConfirmado(false);
 
        historialRepo.save(historial);
    }
 
    public List<HistorialReporte> findAll() {
        return historialRepo.findAll();
    }
    
    public List<HistorialReporte> procesoVobo() {
        return historialRepo.procesoVobo();
    }
    
 
    
   // public List<HistorialReporte> obtenerHistorial() {
     //   return historialRepo.findAll(Sort.by(Sort.Direction.DESC, "fechaGeneracion"));
    //}
}