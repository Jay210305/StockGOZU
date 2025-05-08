package the305labs.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import the305labs.inventario.entity.MovimientoInventario;

import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    List<MovimientoInventario> findBySucursalId(Integer sucursalId);

    List<MovimientoInventario> findByProductoId(Long productoId);

    List<MovimientoInventario> findBySucursalIdAndProductoId(Integer sucursalId, Long productoId);
}
