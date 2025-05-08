package the305labs.inventario.service;

import the305labs.inventario.dto.ProductoDTO;
import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.Producto;
import the305labs.inventario.repository.InventarioRepository;
import the305labs.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    private final InventarioRepository inventarioRepo;

    public ProductoService(ProductoRepository repo, InventarioRepository inventarioRepo) {
        this.repo = repo;
        this.inventarioRepo = inventarioRepo;
    }

    public List<ProductoDTO> listarTodos() {
        return repo.findAll().stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de producto es obligatorio");
        }
        return repo.findById(id)
                .map(ProductoDTO::fromEntity);
    }

    public List<ProductoDTO> listarPorSucursal(Integer sucursalId) {
        if (sucursalId == null) {
            throw new IllegalArgumentException("El ID de sucursal es obligatorio");
        }
        List<Inventario> inventarios = inventarioRepo.findBySucursalId(sucursalId);
        List<Long> productoIds = inventarios.stream()
                .map(Inventario::getProductoId)
                .collect(Collectors.toList());

        return repo.findAllById(productoIds).stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductoDTO crear(ProductoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de producto son obligatorios");
        }
        if (repo.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un producto con el código: " + dto.getCodigo());
        }
        Producto entidad = dto.toEntity();
        Producto saved = repo.save(entidad);
        return ProductoDTO.fromEntity(saved);
    }

    public Optional<ProductoDTO> actualizar(Long id, ProductoDTO dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("ID y datos de producto son obligatorios");
        }
        return repo.findById(id).map(existing -> {
            if (!existing.getCodigo().equals(dto.getCodigo()) && repo.existsByCodigo(dto.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un producto con el código: " + dto.getCodigo());
            }
            Producto entidad = dto.toEntity();
            entidad.setId(id);
            Producto updated = repo.save(entidad);
            return ProductoDTO.fromEntity(updated);
        });
    }

    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de producto es obligatorio");
        }
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Producto no encontrado con ID " + id);
        }
        repo.deleteById(id);
    }
}
