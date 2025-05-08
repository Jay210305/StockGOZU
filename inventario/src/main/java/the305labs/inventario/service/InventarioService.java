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
        if (mov.getSucursalId() == null || mov.getProductoId() == null || mov.getCantidad() == null || mov.getTipo() == null) {
            throw new IllegalArgumentException("Sucursal, producto, cantidad y tipo son obligatorios");
        }

        // Obtener la clave compuesta
        InventarioPK pk = new InventarioPK(mov.getSucursalId(), mov.getProductoId());

        // Buscar el inventario correspondiente o crearlo si no existe
        Inventario inventario = invRepo.findById(pk).orElseGet(() -> {
            Inventario nuevo = new Inventario();
            nuevo.setSucursalId(mov.getSucursalId());
            nuevo.setProductoId(mov.getProductoId());
            nuevo.setCantidad(0); // stock inicial
            return nuevo;
        });

        // Actualizar el stock según el tipo de movimiento
        int nuevaCantidad = inventario.getCantidad();
        if (mov.getTipo() == MovimientoInventario.Tipo.INGRESO) {
            nuevaCantidad += mov.getCantidad();
        } else if (mov.getTipo() == MovimientoInventario.Tipo.SALIDA) {
            if (mov.getCantidad() > nuevaCantidad) {
                throw new IllegalArgumentException("No hay suficiente stock para realizar la salida");
            }
            nuevaCantidad -= mov.getCantidad();
        }

        inventario.setCantidad(nuevaCantidad);
        invRepo.save(inventario); // Guardar cambios en el inventario

        return movRepo.save(mov); // Registrar el movimiento
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