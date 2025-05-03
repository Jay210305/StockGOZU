package the305labs.inventario.repository;

import the305labs.inventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByProductoId(Long productoId);
    List<MovimientoInventario> findByProductoIdAndSucursalId(Long productoId, Integer sucursalId);
    
}