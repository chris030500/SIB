package ahorrosbienestar.sib.std.metascomisiones.relaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.metascomisiones.security.MetasComisionesAccesoService;

@Controller
public class EstructuraMetasComisionesViewController {

    @Autowired
    private MetasComisionesAccesoService accesoService;

    @GetMapping("/metas-comisiones/relaciones/gestion")
    public String vistaGestionRelaciones() {
        return accesoService.tieneAcceso(Constantes.METAS_COMISIONES_RELACIONES)
                ? "metasComisiones/relaciones"
                : Constantes.RUTA_ERROR_PERMISO;
    }

    @GetMapping("/metas-comisiones/definicion/gestion")
    public String vistaDefinicionMetasSimulacion() {
        return accesoService.tieneAcceso(Constantes.METAS_COMISIONES_DEFINICION)
                ? "metasComisiones/definicion"
                : Constantes.RUTA_ERROR_PERMISO;
    }

    @GetMapping("/metas-comisiones/resultados/gestion")
    public String vistaResultadosComisiones() {
        return accesoService.tieneAcceso(Constantes.METAS_COMISIONES_RESULTADOS)
                ? "metasComisiones/resultados"
                : Constantes.RUTA_ERROR_PERMISO;
    }

    @GetMapping("/metas-comisiones/permisos/gestion")
    public String vistaPermisosMetasComisiones() {
        return accesoService.tieneAcceso(Constantes.METAS_COMISIONES_PERMISOS)
                ? "metasComisiones/permisos"
                : Constantes.RUTA_ERROR_PERMISO;
    }
}
