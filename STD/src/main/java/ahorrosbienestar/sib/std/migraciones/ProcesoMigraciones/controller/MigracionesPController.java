package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.controller;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.service.MigracionesPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/STD/migraciones")
public class MigracionesPController {
	

    @Autowired
    private MigracionesPService service;
    

    @PostMapping("/cambiar-estatus")
    @ResponseBody
    public ResponseEntity<?> cambiarEstatus(@RequestParam("id") Integer id,
                                            @RequestParam("estatus") Integer estatus,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        service.actualizarEstatus(id, estatus, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }
    
    @GetMapping("/obtener-migracion/{id}")
    @ResponseBody
    public ResponseEntity<MigracionesP> obtenerMigracion(@PathVariable("id") Integer id) {
        Optional<MigracionesP> migracion = service.obtenerMigracionPorId(id);
        return migracion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

