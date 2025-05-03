package the305labs.inventario.controller;

import jakarta.validation.constraints.Min;
import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping("/stock/{sucursalId}/{productoId}")
    public ResponseEntity<Inventario> obtenerStock(@PathVariable Integer sucursalId,
                                                   @PathVariable Long productoId) {
        Inventario inv = service.consultarStock(sucursalId, productoId);
        return ResponseEntity.ok(inv);
    }

    @PostMapping("/movimiento")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(
            @RequestBody MovimientoInventario movimiento) {
        MovimientoInventario saved = service.registrarMovimiento(movimiento);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/alertas")
    public List<Inventario> alertasStock(@RequestParam @Min(0) Integer umbral) {
        return service.listarAlertas(umbral);
    }
}