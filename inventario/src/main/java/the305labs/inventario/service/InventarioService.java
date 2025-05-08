package the305labs.inventario.service;

import the305labs.inventario.dto.InventarioDTO;
import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.InventarioPK;
import the305labs.inventario.entity.MovimientoInventario;
import the305labs.inventario.repository.InventarioRepository;
import the305labs.inventario.repository.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class InventarioService {
    private final InventarioRepository invRepo;
    private final MovimientoInventarioRepository movRepo;

    public InventarioService(InventarioRepository invRepo,
                             MovimientoInventarioRepository movRepo) {
        this.invRepo     = invRepo;
        this.movRepo     = movRepo;
    }

    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario mov) {
        if (mov.getSucursalId() == null || mov.getProductoId() == null || mov.getCantidad() == null) {
            throw new IllegalArgumentException("Sucursal, producto y cantidad son obligatorios");
        }
        return movRepo.save(mov);
    }

    public InventarioDTO consultarStock(Integer sucursalId, Long productoId) {
        if (sucursalId == null || productoId == null) {
            throw new IllegalArgumentException("sucursalId y productoId no pueden ser nulos");
        }

        InventarioPK pk = new InventarioPK(sucursalId, productoId);
        Inventario inv = invRepo.findById(pk).orElseThrow(() ->
                new NoSuchElementException("No se encontró inventario para sucursal " + sucursalId + " y producto " + productoId)
        );

        return InventarioDTO.fromEntity(inv);
    }

    public List<InventarioDTO> listarAlertas(Integer umbral) {
        if (umbral < 0) {
            throw new IllegalArgumentException("El umbral no puede ser negativo");
        }

        return invRepo.findAll().stream()
                .map(InventarioDTO::fromEntity)
                .filter(dto -> dto.getCantidad() <= umbral)
                .collect(Collectors.toList());
    }
}