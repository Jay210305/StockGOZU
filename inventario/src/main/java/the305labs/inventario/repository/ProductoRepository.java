package the305labs.inventario.repository;

import the305labs.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findBySucursal_Id(Integer id);

    boolean existsByCodigo(String codigo);
}
