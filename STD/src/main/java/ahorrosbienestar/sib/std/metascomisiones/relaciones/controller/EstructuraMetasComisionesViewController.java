package ahorrosbienestar.sib.std.metascomisiones.relaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstructuraMetasComisionesViewController {

    @GetMapping("/metas-comisiones/relaciones/gestion")
    public String vistaGestionRelaciones() {
        return "metasComisiones/relaciones";
    }
}
