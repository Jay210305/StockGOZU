package the305labs.inventario.service;

import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.repository.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoInventarioService {
    private final MovimientoInventarioRepository repository; //acede repo

    public MovimientoInventarioService(MovimientoInventarioRepository repository) { //constructor
        this.repository = repository;
    }
    
    //filtra por sucursal y producto
    public List<MovimientoInventario> obtenerMovimientosPorProducto(Long productoId, Integer sucursalId) {
        if (sucursalId != null) {
            return repository.findByProductoIdAndSucursalId(productoId, sucursalId);
        }
        return repository.findByProductoId(productoId);
    }
    public List<MovimientoInventario> obtenerHistorialCompleto() {
        return repository.findAllByOrderByFechaDesc();
    }
}