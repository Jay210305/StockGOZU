package the305labs.inventario.service;

import the305labs.inventario.dto.ProductoDTO;
import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.Producto;
import the305labs.inventario.repository.InventarioRepository;
import the305labs.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
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
        return repo.findById(id)
                .map(ProductoDTO::fromEntity);
    }

    public List<ProductoDTO> listarPorSucursal(Integer sucursalId) {
        // Obtener los productos asociados a la sucursal con el inventario
        List<Inventario> inventarios = inventarioRepo.findBySucursalId(sucursalId);
        List<Long> productoIds = inventarios.stream()
                .map(Inventario::getProductoId)
                .collect(Collectors.toList());

        // Buscar los productos que coinciden con esos IDs
        return repo.findAllById(productoIds).stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    public ProductoDTO crear(ProductoDTO dto) {
        Producto entidad = dto.toEntity();
        Producto saved = repo.save(entidad);
        return ProductoDTO.fromEntity(saved);
    }

    public Optional<ProductoDTO> actualizar(Long id, ProductoDTO dto) {
        return repo.findById(id).map(existing -> {
            Producto entidad = dto.toEntity();
            entidad.setId(id);
            Producto updated = repo.save(entidad);
            return ProductoDTO.fromEntity(updated);
        });
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}