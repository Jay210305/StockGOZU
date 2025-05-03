package the305labs.inventario.controller;

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

    @GetMapping("/sucursal/{sucursalId}")//rf6 http://localhost:8081/api/inventario/sucursal/1
    public ResponseEntity<List<Inventario>> listarInventarioPorSucursal(@PathVariable Integer sucursalId) {
        List<Inventario> inventario = service.listarInventarioPorSucursal(sucursalId);
        return ResponseEntity.ok(inventario);
    }

    @PostMapping("/movimiento")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(
            @RequestBody MovimientoInventario movimiento) {
        MovimientoInventario saved = service.registrarMovimiento(movimiento);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/alertas")
    public List<Inventario> alertasStock(@RequestParam(defaultValue = "0") Integer umbral) {
        // Filtrar inventario con cantidad <= umbral
        return service.listarAlertas(umbral);
    }
}