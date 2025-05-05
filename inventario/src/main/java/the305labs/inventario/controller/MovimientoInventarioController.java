package the305labs.inventario.controller;

import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.service.MovimientoInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {
    private final MovimientoInventarioService service;

    public MovimientoInventarioController(MovimientoInventarioService service) {
        this.service = service;
    }

    @GetMapping("/{productoId}") //http://localhost:8081/api/movimientos/1
    public ResponseEntity<List<MovimientoInventario>> obtenerMovimientosPorProducto(
            @PathVariable Long productoId,
            @RequestParam(required = false) Integer sucursalId) {
        List<MovimientoInventario> movimientos = service.obtenerMovimientosPorProducto(productoId, sucursalId);
        return ResponseEntity.ok(movimientos);
    }
    @GetMapping("/historial") // http://localhost:8081/api/movimientos/historial
    public ResponseEntity<List<MovimientoInventario>> obtenerHistorialCompleto() {
        List<MovimientoInventario> historial = service.obtenerHistorialCompleto();
        return ResponseEntity.ok(historial);
    }
}