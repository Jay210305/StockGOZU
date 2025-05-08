package the305labs.inventario.controller;

import the305labs.inventario.dto.InventarioDTO;
import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping("/stock/{sucursalId}/{productoId}")
    public ResponseEntity<InventarioDTO> obtenerStock(@PathVariable Integer sucursalId,
                                                      @PathVariable Long productoId) {
        InventarioDTO dto = service.consultarStock(sucursalId, productoId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/stockbajo")
    public ResponseEntity<List<InventarioDTO>> obtenerStockBajo(@RequestParam(defaultValue = "0") Integer umbral) {
        List<InventarioDTO> productosBajoStock = service.listarAlertas(umbral);
        return ResponseEntity.ok(productosBajoStock);
    }

    @PostMapping("/movimiento")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(
            @Valid @RequestBody MovimientoInventario movimiento) {
        MovimientoInventario saved = service.registrarMovimiento(movimiento);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<InventarioDTO>> alertasStock(
            @RequestParam(defaultValue = "0") Integer umbral) {
        List<InventarioDTO> lista = service.listarAlertas(umbral);
        return ResponseEntity.ok(lista);
    }

}