package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.ExclusionMunicipioModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.ExclusionClienteService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.ExclusionMunicipioService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.ExclisionesClientesModel;

@Controller
@RequestMapping("/credito")
public class exclusionesCampaniasController {

    @Autowired
    private ExclusionMunicipioService exclusionMunicipioService;
    
    @Autowired
    private ExclusionClienteService exclusionClienteService;

    @GetMapping("/exclusiones")
    public String mostrarExclusiones() {
        System.out.print("Si entra");
        return "credito/exclusiones"; // Renderiza la vista Thymeleaf
    }

    @PostMapping("/cargarMunicipios")
    public ResponseEntity<String> cargarExcel(@RequestParam("archivo") MultipartFile archivo) {
        try {
            exclusionMunicipioService.procesarExcel(archivo);
            return ResponseEntity.ok("Archivo procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/listarMunicipios")
    @ResponseBody
    public List<ExclusionMunicipioModel> listarMunicipios() {
        return exclusionMunicipioService.obtenerTodos();
    }
    
    @GetMapping("/lista_clientes")
	public ResponseEntity<List<ClientesActivosModel>> obtenerClientesPorEmpresaEmpleado() {
		List<ClientesActivosModel> clientes = exclusionMunicipioService.obtenerClientes();
		return ResponseEntity.ok(clientes);
	}
    
    

    @PostMapping("/guardarCliente")
    public String guardarCliente(
        @RequestParam("clienteId") Integer idCliente,
        @RequestParam("buscarEmpleado") String nombreCliente,
        @RequestParam("motivo") String motivo,
        @RequestParam("observaciones") String observaciones,
        RedirectAttributes redirectAttributes
    ) {
    	ExclisionesClientesModel model = new ExclisionesClientesModel();
        model.setIdCliente(idCliente);
        model.setNombreCliente(nombreCliente);
        model.setMotivoExclusion(motivo);
        model.setObservacionesExclusion(observaciones);
        model.setEstatusCliente(1); // ejemplo

        exclusionClienteService.guardarExclusion(model);

        redirectAttributes.addFlashAttribute("mensaje", "Cliente excluido correctamente.");
        return "redirect:/credito/exclusiones";
    }

}
