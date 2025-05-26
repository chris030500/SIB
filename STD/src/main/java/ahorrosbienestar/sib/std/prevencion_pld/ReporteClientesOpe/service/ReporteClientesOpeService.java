//package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

//import java.io.InputStream;
//import java.util.List;
//import java.util.Map;

//import org.springframework.core.io.InputStreamResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteGenerado;
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ReporteClientesOpeRepository;
//import lombok.RequiredArgsConstructor;

//@Service
//@RequiredArgsConstructor
//public class ReporteClientesOpeService {

    //private final ReporteClientesOpeRepository archivoPEPRepository;
    //private final ReporteClientesOpeRepository reporteClientesOpeRepository;

    // 1. Procesar archivo Excel PEP
    //public void procesarArchivoPEP(MultipartFile archivo) throws Exception {
        //Validar y guardar archivo
       //if (archivo.isEmpty()) throw new IllegalArgumentException("Archivo vacío");
       //archivoPEPRepository.guardarArchivo(archivo);
   // }

    // 2. Obtener historial de archivos PEP
    //public List<Map<String, Object>> obtenerHistorialArchivosPEP() {
        //return archivoPEPRepository.listarHistorial();
    //}

    // 3. Generar reporte
    //public String generarReporte(String fechaInicio, String fechaFin) throws Exception {
        // Lógica para generar el reporte (llamada a SP o proceso)
        //return reporteClientesOpeRepository.generarReporte(fechaInicio, fechaFin);
    //}

    // 4. Consultar historial de reportes
    //public List<ReporteGenerado> consultarHistorialReportes(String fechaInicio, String fechaFin) {
       // return reporteClientesOpeRepository.obtenerHistorial(fechaInicio, fechaFin);
    //}

    // 5. Descargar reporte
    //public ArchivoReporte descargarReporte(Long id) throws Exception {
        //return reporteClientesOpeRepository.descargarPorId(id);
    //}

    // 6. Confirmar o validar reporte
    //public void confirmarReporte(Long id) {
        //reporteClientesOpeRepository.confirmarReporte(id);
    //}
//}
