package the305labs.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.repository.MovimientoInventarioRepository;

import java.util.List;

@RestController
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping("/api/inventario/movimientos")
    public List<MovimientoInventario> obtenerHistorialMovimientos(
            @RequestParam(required = false) Integer sucursalId,
            @RequestParam(required = false) Long productoId) {

        if (sucursalId != null && productoId != null) {
            return movimientoInventarioRepository.findBySucursalIdAndProductoId(sucursalId, productoId);
        } else if (sucursalId != null) {
            return movimientoInventarioRepository.findBySucursalId(sucursalId);
        } else if (productoId != null) {
            return movimientoInventarioRepository.findByProductoId(productoId);
        } else {
            return movimientoInventarioRepository.findAll();
        }
    }
}
