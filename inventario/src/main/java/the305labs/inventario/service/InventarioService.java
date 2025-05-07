package the305labs.inventario.service;

import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.InventarioPK;
import the305labs.inventario.repository.MovimientoInventarioRepository;
import the305labs.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    private final InventarioRepository invRepo;
    private final MovimientoInventarioRepository movRepo;

    public InventarioService(InventarioRepository invRepo, MovimientoInventarioRepository movRepo) {
        this.invRepo = invRepo;
        this.movRepo = movRepo;
    }

    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario mov) {
        // El trigger DB validará stock negativo
        MovimientoInventario saved = movRepo.save(mov);
        // El trigger DB actualizará automáticamente tabla inventario
        return saved;
    }

    public Inventario consultarStock(Integer sucursalId, Long productoId) {
        InventarioPK pk = new InventarioPK(sucursalId, productoId);
        return invRepo.findById(pk).orElseGet(() -> {
            Inventario inv = new Inventario();
            inv.setSucursalId(sucursalId);
            inv.setProductoId(productoId);
            return invRepo.save(inv);
        });
    }

    public List<Inventario> listarAlertas(Integer umbral) {
        return invRepo.findByCantidadLessThanEqual(umbral); // ✅ Consulta parametrizada
    }
}