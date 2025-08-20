package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.ClienteRowDTO;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto.OperacionRowDTO;

import java.sql.Timestamp;
import java.util.List;

public interface AuditoriaReadRepository {
  List<OperacionRowDTO> nextOperacionesBatch(Timestamp lastF, long lastId,
                                             Timestamp ini, Timestamp fin, int batch);
  List<ClienteRowDTO>   nextClientesBatch   (Timestamp lastF, int  lastId,
                                             Timestamp ini, Timestamp fin, int batch);
}