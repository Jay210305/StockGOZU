package the305labs.inventario.service;

import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.Producto;
import the305labs.inventario.repository.InventarioRepository;
import the305labs.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    private final InventarioRepository inventarioRepo;

    public ProductoService(ProductoRepository repo, InventarioRepository inventarioRepo) {
        this.repo = repo;
        this.inventarioRepo = inventarioRepo;
    }

    public List<Producto> listarTodos() {
        return repo.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Producto crear(Producto p) {
        Producto productoGuardado = repo.save(p);
        // registra stock en tabla inventario
        if (p.getStockInicial() > 0) {
            Inventario inventario = new Inventario();
            inventario.setSucursalId(1);
            inventario.setProductoId(productoGuardado.getId());
            inventario.setCantidad(p.getStockInicial());
            inventario.setStockMinimo(0);
            inventarioRepo.save(inventario);
        }
        return productoGuardado;
    }

    public Producto actualizar(Producto p) {
        return repo.save(p);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}