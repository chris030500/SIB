package ahorrosbienestar.sib.std.credito.campaniastemporales.dto;

import java.util.List;
import java.util.ArrayList;
import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;

public class ResultadoCargaExcel {

    private boolean tieneIncidencias;
    private List<DetalleCampaniasTemporalesModel> registros;
    private List<String> incidencias;

    // CONSTRUCTOR CORRECTO
    public ResultadoCargaExcel() {
        this.registros = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.tieneIncidencias = false;
    }

    public boolean isTieneIncidencias() {
        return tieneIncidencias;
    }

    public void setTieneIncidencias(boolean tieneIncidencias) {
        this.tieneIncidencias = tieneIncidencias;
    }

    public List<DetalleCampaniasTemporalesModel> getRegistros() {
        return registros;
    }

    public List<String> getIncidencias() {
        return incidencias;
    }

    public void agregarIncidencia(String mensaje) {
        this.incidencias.add(mensaje);
        this.tieneIncidencias = true;
    }
}