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

    @GetMapping("/{productoId}")
    public ResponseEntity<List<MovimientoInventario>> obtenerMovimientosPorProducto(
            @PathVariable Long productoId,
            @RequestParam(required = false) Integer sucursalId) {
                
        //obtiene movimts del servicio
        List<MovimientoInventario> movimientos = service.obtenerMovimientosPorProducto(productoId, sucursalId);
        return ResponseEntity.ok(movimientos);
    }
}